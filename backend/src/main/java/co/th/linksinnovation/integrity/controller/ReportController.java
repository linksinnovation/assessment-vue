/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.controller;

import co.th.linksinnovation.integrity.model.LocationScore;
import co.th.linksinnovation.integrity.repository.LocationScoreRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jirawong
 */
@RestController
@RequestMapping("/api/report")
public class ReportController {
    
    @Autowired
    private LocationScoreRepository locationScoreRepository;

    @GetMapping("/{id}")
    public List<LocationScore> getLocationScore(@PathVariable Integer id) {
        return locationScoreRepository.findAll();
    }
}
