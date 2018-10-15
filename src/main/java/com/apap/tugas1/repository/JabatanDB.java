package com.apap.tugas1.repository;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface JabatanDB extends JpaRepository<JabatanModel, Long> {
    JabatanModel findById(BigInteger id);
}
