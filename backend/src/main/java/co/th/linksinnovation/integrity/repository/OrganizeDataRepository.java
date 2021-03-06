/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.repository;

import co.th.linksinnovation.integrity.model.Assessment;
import co.th.linksinnovation.integrity.model.OrganizeData;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
public interface OrganizeDataRepository extends JpaRepository<OrganizeData, Long>{
    @Modifying
    @Transactional
    @Query("delete from OrganizeData o where o.assessment = ?1")
    public void removeByAssessment(Assessment assessment);
    
    @Query("select distinct o.organizeName from OrganizeData o where o.assessment = ?1")
    public List<String> selectDistinctOrganize(Assessment assessment);

    public List<OrganizeData> findByUsernameAndAssessment(String toUpperCase, Assessment assessment);

}
