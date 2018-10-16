package com.apap.tugas1.service;

import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService{
    @Autowired
    private ProvinsiDB provinsiDB;


    @Override
    public ProvinsiModel getProvinsiDetailById(BigInteger id) {
        return provinsiDB.findById(id);
    }

    @Override
    public List<ProvinsiModel> getAllProvinsi() {
        return provinsiDB.findAll();
    }
}
