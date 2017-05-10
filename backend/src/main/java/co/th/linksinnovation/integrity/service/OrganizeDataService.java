/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.service;

import co.th.linksinnovation.integrity.model.Assessment;
import co.th.linksinnovation.integrity.repository.AssessmentRepository;
import co.th.linksinnovation.integrity.repository.OrganizeDataRepository;
import co.th.linksinnovation.integrity.repository.OrganizeSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
@Service
@Transactional
public class OrganizeDataService {
    
    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private OrganizeDataRepository organizeDataRepository;
    @Autowired
    private OrganizeSummaryRepository organizeSummaryRepository;
    
    public void calculateOrganizeChart(Assessment assessment){
        organizeSummaryRepository.initLocationScore(assessment);
    }
    
}
