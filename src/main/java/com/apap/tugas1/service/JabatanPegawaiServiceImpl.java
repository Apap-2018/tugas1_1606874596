package com.apap.tugas1.service;

import com.apap.tugas1.repository.JabatanPegawaiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JabatanPegawaiServiceImpl implements JabatanPegawaiService{
    @Autowired
    private JabatanPegawaiDB jabatanPegawaiDB;

}
