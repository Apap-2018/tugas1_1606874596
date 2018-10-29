package com.apap.tugas1.repository;

import com.apap.tugas1.model.JabatanPegawaiModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface JabatanPegawaiDB  extends JpaRepository<JabatanPegawaiModel, BigInteger> {
}
