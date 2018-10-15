package com.apap.tugas1.service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{
    @Autowired
    private JabatanDB jabatanDB;


    @Override
    public List<JabatanModel> getAllJabatan() {
        return jabatanDB.findAll();
    }

    @Override
    public JabatanModel getJabatanDetailById(BigInteger id) {
        return jabatanDB.findById(id);
    }

}
