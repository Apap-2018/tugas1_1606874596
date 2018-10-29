package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.InstansiDB;
import com.apap.tugas1.repository.JabatanPegawaiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService {
    @Autowired
    private InstansiDB instansiDB;

    @Override
    public List<InstansiModel> getAllInstansi() {
        return instansiDB.findAll();
    }

    @Override
    public InstansiModel getInstansiDetailById(BigInteger id) {
        return instansiDB.findById(id).get();
    }
}
