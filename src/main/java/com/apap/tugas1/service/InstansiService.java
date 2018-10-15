package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;

import java.math.BigInteger;
import java.util.List;

public interface InstansiService {
    List<InstansiModel> getAllInstansi();
    InstansiModel getInstansiDetailById(BigInteger id);


}
