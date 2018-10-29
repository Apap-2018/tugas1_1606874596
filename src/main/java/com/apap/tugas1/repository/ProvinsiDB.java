package com.apap.tugas1.repository;

import com.apap.tugas1.model.ProvinsiModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface ProvinsiDB extends JpaRepository<ProvinsiModel, BigInteger> {

}
