/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.repository;

import co.th.linksinnovation.integrity.model.ChartData;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jirawong
 */
@Repository
public class ChartDataRepository {

    @PersistenceContext
    private EntityManager em;

    public List<ChartData> getLocation(Integer assessment, Map<String, String> request) {
        String queryString = "";
        String selectString = "SELECT distinct";
        String sumString = ",sum(organize_done),sum(organize_pass)";
        String fromString = "";
        if (request.get("view").equals("location")) {
            fromString = " FROM assessment.location_score where assessment_id=:assessment";
        } else {
            fromString = " FROM assessment.overview_score where assessment_id=:assessment";
        }

        List<Object[]> resultList = new ArrayList<>();

        if (!request.get("view").equals("") && !request.get("organize").equals("") && !request.get("business").equals("") && !request.get("group").equals("") && !request.get("area").equals("")) {
            queryString = selectString + " party_name" + sumString + ",party_total" + fromString + " and organize_name=:organize and business_name=:business and group_name=:group and area_name=:area and party_name != '' group by party_name";

            System.out.println(queryString);
            Query q = em.createNativeQuery(queryString);
            q.setParameter("assessment", assessment);
            q.setParameter("organize", request.get("organize"));
            q.setParameter("business", request.get("business"));
            q.setParameter("group", request.get("group"));
            q.setParameter("area", request.get("area"));
            resultList = q.getResultList();
        } else if (!request.get("view").equals("") && !request.get("organize").equals("") && !request.get("business").equals("") && !request.get("group").equals("")) {
            queryString = selectString + " area_name" + sumString + ",area_total" + fromString + " and organize_name=:organize and business_name=:business and group_name=:group and area_name != '' group by area_name";

            System.out.println(queryString);
            Query q = em.createNativeQuery(queryString);
            q.setParameter("assessment", assessment);
            q.setParameter("organize", request.get("organize"));
            q.setParameter("business", request.get("business"));
            q.setParameter("group", request.get("group"));
            resultList = q.getResultList();
        } else if (!request.get("view").equals("") && !request.get("organize").equals("") && !request.get("business").equals("")) {
            queryString = selectString + " group_name" + sumString + ",group_total" + fromString + " and organize_name=:organize and business_name=:business and group_name != '' group by group_name";

            System.out.println(queryString);
            Query q = em.createNativeQuery(queryString);
            q.setParameter("assessment", assessment);
            q.setParameter("organize", request.get("organize"));
            q.setParameter("business", request.get("business"));
            resultList = q.getResultList();
        } else if (!request.get("view").equals("") && !request.get("organize").equals("")) {
            queryString = selectString + " business_name" + sumString + ",business_total" + fromString + " and organize_name=:organize and business_name != '' group by business_name";

            System.out.println(queryString);
            Query q = em.createNativeQuery(queryString);
            q.setParameter("assessment", assessment);
            q.setParameter("organize", request.get("organize"));
            resultList = q.getResultList();
        } else if (!request.get("view").equals("")) {
            queryString = selectString + " organize_name" + sumString + ",organize_total" + fromString + " and organize_name != '' group by organize_name";

            System.out.println(queryString);
            Query q = em.createNativeQuery(queryString);
            q.setParameter("assessment", assessment);
            resultList = q.getResultList();
        }

        List<ChartData> chartDatas = new ArrayList<>();
        resultList.stream().map((o) -> {
            ChartData chartData = new ChartData();
            chartData.setName(o[0].toString());
            chartData.setDone((BigDecimal) o[1]);
            chartData.setPass((BigDecimal) o[2]);
            chartData.setTotal((BigDecimal) o[3]);
            return chartData;
        }).forEach((chartData) -> {
            chartDatas.add(chartData);
        });
        return chartDatas;
    }
}
