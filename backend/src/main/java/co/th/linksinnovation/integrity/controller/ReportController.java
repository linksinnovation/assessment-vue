/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.controller;

import co.th.linksinnovation.integrity.model.Assessment;
import co.th.linksinnovation.integrity.model.LocationScore;
import co.th.linksinnovation.integrity.repository.AssessmentRepository;
import co.th.linksinnovation.integrity.repository.LocationScoreRepository;
import co.th.linksinnovation.integrity.repository.ReportRepository;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jirawong
 */
@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private LocationScoreRepository locationScoreRepository;
    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private ReportRepository reportRepository;

    @GetMapping("/{id}")
    public List<LocationScore> getLocationScore(@PathVariable Integer id) {
        Assessment assessment = assessmentRepository.findOne(id);
        return locationScoreRepository.findByAssessment(assessment);
    }

    @GetMapping("/csv/{id}")
    public ResponseEntity<byte[]> getReport(@PathVariable Integer id) throws UnsupportedEncodingException {
        Assessment assessment = assessmentRepository.findOne(id);
        String report = reportRepository.getReport(assessment);

        byte[] output = report.getBytes("UTF-8");

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("charset", "UTF-8");
        responseHeaders.setContentType(MediaType.valueOf("text/html"));
        responseHeaders.setContentLength(output.length);
        responseHeaders.set("Content-disposition", "attachment; filename=report.csv");

        return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
    }
}
