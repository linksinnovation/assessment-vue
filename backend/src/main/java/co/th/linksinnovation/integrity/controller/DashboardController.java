/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.controller;

import co.th.linksinnovation.integrity.model.ChartData;
import co.th.linksinnovation.integrity.repository.ChartDataRepository;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jirawong
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    
    @Autowired
    private ChartDataRepository chartDataRepository;
    
    @PostMapping("/location/{id}")
    public List<ChartData> getLocationScore(@PathVariable Integer id,@RequestBody Map<String,String> request){
        return chartDataRepository.getLocation(id,request);
    }
}
