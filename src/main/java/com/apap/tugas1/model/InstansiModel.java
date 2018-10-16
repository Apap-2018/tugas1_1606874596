package com.apap.tugas1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Comparator;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "instansi")
public class InstansiModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 255)
    @Column(name = "deskripsi", nullable = false)
    private String deskripsi;

    @JsonIgnore
    @OneToMany(mappedBy = "instansi", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PegawaiModel> pegawaiInstansi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_provinsi", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private ProvinsiModel provinsi;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public List<PegawaiModel> getPegawaiInstansi() {
        return pegawaiInstansi;
    }

    public void setPegawaiInstansi(List<PegawaiModel> pegawaiInstansi) {
        this.pegawaiInstansi = pegawaiInstansi;
    }

    public ProvinsiModel getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(ProvinsiModel provinsi) {
        this.provinsi = provinsi;
    }

    public PegawaiModel getPegawaiByTermuda() {
        Collections.sort(this.pegawaiInstansi, Comparator.comparing(PegawaiModel::getTanggalLahir));
        return this.pegawaiInstansi.get(pegawaiInstansi.size()-1);
    }

    public PegawaiModel getPegawaiByTertua() {
        Collections.sort(this.pegawaiInstansi, Comparator.comparing(PegawaiModel::getTanggalLahir));
        return this.pegawaiInstansi.get(0);
    }
}