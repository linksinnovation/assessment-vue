package co.th.linksinnovation.integrity.controller;

import co.th.linksinnovation.integrity.model.Assessment;
import co.th.linksinnovation.integrity.model.Course;
import co.th.linksinnovation.integrity.model.OrganizeData;
import co.th.linksinnovation.integrity.model.enumuration.ContentType;
import co.th.linksinnovation.integrity.repository.AssessmentRepository;
import co.th.linksinnovation.integrity.repository.CourseRepository;
import co.th.linksinnovation.integrity.repository.OrganizeDataRepository;
import co.th.linksinnovation.integrity.repository.UserDetailsRepository;
import co.th.linksinnovation.integrity.service.OrganizeDataService;
import co.th.linksinnovation.integrity.utils.MD5;
import co.th.linksinnovation.integrity.utils.QualitySelect;
import co.th.linksinnovation.integrity.utils.mediainfo.MediaInfo;
import co.th.linksinnovation.integrity.utils.mediainfo.MediaInfoUtil;
import co.th.linksinnovation.integrity.utils.ppt2pdf.Ppt2Pdf;
import com.itextpdf.text.DocumentException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
    private UserDetailsRepository userDetailsRepository;

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
    public void csvUpload(@RequestBody byte[] file, HttpServletRequest request) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        InputStream chunk = new ByteArrayInputStream(file);
        String filename = URLDecoder.decode(request.getHeader("Content-Name"), "UTF-8");
        appendFile(request.getHeader("Content-Start"), chunk, new File("/mnt/data/files/" + request.getHeader("Content-Lecture") + "-" + filename));
        if (request.getHeader("Content-End") != null && request.getHeader("Content-End").equals(request.getHeader("Content-FileSize"))) {

            List<OrganizeData> organizeDatas;
            try (FileInputStream inputStream = new FileInputStream(new File("/mnt/data/files/" + request.getHeader("Content-Lecture") + "-" + filename))) {
                Workbook workbook = new XSSFWorkbook(inputStream);
                Sheet firstSheet = workbook.getSheetAt(0);
                Iterator<Row> iterator = firstSheet.iterator();
                organizeDatas = new ArrayList<>();
                while (iterator.hasNext()) {
                    Row nextRow = iterator.next();

//                    Iterator<Cell> cellIterator = nextRow.cellIterator();
//                    while (cellIterator.hasNext()) {
//                        Cell cell = cellIterator.next();
//                        System.out.println(cell.getColumnIndex() + " - " + cell.getStringCellValue());
//                    }
//                    if(nextRow.getRowNum() == 1){
//                        return;
//                    }
                    
                    if (nextRow.getRowNum() == 0) {
                        if (nextRow.getCell(58).getStringCellValue().equals("User_name")) {
                            continue;
                        } else {
                            return;
                        }
                    }else if(nextRow.getCell(58).getStringCellValue().equals("")){
                        continue;
                    }

                    OrganizeData organizeData = new OrganizeData();
                    organizeData.setOrganizeId(nextRow.getCell(7).getStringCellValue());
                    organizeData.setOrganizeName(nextRow.getCell(8).getStringCellValue());
                    organizeData.setBusinessId(nextRow.getCell(10).getStringCellValue());
                    organizeData.setBusinessName(nextRow.getCell(11).getStringCellValue());
                    organizeData.setGroupId(nextRow.getCell(13).getStringCellValue());
                    organizeData.setGroupName(nextRow.getCell(14).getStringCellValue());
                    organizeData.setFieldId(nextRow.getCell(16).getStringCellValue());
                    organizeData.setFieldName(nextRow.getCell(17).getStringCellValue());
                    organizeData.setAreaId(nextRow.getCell(19).getStringCellValue());
                    organizeData.setAreaName(nextRow.getCell(20).getStringCellValue());
                    organizeData.setPartyId(nextRow.getCell(22).getStringCellValue());
                    organizeData.setPartyName(nextRow.getCell(23).getStringCellValue());
                    organizeData.setLocation(nextRow.getCell(52).getStringCellValue());
                    organizeData.setUsername(nextRow.getCell(58).getStringCellValue());
                    organizeData.setEmployeeId(nextRow.getCell(0).getStringCellValue());
                    organizeDatas.add(organizeData);
                }

                workbook.close();
            }

            Assessment assessment = assessmentRepository.findOne(Integer.parseInt(request.getHeader("Content-Lecture")));
            organizeDataRepository.removeByAssessment(assessment);

            for (OrganizeData org : organizeDatas) {
                org.setAssessment(assessment);
            }

            assessment = assessmentRepository.findOne(Integer.parseInt(request.getHeader("Content-Lecture")));
            assessment.setOrganizeFile(filename);
            assessment.getOrganizeDatas().clear();
            assessment.getOrganizeDatas().addAll(organizeDatas);
            assessmentRepository.save(assessment);
            organizeDataService.calculateOrganizeChart(assessment);
        }
    }

    @RequestMapping(value = "/coverupload", method = RequestMethod.PUT)
    public void coverUpload(@RequestBody byte[] file, HttpServletRequest request) throws UnsupportedEncodingException {
        InputStream chunk = new ByteArrayInputStream(file);
        String filename = URLDecoder.decode(request.getHeader("Content-Name"), "UTF-8");
        appendFile(request.getHeader("Content-Start"), chunk, new File("/mnt/data/images/" + request.getHeader("Content-Lecture") + "-" + filename));
        if (request.getHeader("Content-End") != null && request.getHeader("Content-End").equals(request.getHeader("Content-FileSize"))) {
            Assessment assessment = assessmentRepository.findOne(Integer.parseInt(request.getHeader("Content-Lecture")));
            assessment.setCover(request.getHeader("Content-Lecture") + "-" + filename);
            assessmentRepository.save(assessment);
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
