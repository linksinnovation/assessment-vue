/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.controller;

import co.th.linksinnovation.integrity.model.Assessment;
import co.th.linksinnovation.integrity.repository.AssessmentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
@RestController
@RequestMapping("/api/assessment")
public class AssessmentController {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @GetMapping("/{id}")
    public Assessment get(@PathVariable Integer id) {
        return assessmentRepository.findOne(id);
    }

    @GetMapping
    public List<Assessment> get() {
        return assessmentRepository.findAll();
    }

    @PostMapping
    public Assessment post(@RequestBody Assessment assessment) {
        return assessmentRepository.save(assessment);
    }
}
