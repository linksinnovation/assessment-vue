/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.repository;

import co.th.linksinnovation.integrity.model.Assessment;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
public interface AssessmentRepository extends JpaRepository<Assessment, Integer>{

    public List<Assessment> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date date, Date date0);

    public List<Assessment> findByEnableIsTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Date date, Date date0);

    public List<Assessment> findByEnableIsTrue();
    
}
