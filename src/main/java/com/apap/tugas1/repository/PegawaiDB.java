package com.apap.tugas1.repository;

import com.apap.tugas1.model.PegawaiModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PegawaiDB  extends JpaRepository<PegawaiModel, Long> {
}
