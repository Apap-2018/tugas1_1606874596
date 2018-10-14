package com.apap.tugas1.controller;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.PegawaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PegawaiController {
    @Autowired
    private PegawaiService pegawaiService;

    @RequestMapping("/")
    private String home() {
        return "home";
    }

    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
    private String add(Model model){
        model.addAttribute("pegawai", new PegawaiModel());
        return "add-pegawai";
    }

    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
    private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai){
        pegawaiService.addPegawai(pegawai);
        return "add";
    }

    @RequestMapping(value = "/pegawai", method = RequestMethod.GET)
    public String view(@RequestParam("nip") String nip, Model model){
        PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
        Double gajiPokok = pegawai.getJabatanGajiTertinggi().getGajiPokok();
        Double tunjangan = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan();
        int gaji = (int) (gajiPokok + (tunjangan * gajiPokok));

        model.addAttribute("pegawai", pegawai);
        model.addAttribute("jabatans", pegawai.getJabatan());
        model.addAttribute("gaji", gaji);
        return"view-pegawai";
    }

}
