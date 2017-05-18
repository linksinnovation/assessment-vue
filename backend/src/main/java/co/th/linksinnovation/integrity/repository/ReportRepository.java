/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.repository;

import co.th.linksinnovation.integrity.model.Assessment;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
@Repository
public class ReportRepository {

    @PersistenceContext
    private EntityManager em;

    private static final String reportUser = "select id,location,employee_id,name_th,business_name,group_name,field_name,area_name,party_name,pass,assessment_date,username from report_user where id=:assessment";
    private static final String reportQuestion = "select checked,selected from report_question where assessment_id=:assessment and user_username=:username";

    public String getReport(Assessment assessment) {
        Query q = em.createNativeQuery(reportUser);
        q.setParameter("assessment", assessment.getId());
        List<Object[]> resultList = q.getResultList();

        StringBuilder sb = new StringBuilder();
        for (Object[] o : resultList) {
            sb.append(o[1].toString());
            sb.append(",");
            sb.append(o[2].toString());
            sb.append(",");
            sb.append(o[3].toString());
            sb.append(",");
            sb.append(o[4].toString());
            sb.append(",");
            sb.append(o[5].toString());
            sb.append(",");
            sb.append(o[6].toString());
            sb.append(",");
            sb.append(o[7].toString());
            sb.append(",");
            sb.append(o[8].toString());

            Query query = em.createNativeQuery(reportQuestion);
            query.setParameter("assessment", assessment.getId());
            query.setParameter("username", o[11].toString());
            List<Object[]> result = query.getResultList();
            for (Object[] object : result) {
                sb.append(",");
                sb.append(object[0].toString());
            }

            sb.append(",");
            sb.append(o[9].toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
