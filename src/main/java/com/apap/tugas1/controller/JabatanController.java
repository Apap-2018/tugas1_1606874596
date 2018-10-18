package com.apap.tugas1.controller;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;


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

    @RequestMapping (value = "/jabatan/ubah", method = RequestMethod.GET)
    private String updateJabatan (@RequestParam("id") BigInteger id, Model model) {
        JabatanModel jabatan = jabatanService.getJabatanDetailById(id);
        model.addAttribute("jabatan", jabatan);
        return "update-jabatan";
    }

    @RequestMapping (value = "/jabatan/ubah", method = RequestMethod.POST)
    private String updateJabatanSubmit (@ModelAttribute JabatanModel jabatan) {
        jabatanService.updateJabatan(jabatan, jabatan.getId());
        return "update";
    }

    @RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
    public String viewAll(Model model){
        List<JabatanModel> allJabatan = jabatanService.getAllJabatan();

        model.addAttribute("allJabatan", allJabatan);
        return"viewall-jabatan";
    }

    @RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
    private String deleteJabatan(@RequestParam(value = "id", required = true) BigInteger id) {
        JabatanModel jabatan = jabatanService.getJabatanDetailById(id);
        if (jabatan.getPegawai().isEmpty()){
            jabatanService.deleteJabatan(jabatan);
            return "delete";
        }
        else {
            return "delete-failed";
        }
    }
}
