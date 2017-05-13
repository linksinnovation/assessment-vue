/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.service;

import co.th.linksinnovation.integrity.model.Assessment;
import co.th.linksinnovation.integrity.model.AssessmentUser;
import co.th.linksinnovation.integrity.model.LocationScore;
import co.th.linksinnovation.integrity.model.OrganizeData;
import co.th.linksinnovation.integrity.model.UserDetails;
import co.th.linksinnovation.integrity.repository.LocationScoreRepository;
import co.th.linksinnovation.integrity.repository.OrganizeDataRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jirawong
 */
@Service
public class ScoreService {

    @Autowired
    private OrganizeDataRepository organizeDataRepository;
    @Autowired
    private LocationScoreRepository locationScoreRepository;

    public void calculateLocationScore(UserDetails userDetails, Assessment assessment, AssessmentUser assessmentUser) {
        List<OrganizeData> organizeDatas = organizeDataRepository.findByUsernameAndAssessment(userDetails.getUsername().toUpperCase(), assessment);
        if (organizeDatas.size() > 0) {
            OrganizeData get = organizeDatas.get(0);
            List<LocationScore> locationScores = locationScoreRepository.findByOrganizeNameAndBusinessNameAndGroupNameAndFieldNameAndAreaNameAndPartyNameAndAssessment(get.getLocation(), get.getBusinessName(), get.getGroupName(), get.getFieldName(), get.getAreaName(), get.getPartyName(), assessment);
            if(locationScores.size() > 0){
                LocationScore locationScore = locationScores.get(0);
                System.out.println(assessment.getAssessmentUsers().size());
                if(assessment.getAssessmentUsers().size() == 1){
                    locationScore = locationScore.incrementDone();
                    System.out.println(locationScore.getOrgaizeDone());
                }
                int score = 0;
                for(AssessmentUser au : assessment.getAssessmentUsers()){
                    if(au.isPass()){
                        score++;
                    }
                }
                if(score == 1 && assessmentUser.isPass()){
                    locationScore = locationScore.incrementPass();
                }
                locationScoreRepository.save(locationScore);
            }
        }

    }
}
