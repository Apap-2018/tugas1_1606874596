package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public void updatePegawai(PegawaiModel newPegawai, String nip) {
        System.out.println(nip);
        PegawaiModel oldPegawai = pegawaiDB.findByNip(nip);
        int pegawaiKe = 1;
        System.out.println(newPegawai.getNama());
        System.out.println(oldPegawai.getNama());
        System.out.println("MASUK");

        String pattern = "dd-MM-yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        oldPegawai.setNama(newPegawai.getNama());
        oldPegawai.setTempatLahir(newPegawai.getTempatLahir());

        if (oldPegawai.getInstansi().equals(newPegawai.getInstansi())) {
            String tanggalLahirLama = simpleDateFormat.format(oldPegawai.getTanggalLahir());
            String tanggalLahirBaru = simpleDateFormat.format(newPegawai.getTanggalLahir());
            if (tanggalLahirLama.equals(tanggalLahirBaru)) {

                if (oldPegawai.getTahunMasuk().equals(newPegawai.getTahunMasuk())) {
                    pegawaiKe = (int) (Long.parseLong(oldPegawai.getNip())%100);
                }
                else {
                    oldPegawai.setTahunMasuk(newPegawai.getTahunMasuk());
                    List<PegawaiModel> listPegawaiNIPMirip = this.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(oldPegawai.getInstansi(), oldPegawai.getTanggalLahir(), oldPegawai.getTahunMasuk());
                    if (!listPegawaiNIPMirip.isEmpty()) {
                        pegawaiKe = (int) (Long.parseLong(listPegawaiNIPMirip.get(listPegawaiNIPMirip.size()-1).getNip())%100) + 1;
                    }
                }

            }
            else {
                oldPegawai.setTanggalLahir(newPegawai.getTanggalLahir());
                oldPegawai.setTahunMasuk(newPegawai.getTahunMasuk());
                List<PegawaiModel> listPegawaiNIPMirip = this.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(oldPegawai.getInstansi(), oldPegawai.getTanggalLahir(), oldPegawai.getTahunMasuk());
                if (!listPegawaiNIPMirip.isEmpty()) {
                    pegawaiKe = (int) (Long.parseLong(listPegawaiNIPMirip.get(listPegawaiNIPMirip.size()-1).getNip())%100) + 1;
                }
            }

        }
        else {
            oldPegawai.setTanggalLahir(newPegawai.getTanggalLahir());
            oldPegawai.setTahunMasuk(newPegawai.getTahunMasuk());
            oldPegawai.setInstansi(newPegawai.getInstansi());
            List<PegawaiModel> listPegawaiNIPMirip = this.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(oldPegawai.getInstansi(), oldPegawai.getTanggalLahir(), oldPegawai.getTahunMasuk());
            if (!listPegawaiNIPMirip.isEmpty()) {
                pegawaiKe = (int) (Long.parseLong(listPegawaiNIPMirip.get(listPegawaiNIPMirip.size()-1).getNip())%100) + 1;
            }
        }

        oldPegawai.setJabatan(newPegawai.getJabatan());

        InstansiModel instansi = oldPegawai.getInstansi();
        Date tanggalLahir = oldPegawai.getTanggalLahir();
        String tahunMasuk = oldPegawai.getTahunMasuk();

        String kodeInstansi = instansi.getId().toString();

        String tanggalLahirString = simpleDateFormat.format(tanggalLahir).replaceAll("-", "");
        String pegawaiKeString = pegawaiKe/10 == 0 ? ("0" + Integer.toString(pegawaiKe)) : (Integer.toString(pegawaiKe));
        String nipBaru = kodeInstansi + tanggalLahirString + tahunMasuk + pegawaiKeString;

        oldPegawai.setNip(nipBaru);
        newPegawai.setNip(nipBaru);
    }

    @Override
    public List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk){
        return pegawaiDB.findByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
    }


    @Override
    public List<PegawaiModel> getPegawaiByProvinsi(BigInteger provinsiId) {
        List<PegawaiModel> hasil = new ArrayList<>();

        for(PegawaiModel pegawai : pegawaiDB.findAll()) {
            if (pegawai.getInstansi().getProvinsi().getId() == provinsiId) {
                hasil.add(pegawai);
            }
        }

        return hasil;
    }

    @Override
    public List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi) {
        return pegawaiDB.findByInstansi(instansi);
    }

    @Override
    public List<PegawaiModel> getPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan) {
        List<PegawaiModel> hasil = new ArrayList<>();

        for(PegawaiModel pegawai : pegawaiDB.findByInstansi(instansi)) {
            if (pegawai.getJabatan().contains(jabatan)) {
                hasil.add(pegawai);
            }
        }

        return hasil;
    }

    @Override
    public List<PegawaiModel> getPegawaiByProvinsiAndJabatan(BigInteger provinsiId, JabatanModel jabatan){
        List<PegawaiModel> hasil = new ArrayList<>();

        for(PegawaiModel pegawai : this.getPegawaiByProvinsi(provinsiId)) {
            if (pegawai.getJabatan().contains(jabatan)) {
                hasil.add(pegawai);
            }
        }

        return hasil;
    }

    @Override
    public List<PegawaiModel> getPegawaiByJabatan(JabatanModel jabatan){
        return pegawaiDB.findByJabatan(jabatan);
    }
}
