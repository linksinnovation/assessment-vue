/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Jirawong
 */
@Controller
@RequestMapping("/")
public class IndexController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }

    @RequestMapping(value = {"{path:(?!oauth|api|assets|error|images|ViewerJS|files|flowplayer|static).*$}", "{path:(?!oauth|api|assets|error|images|ViewerJS|files|flowplayer|static).*$}/**"}, method = RequestMethod.GET)
    public String index(@PathVariable("path") String path, Model model) {
        System.out.println("hahahaha");
        return "index";
    }
}
