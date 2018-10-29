package com.apap.tugas1.repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

public interface PegawaiDB  extends JpaRepository<PegawaiModel, BigInteger> {
    PegawaiModel findByNip(String nip);


    List<PegawaiModel> findByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);
    List<PegawaiModel> findByInstansi(InstansiModel instansi);
    List<PegawaiModel> findByJabatan(JabatanModel jabatan);
}

