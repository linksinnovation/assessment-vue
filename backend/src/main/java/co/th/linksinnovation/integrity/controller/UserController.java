/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.controller;

import co.th.linksinnovation.integrity.model.Authority;
import co.th.linksinnovation.integrity.model.UserDetails;
import co.th.linksinnovation.integrity.repository.UserDetailsRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jirawong
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @GetMapping
    public List<UserDetails> get() {
        return userDetailsRepository.findAll();
    }

    @PostMapping("/search")
    public List<UserDetails> search(@RequestBody Object keyword) {
        String word = "%".concat(keyword.toString()).concat("%");
        return userDetailsRepository.findByNameEnIgnoreCaseContainingOrNameThContaining(word, word);
    }

    @PostMapping("/update")
    public void updateAuthority(@RequestBody UserDetails userDetails) {
        List<Authority> authorities = new ArrayList<>();
        Authority authority = new Authority();
        if (userDetails.getAuthorities().get(0).getAuthority().equals("Administrator")) {
            authority.setAuthority("User");
        }else{
            authority.setAuthority("Administrator");
        }
        authorities.add(authority);

        UserDetails findOne = userDetailsRepository.findOne(userDetails.getUsername());

        System.out.println(userDetails.getUsername());

        findOne.setAuthorities(authorities);
        userDetailsRepository.save(findOne);
    }

}
