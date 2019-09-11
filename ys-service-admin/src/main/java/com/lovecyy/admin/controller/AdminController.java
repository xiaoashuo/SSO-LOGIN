package com.lovecyy.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ys
 * @topic
 * @date 2019/9/10 16:53
 */
@Controller
public class AdminController {

    @GetMapping("index")
    public String index(){
        return "index";
    }
}
