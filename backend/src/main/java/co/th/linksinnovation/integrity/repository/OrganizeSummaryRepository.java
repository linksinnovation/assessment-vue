/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.repository;

import co.th.linksinnovation.integrity.model.Assessment;
import co.th.linksinnovation.integrity.model.LocationScore;
import co.th.linksinnovation.integrity.model.OverviewScore;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
@Repository
public class OrganizeSummaryRepository {
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private LocationScoreRepository locationScoreRepository;
    @Autowired
    private OverviewScoreRepository overviewScoreRepository;

    private static final String location = "Select location,location_count,business_name,business_count,group_name,group_count,field_name,field_count,area_name,area_count,party_name,party_count,assessment_id from location_sum where assessment_id=:assessment";
    private static final String overview = "Select organize_name,organize_count,business_name,business_count,group_name,group_count,field_name,field_count,area_name,area_count,party_name,party_count,assessment_id from overview_sum where assessment_id=:assessment";
    
    public void initLocationScore(Assessment assessment){
        Query q = em.createNativeQuery(location);
        q.setParameter("assessment", assessment.getId());
        List<Object[]> resultList = q.getResultList();
        List<LocationScore> organizeScores = new ArrayList<>();
        for(Object[] o : resultList){
            LocationScore locationScore = new LocationScore();
            locationScore.setOrganizeName(o[0].toString());
            locationScore.setOrganizeTotal((BigInteger)o[1]);
            locationScore.setBusinessName(o[2].toString());
            locationScore.setBusinessTotal((BigInteger)o[3]);
            locationScore.setGroupName(o[4].toString());
            locationScore.setGroupTotal((BigInteger)o[5]);
            locationScore.setFieldName(o[6].toString());
            locationScore.setFieldTotal((BigInteger)o[7]);
            locationScore.setAreaName(o[8].toString());
            locationScore.setAreaTotal((BigInteger)o[9]);
            locationScore.setPartyName(o[10].toString());
            locationScore.setPartyTotal((BigInteger)o[11]);
            locationScore.setAssessment(assessment);
            organizeScores.add(locationScore);
        }
        List<LocationScore> save = locationScoreRepository.save(organizeScores);
        initOverviewScore(assessment);
    }
    
    private void initOverviewScore(Assessment assessment){
        Query q = em.createNativeQuery(overview);
        q.setParameter("assessment", assessment.getId());
        List<Object[]> resultList = q.getResultList();
        List<OverviewScore> organizeScores = new ArrayList<>();
        for(Object[] o : resultList){
            OverviewScore overviewScore = new OverviewScore();
            overviewScore.setOrganizeName(o[0].toString());
            overviewScore.setOrganizeTotal((BigInteger)o[1]);
            overviewScore.setBusinessName(o[2].toString());
            overviewScore.setBusinessTotal((BigInteger)o[3]);
            overviewScore.setGroupName(o[4].toString());
            overviewScore.setGroupTotal((BigInteger)o[5]);
            overviewScore.setFieldName(o[6].toString());
            overviewScore.setFieldTotal((BigInteger)o[7]);
            overviewScore.setAreaName(o[8].toString());
            overviewScore.setAreaTotal((BigInteger)o[9]);
            overviewScore.setPartyName(o[10].toString());
            overviewScore.setPartyTotal((BigInteger)o[11]);
            overviewScore.setAssessment(assessment);
            organizeScores.add(overviewScore);
        }
        List<OverviewScore> save = overviewScoreRepository.save(organizeScores);
    }
}
