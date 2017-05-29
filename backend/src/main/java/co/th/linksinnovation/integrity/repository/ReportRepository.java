/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.repository;

import co.th.linksinnovation.integrity.model.Assessment;
import co.th.linksinnovation.integrity.model.Question;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
@Repository
public class ReportRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private QuestionRepository questionRepository;

    private static final String reportUser = "select assessment_id,location,employee_id,name_th,business_name,group_name,field_name,area_name,party_name,pass,assessment_date,username from report_user where assessment_id=:assessment";
    private static final String reportQuestion = "select checked,selected from report_question where assessment_id=:assessment and user_username=:username";

    public String getReport(Assessment assessment) throws FileNotFoundException, IOException {
        Query q = em.createNativeQuery(reportUser);
        q.setParameter("assessment", assessment.getId());
        List<Object[]> resultList = q.getResultList();

        int rownum = 1;
        int questionIndex = 1;
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Report");

        List<Question> questions = questionRepository.findByAssessment(assessment);
        XSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("Location");
        header.createCell(1).setCellValue("รหัสพนักงาน");
        header.createCell(2).setCellValue("ชื่อ");
        header.createCell(3).setCellValue("กลุ่มธุรกิจ");
        header.createCell(4).setCellValue("กลุ่มงาน");
        header.createCell(5).setCellValue("สายงาน");
        header.createCell(6).setCellValue("ด้าน");
        header.createCell(7).setCellValue("ฝ่าย");
        for (Question question : questions) {
            header.createCell(questionIndex + 7).setCellValue("ข้อ " + questionIndex);
            questionIndex++;
        }
        header.createCell(questionIndex + 7).setCellValue("สรุปผลการทำแบบทดสอบ");

        for(Object[] o : resultList) {
            XSSFRow row = sheet.createRow(rownum);
            row.createCell(0).setCellValue(o[1].toString());
            row.createCell(1).setCellValue(o[2].toString());
            row.createCell(2).setCellValue(o[3].toString());
            row.createCell(3).setCellValue(o[4].toString());
            row.createCell(4).setCellValue(o[5].toString());
            row.createCell(5).setCellValue(o[6].toString());
            row.createCell(6).setCellValue(o[7].toString());
            row.createCell(7).setCellValue(o[8].toString());

            Query query = em.createNativeQuery(reportQuestion);
            query.setParameter("assessment", assessment.getId());
            query.setParameter("username", o[11].toString());
            List<Object[]> result = query.getResultList();
            int cellnum = 8;
            for (Object[] object : result) {
                row.createCell(cellnum).setCellValue(object[0].toString().toUpperCase());
                cellnum++;
            }
            row.createCell(cellnum).setCellValue(o[9].toString().toUpperCase());
            rownum++;
        }

        String filename = "/mnt/data/files/report-" + assessment.getId() + ".xlsx";

        FileOutputStream outputStream = new FileOutputStream(filename);
        workbook.write(outputStream);
        workbook.close();
        return filename;
    }
}
