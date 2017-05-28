/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.controller;

import co.th.linksinnovation.integrity.model.Course;
import co.th.linksinnovation.integrity.model.UserCourse;
import co.th.linksinnovation.integrity.model.UserDetails;
import co.th.linksinnovation.integrity.repository.CourseRepository;
import co.th.linksinnovation.integrity.repository.UserCourseRepository;
import co.th.linksinnovation.integrity.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
@RestController
@RequestMapping("/api/course")
public class CourseController {
    
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserCourseRepository userCourseRepository;
    
    @GetMapping("/{assessment}/{course}")
    public void get(@PathVariable("assessment") Integer assessemnt,@PathVariable("course") Integer course, @AuthenticationPrincipal String username){
        UserDetails userdetails = userDetailsRepository.findOne(username);
        Course findCourse = courseRepository.findOne(course);
        UserCourse userCourse = new UserCourse();
        userCourse.setAssessmentId(assessemnt);
        userCourse.setUser(userdetails);
        userCourse.setCourse(findCourse);
        userCourseRepository.save(userCourse);
    }
}
