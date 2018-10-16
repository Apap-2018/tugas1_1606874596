package com.apap.tugas1.service;

import com.apap.tugas1.model.ProvinsiModel;

import java.math.BigInteger;
import java.util.List;

public interface ProvinsiService {
    ProvinsiModel getProvinsiDetailById(BigInteger id);
    List<ProvinsiModel> getAllProvinsi();


}
