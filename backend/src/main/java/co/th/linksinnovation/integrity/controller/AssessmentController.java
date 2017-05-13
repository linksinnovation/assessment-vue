/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.controller;

import co.th.linksinnovation.integrity.model.Assessment;
import co.th.linksinnovation.integrity.model.UserDetails;
import co.th.linksinnovation.integrity.repository.AssessmentRepository;
import co.th.linksinnovation.integrity.repository.UserDetailsRepository;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @GetMapping("/{id}")
    public Assessment get(@PathVariable Integer id) {
        return assessmentRepository.findOne(id);
    }

    @GetMapping
    public List<Assessment> get(@AuthenticationPrincipal String username) {
        UserDetails findOne = userDetailsRepository.findOne(username);
        if(findOne.getAuthorities().get(0).getAuthority().equals("Administrator")){
            return assessmentRepository.findAll();
        }else{
            return assessmentRepository.findByStartDateGreaterThanEqualAndEndDateLessThanEqual(new Date(),new Date());
        }   
    }

    @PostMapping
    public Assessment post(@RequestBody Assessment assessment) throws IOException {
        Assessment save = assessmentRepository.save(assessment);
        File file = new File("/mnt/data/images/qrcode-"+save.getId()+".jpg");
        if(!file.exists()){
            QRCode.from("http://localhost:8000/assessment/"+save.getId()+"/assessment").to(ImageType.JPG).writeTo(new FileOutputStream(file));
        }
        return save;
    }
}
