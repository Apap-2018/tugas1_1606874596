package com.apap.tugas1.repository;

import com.apap.tugas1.model.PegawaiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface PegawaiDB  extends JpaRepository<PegawaiModel, Long> {
    PegawaiModel findByNip(String nip);

    @Query("SELECT p FROM PegawaiModel p, InstansiModel i JOIN p.jabatan j WHERE i.provinsi.id = :idProvinsi AND i.id = :idInstansi AND j.id = :idJabatan AND p.instansi = i.id")
    List<PegawaiModel> findPegawaiModelByProvInJab(@Param("idProvinsi") BigInteger idProvinsi,
                                     @Param("idInstansi") BigInteger idInstansi,
                                     @Param("idJabatan") BigInteger idJabatan);

}