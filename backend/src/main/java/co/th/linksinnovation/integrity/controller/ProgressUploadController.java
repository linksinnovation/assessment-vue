package co.th.linksinnovation.integrity.controller;

import co.th.linksinnovation.integrity.model.Assessment;
import co.th.linksinnovation.integrity.model.Course;
import co.th.linksinnovation.integrity.model.OrganizeData;
import co.th.linksinnovation.integrity.model.enumuration.ContentType;
import co.th.linksinnovation.integrity.repository.AssessmentRepository;
import co.th.linksinnovation.integrity.repository.CourseRepository;
import co.th.linksinnovation.integrity.repository.OrganizeDataRepository;
import co.th.linksinnovation.integrity.service.OrganizeDataService;
import co.th.linksinnovation.integrity.utils.MD5;
import co.th.linksinnovation.integrity.utils.QualitySelect;
import co.th.linksinnovation.integrity.utils.mediainfo.MediaInfo;
import co.th.linksinnovation.integrity.utils.mediainfo.MediaInfoUtil;
import co.th.linksinnovation.integrity.utils.ppt2pdf.Ppt2Pdf;
import com.itextpdf.text.DocumentException;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Kong on 12/26/2015 AD.
 */
@RestController
@RequestMapping("/api")
public class ProgressUploadController {

    private static final int BUFFER_SIZE = 1024 * 100;

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private OrganizeDataRepository organizeDataRepository;
    
    @Autowired
    private OrganizeDataService organizeDataService;

    @RequestMapping(value = "/videoupload", method = RequestMethod.PUT)
    public void upload(@RequestBody byte[] file, HttpServletRequest request) throws IOException, InterruptedException {
        InputStream chunk = new ByteArrayInputStream(file);
        String filename = URLDecoder.decode(request.getHeader("Content-Name"), "UTF-8");
        String hexFile = MD5.getMd5(filename);
        appendFile(request.getHeader("Content-Start"), chunk, new File("/mnt/data/source/" + hexFile));
        if (request.getHeader("Content-End") != null && request.getHeader("Content-End").equals(request.getHeader("Content-FileSize"))) {
            final MediaInfo mediaInfo = MediaInfoUtil.getMediaInfo("/mnt/data/source/" + hexFile);
            Course course = courseRepository.findOne(Integer.parseInt(request.getHeader("Content-Lecture")));
            course.setContent(filename);
            course.setContentType(ContentType.VIDEO);
            course.setDuration(Long.parseLong(mediaInfo.get("Video", "Duration")));
            course.setUpdateDate(new Date());
            course.setUuid(hexFile);
            course = courseRepository.save(course);
            
            RestTemplate rest = new RestTemplate();
            Map<String, Object> map = new HashMap<>();
            map.put("uuid", hexFile);
            map.put("lecture", course.getId());
            map.put("quality", QualitySelect.select(mediaInfo.get("Video", "Height")).toString());
            rest.postForEntity("http://10.1.2.202:8080", map, String.class);
        }
    }

    @RequestMapping(value = "/pdfupload", method = RequestMethod.PUT)
    public void pdfUpload(@RequestBody byte[] file, HttpServletRequest request) throws UnsupportedEncodingException {
        InputStream chunk = new ByteArrayInputStream(file);
        String filename = URLDecoder.decode(request.getHeader("Content-Name"), "UTF-8");
        appendFile(request.getHeader("Content-Start"), chunk, new File("/mnt/data/files/" + request.getHeader("Content-Lecture") + "-" + filename));
        if (request.getHeader("Content-End") != null && request.getHeader("Content-End").equals(request.getHeader("Content-FileSize"))) {
            Course course = courseRepository.findOne(Integer.parseInt(request.getHeader("Content-Lecture")));
            course.setContent(filename);
            course.setContentType(ContentType.PDF);
            courseRepository.save(course);
        }
    }

    @RequestMapping(value = "/pptupload", method = RequestMethod.PUT)
    public void pptUpload(@RequestBody byte[] file, HttpServletRequest request) throws UnsupportedEncodingException, IOException, DocumentException {
        InputStream chunk = new ByteArrayInputStream(file);
        String filename = URLDecoder.decode(request.getHeader("Content-Name"), "UTF-8");
        appendFile(request.getHeader("Content-Start"), chunk, new File("/mnt/data/files/" + request.getHeader("Content-Lecture") + "-" + filename));
        if (request.getHeader("Content-End") != null && request.getHeader("Content-End").equals(request.getHeader("Content-FileSize"))) {
            Ppt2Pdf.convert(
                    new FileInputStream("/mnt/data/files/" + request.getHeader("Content-Lecture") + "-" + filename),
                    new FileOutputStream("/mnt/data/files/" + request.getHeader("Content-Lecture") + "-" + filename + ".pdf")
            );
            Course course = courseRepository.findOne(Integer.parseInt(request.getHeader("Content-Lecture")));
            course.setContent(filename);
            course.setContentType(ContentType.PPT);
            courseRepository.save(course);
        }
    }

    @RequestMapping(value = "/csvupload", method = RequestMethod.PUT)
    public void csvUpload(@RequestBody byte[] file, HttpServletRequest request) throws UnsupportedEncodingException, IOException, DocumentException {
        InputStream chunk = new ByteArrayInputStream(file);
        String filename = URLDecoder.decode(request.getHeader("Content-Name"), "UTF-8");
        appendFile(request.getHeader("Content-Start"), chunk, new File("/mnt/data/files/" + request.getHeader("Content-Lecture") + "-" + filename));
        if (request.getHeader("Content-End") != null && request.getHeader("Content-End").equals(request.getHeader("Content-FileSize"))) {
            BeanListProcessor<OrganizeData> rowProcessor = new BeanListProcessor<>(OrganizeData.class);

            CsvParserSettings parserSettings = new CsvParserSettings();
            parserSettings.setRowProcessor(rowProcessor);
            parserSettings.setNullValue("");
            parserSettings.setHeaderExtractionEnabled(true);

            CsvParser parser = new CsvParser(parserSettings);
            parser.parse(new File("/mnt/data/files/" + request.getHeader("Content-Lecture") + "-" + filename));

            List<OrganizeData> beans = rowProcessor.getBeans();

            Assessment assessment = assessmentRepository.findOne(Integer.parseInt(request.getHeader("Content-Lecture")));
            organizeDataRepository.removeByAssessment(assessment);

            for (OrganizeData org : beans) {
                org.setAssessment(assessment);
            }

            assessment = assessmentRepository.findOne(Integer.parseInt(request.getHeader("Content-Lecture")));
            assessment.setOrganizeFile(filename);
            assessment.getOrganizeDatas().clear();
            assessment.getOrganizeDatas().addAll(beans);
            assessmentRepository.save(assessment);
            organizeDataService.calculateOrganizeChart(assessment);
        }
    }

    private void appendFile(String start, InputStream in, File dest) {
        OutputStream out = null;

        try {
            if (dest.exists()) {
                if (start.equals("0")) {
                    if (dest.delete()) {
                        out = new BufferedOutputStream(new FileOutputStream(dest), BUFFER_SIZE);
                    }
                }
                out = new BufferedOutputStream(new FileOutputStream(dest, true), BUFFER_SIZE);
            } else {
                out = new BufferedOutputStream(new FileOutputStream(dest), BUFFER_SIZE);
            }
            in = new BufferedInputStream(in, BUFFER_SIZE);

            int len = 0;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
}
