package com.example.starbuckscashier.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {

    @RequestMapping(value = {"/admin/dashboard"}, method = RequestMethod.GET)
    public String adminDashboardPage(){
        return "admin/dashboard";
    }

    @RequestMapping("/starbucks")
    public String starbucksPage() {
        return "starbucks";
    }
}
