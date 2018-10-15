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

    @Override
    public void addJabatan(JabatanModel jabatan) {
        jabatanDB.save(jabatan);
    }

    @Override
    public void updateJabatan(JabatanModel jabatan, BigInteger id) {
        JabatanModel updatedJabatan = jabatanDB.findById(id);
        updatedJabatan.setNama(jabatan.getNama());
        updatedJabatan.setDeskripsi(jabatan.getDeskripsi());
        updatedJabatan.setGajiPokok(jabatan.getGajiPokok());
        jabatanDB.save(updatedJabatan);
    }

}
