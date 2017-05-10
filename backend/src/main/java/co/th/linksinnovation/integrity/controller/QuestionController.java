/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.controller;

import co.th.linksinnovation.integrity.model.Assessment;
import co.th.linksinnovation.integrity.model.AssessmentUser;
import co.th.linksinnovation.integrity.model.Question;
import co.th.linksinnovation.integrity.model.UserDetails;
import co.th.linksinnovation.integrity.model.UserQuestion;
import co.th.linksinnovation.integrity.repository.AssessmentRepository;
import co.th.linksinnovation.integrity.repository.QuestionRepository;
import co.th.linksinnovation.integrity.repository.UserDetailsRepository;
import co.th.linksinnovation.integrity.repository.UserQuestionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserQuestionRepository userQuestionRepository;
    @Autowired
    private AssessmentRepository assessmentRepository;

    @PostMapping("/{id}")
    public List<UserQuestion> post(@RequestBody List<Map<String, String>> answers, @PathVariable Integer id, @AuthenticationPrincipal String user) {
        Assessment assessment = assessmentRepository.findOne(id);
        UserDetails userDetails = userDetailsRepository.findOne(user);
        List<UserQuestion> userQuestions = new ArrayList<>();
        AssessmentUser assessmentUser = new AssessmentUser();

        Integer score = 0;

        for (Map<String, String> map : answers) {
            Question question = questionRepository.findOne(Integer.parseInt(map.get("question")));
            UserQuestion userQuestion = new UserQuestion();
            userQuestion.setQuestion(question);
            userQuestion.setUser(userDetails);
            userQuestion.setSelected(Integer.parseInt(map.get("selected")));
            userQuestion.setChecked(question.getChecked().equals(Integer.parseInt(map.get("selected"))));
            userQuestions.add(userQuestion);
            if (question.getChecked().equals(Integer.parseInt(map.get("selected")))) {
                score++;
            }
        }
        
        if((score/userQuestions.size())*100 >= assessment.getPassScore()){
            assessmentUser.setPass(true);
        }
        assessmentUser.setAssessment(assessment);
        assessmentUser.setUser(userDetails);
        assessment.addAssessmentUser(assessmentUser);
        assessmentRepository.save(assessment);
        

        userQuestions = userQuestionRepository.save(userQuestions);

        return userQuestions;
    }
}
