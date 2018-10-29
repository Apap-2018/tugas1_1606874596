package com.apap.tugas1.repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface InstansiDB extends JpaRepository<InstansiModel, BigInteger> {
    List<InstansiModel> findByProvinsi(ProvinsiModel provinsi);

}
