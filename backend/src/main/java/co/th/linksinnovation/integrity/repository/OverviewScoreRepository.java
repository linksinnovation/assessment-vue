/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.repository;

import co.th.linksinnovation.integrity.model.Assessment;
import co.th.linksinnovation.integrity.model.OverviewScore;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
public interface OverviewScoreRepository extends JpaRepository<OverviewScore, Integer>{

    public List<OverviewScore> findByOrganizeNameAndBusinessNameAndGroupNameAndFieldNameAndAreaNameAndPartyNameAndAssessment(String location, String businessName, String groupName, String fieldName, String areaName, String partyName, Assessment assessment);
    
}
