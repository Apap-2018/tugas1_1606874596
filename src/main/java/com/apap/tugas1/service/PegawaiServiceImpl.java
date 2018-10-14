package com.apap.tugas1.service;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
    @Autowired
    private PegawaiDB pegawaiDB;


    @Override
    public PegawaiModel getPegawaiDetailByNip(String nip) {
        return pegawaiDB.findByNip(nip);
    }

    @Override
    public void addPegawai(PegawaiModel pegawai) {
        pegawaiDB.save(pegawai);
    }

    @Override
    public void updatePegawai(PegawaiModel pegawai, String nip) {
        /**PegawaiModel updatedPilot = pegawaiDB.findByNip(nip);
        updatedPegawai.setName(pilot.getName());
        updatedPilot.setFlyHour(pilot.getFlyHour());
        pilotDB.save(updatedPilot); **/
    }
}
