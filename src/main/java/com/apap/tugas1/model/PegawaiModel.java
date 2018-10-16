package com.apap.tugas1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "pegawai")
public class PegawaiModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @NotNull
    @Size(max = 255)
    @Column(name = "NIP", nullable = false, unique = true)
    private String nip;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 255)
    @Column(name = "tempat_lahir", nullable = false)
    private String tempatLahir;

    @NotNull
    @Column(name = "tanggal_lahir")
    private Date tanggalLahir;

    @NotNull
    @Size(max = 255)
    @Column(name = "tahun_masuk")
    private String tahunMasuk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instansi", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private InstansiModel instansi;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "jabatan_pegawai",
            joinColumns = {@JoinColumn(name = "id_pegawai")},
            inverseJoinColumns = {@JoinColumn(name = "id_jabatan")})
    private List<JabatanModel> jabatan;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getTahunMasuk() {
        return tahunMasuk;
    }

    public void setTahunMasuk(String tahunMasuk) {
        this.tahunMasuk = tahunMasuk;
    }

    public InstansiModel getInstansi() {
        return instansi;
    }

    public void setInstansi(InstansiModel instansi) {
        this.instansi = instansi;
    }

    public List<JabatanModel> getJabatan() {
        return jabatan;
    }

    public void setJabatan(List<JabatanModel> jabatan) {
        this.jabatan = jabatan;
    }

    public JabatanModel getJabatanGajiTertinggi() {
        Collections.sort(this.jabatan, Comparator.comparing(JabatanModel::getGajiPokok));
        return this.jabatan.get(0);
    }
}