/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.repository;

import co.th.linksinnovation.integrity.model.Assessment;
import co.th.linksinnovation.integrity.model.LocationScore;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
public interface LocationScoreRepository extends JpaRepository<LocationScore, Integer>{

    public List<LocationScore> findByOrganizeNameAndBusinessNameAndGroupNameAndFieldNameAndAreaNameAndPartyName(String organizeName, String businessName, String groupName, String fieldName, String areaName, String partyName);

    public List<LocationScore> findByOrganizeNameAndBusinessNameAndGroupNameAndFieldNameAndAreaNameAndPartyNameAndAssessment(String location, String businessName, String groupName, String fieldName, String areaName, String partyName, Assessment assessment);

    @Transactional
    public void deleteByAssessment(Assessment assessment);

    public List<LocationScore> findByAssessment(Assessment assessment);
    
}
