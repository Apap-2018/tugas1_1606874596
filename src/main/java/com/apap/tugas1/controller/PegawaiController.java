package com.apap.tugas1.controller;

import com.apap.tugas1.service.PegawaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PegawaiController {
    @Autowired
    private PegawaiService pegawaiService;

    @RequestMapping("/")
    private String home() {
        return "home";
    }

}
