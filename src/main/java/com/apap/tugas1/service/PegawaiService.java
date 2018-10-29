package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

public interface PegawaiService {
    PegawaiModel getPegawaiDetailByNip(String nip);
    void addPegawai(PegawaiModel pegawai);
    void updatePegawai(PegawaiModel pegawai, String nip);

    List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);

    List<PegawaiModel> getPegawaiByProvinsi(BigInteger provinsiId);

    List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi);

    List<PegawaiModel> getPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan);

    List<PegawaiModel> getPegawaiByProvinsiAndJabatan(BigInteger provinsiId, JabatanModel jabatan);

    List<PegawaiModel> getPegawaiByJabatan(JabatanModel jabatan);

}
