package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

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
    public List<PegawaiModel> getAllPegawaiForSearch(BigInteger idProvinsi, BigInteger idInstansi, BigInteger idJabatan) {
        return pegawaiDB.findPegawaiModelByProvInJab(idProvinsi, idInstansi, idJabatan);
    }

    @Override
    public void addPegawai(PegawaiModel pegawai) {
        InstansiModel instansi = pegawai.getInstansi();
        Date tanggalLahir = pegawai.getTanggalLahir();
        String tahunMasuk = pegawai.getTahunMasuk();
        int pegawaiKe = 1;

        List<PegawaiModel> listPegawaiNIPMirip = this.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
        if (!listPegawaiNIPMirip.isEmpty()) {
            pegawaiKe = (int) (Long.parseLong(listPegawaiNIPMirip.get(listPegawaiNIPMirip.size()-1).getNip())%100) + 1;
        }

        String kodeInstansi = instansi.getId().toString();

        String pattern = "dd-MM-yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String tanggalLahirString = simpleDateFormat.format(tanggalLahir).replaceAll("-", "");
        String pegawaiKeString = pegawaiKe/10 == 0 ? ("0" + Integer.toString(pegawaiKe)) : (Integer.toString(pegawaiKe));
        String nip = kodeInstansi + tanggalLahirString + tahunMasuk + pegawaiKeString;

        pegawai.setNip(nip);
        pegawaiDB.save(pegawai);
    }

    @Override
    public void updatePegawai(PegawaiModel pegawai, String nip) {
        /**PegawaiModel updatedPilot = pegawaiDB.findByNip(nip);
        updatedPegawai.setName(pilot.getName());
        updatedPilot.setFlyHour(pilot.getFlyHour());
        pilotDB.save(updatedPilot); **/
    }

    @Override
    public List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk){
        return pegawaiDB.findByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
    }
}
