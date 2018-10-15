package com.apap.tugas1.service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface JabatanService {
    List<JabatanModel> getAllJabatan();
    JabatanModel getJabatanDetailById(BigInteger id);

    void addJabatan(JabatanModel jabatan);
    void updateJabatan(JabatanModel jabatan, BigInteger id);


}
