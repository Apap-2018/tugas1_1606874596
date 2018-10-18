package com.apap.tugas1.controller;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.ProvinsiService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.util.List;

@Controller
public class ProvinsiController {
    @Autowired
    private ProvinsiService provinsiService;

    @RequestMapping(value = "/provinsi/instansi", method = RequestMethod.GET)
    @ResponseBody
    public List<InstansiModel> findAllInstansi(@RequestParam(value = "id", required = true) BigInteger id) {
        ProvinsiModel provinsi = provinsiService.getProvinsiDetailById(id);

        return provinsi.getInstansiProvinsi();
    }

}
