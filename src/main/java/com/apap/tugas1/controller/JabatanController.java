package com.apap.tugas1.controller;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;


@Controller
public class JabatanController {
    @Autowired
    private JabatanService jabatanService;

    @RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
    private String add(Model model){
        model.addAttribute("jabatan", new JabatanModel());
        return "add-jabatan";
    }

    @RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
    private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan){
        jabatanService.addJabatan(jabatan);
        return "add";
    }

    @RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
    public String view(@RequestParam("id") BigInteger id, Model model){
        JabatanModel jabatan = jabatanService.getJabatanDetailById(id);

        model.addAttribute("jabatan", jabatan);
        return"view-jabatan";
    }
}
