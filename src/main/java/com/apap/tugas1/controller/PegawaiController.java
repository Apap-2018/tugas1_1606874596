package com.apap.tugas1.controller;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
public class PegawaiController {
    @Autowired
    private PegawaiService pegawaiService;

    @Autowired
    private JabatanService jabatanService;

    @Autowired
    private InstansiService instansiService;

    @Autowired
    private ProvinsiService provinsiService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    private String home(Model model) {
        List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
        List<InstansiModel> listInstansi = instansiService.getAllInstansi();

        model.addAttribute("listInstansi", listInstansi);
        model.addAttribute("listJabatan", listJabatan);
        return "home";
    }

    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
    private String add(Model model){
        PegawaiModel pegawai = new PegawaiModel();
        JabatanModel jabatan = new JabatanModel();
        ArrayList<JabatanModel> listJabatan = new ArrayList<JabatanModel>();
        listJabatan.add(jabatan);
        pegawai.setJabatan(listJabatan);

        List<ProvinsiModel> listAllProvinsi = provinsiService.getAllProvinsi();
        List<JabatanModel> listAllJabatan = jabatanService.getAllJabatan();

        model.addAttribute("pegawai", pegawai);
        model.addAttribute("listProvinsi", listAllProvinsi);
        model.addAttribute("listJabatan", listAllJabatan);
        return "add-pegawai";
    }

    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
    private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model){
        System.out.println("Udah masuk");
        pegawaiService.addPegawai(pegawai);
        model.addAttribute("pegawai", pegawai);
        return "add-pegawai-success";
    }

    @RequestMapping(value="/pegawai/tambah", params={"addRow"}, method = RequestMethod.POST)
    public String addRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
        if(pegawai.getJabatan() == null) {
            pegawai.setJabatan(new ArrayList<JabatanModel>());
        }

        pegawai.getJabatan().add(new JabatanModel());

        List<JabatanModel> listAllJabatan = jabatanService.getAllJabatan();
        List<ProvinsiModel> listAllProvinsi = provinsiService.getAllProvinsi();

        model.addAttribute("pegawai", pegawai);
        model.addAttribute("listProvinsi", listAllProvinsi);
        model.addAttribute("listJabatan", listAllJabatan);
        return "add-pegawai";
    }

    @RequestMapping(value="/pegawai/tambah", params={"removeRow"}, method = RequestMethod.POST)
    public String removeRow(@ModelAttribute PegawaiModel pegawai, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        pegawai.getJabatan().remove(rowId.intValue());

        model.addAttribute("pegawai", pegawai);

        List<JabatanModel> listAllJabatan = jabatanService.getAllJabatan();
        List<ProvinsiModel> listAllProvinsi = provinsiService.getAllProvinsi();

        model.addAttribute("listProvinsi", listAllProvinsi);
        model.addAttribute("listJabatan", listAllJabatan);
        return "add-pegawai";
    }

    @RequestMapping(value = "/pegawai", method = RequestMethod.GET)
    public String view(@RequestParam("nip") String nip, Model model){
        PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
        Double gajiPokok = pegawai.getJabatanGajiTertinggi().getGajiPokok();
        Double tunjangan = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan();
        int gaji = (int) (gajiPokok + (tunjangan * gajiPokok));

        model.addAttribute("pegawai", pegawai);
        model.addAttribute("jabatans", pegawai.getJabatan());
        model.addAttribute("gaji", gaji);
        return"view-pegawai";
    }

    @RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
    public String viewTermudaTertua(@RequestParam("id") BigInteger id, Model model){
        InstansiModel instansi = instansiService.getInstansiDetailById(id);

        PegawaiModel pegawaiTermuda = instansi.getPegawaiByTermuda();
        PegawaiModel pegawaiTertua = instansi.getPegawaiByTertua();

        Double gajiPokokTermuda = pegawaiTermuda.getJabatanGajiTertinggi().getGajiPokok();
        Double tunjanganTermuda = pegawaiTermuda.getInstansi().getProvinsi().getPresentaseTunjangan();
        int gajiTermuda = (int) (gajiPokokTermuda + (tunjanganTermuda * gajiPokokTermuda));

        Double gajiPokokTertua = pegawaiTertua.getJabatanGajiTertinggi().getGajiPokok();
        Double tunjanganTertua = pegawaiTertua.getInstansi().getProvinsi().getPresentaseTunjangan();
        int gajiTertua = (int) (gajiPokokTertua + (tunjanganTertua * gajiPokokTertua));

        model.addAttribute("pegawaiTermuda", pegawaiTermuda);
        model.addAttribute("jabatansTermuda", pegawaiTermuda.getJabatan());
        model.addAttribute("gajiTermuda", gajiTermuda);

        model.addAttribute("pegawaiTertua", pegawaiTertua);
        model.addAttribute("jabatansTertua", pegawaiTertua.getJabatan());
        model.addAttribute("gajiTertua", gajiTertua);
        return"view-termudatertua";
    }


    @RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
    public String searchPegawaiResult(@RequestParam(value="provinsi", required = false) BigInteger provinsiId,
                                      @RequestParam(value="instansi", required = false) BigInteger instansiId,
                                      @RequestParam(value="jabatan", required = false) BigInteger jabatanId, Model model){

        ProvinsiModel provinsi = null;
        InstansiModel instansi = null;
        JabatanModel jabatan = null;

        List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
        model.addAttribute("listJabatan", new HashSet(listJabatan));

        List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
        model.addAttribute("listProvinsi", listProvinsi);

        List<PegawaiModel> hasilPencarian = null;
        if (provinsiId != null) {
            provinsi = provinsiService.getProvinsiDetailById(provinsiId);
            if (instansiId != null) {
                instansi = instansiService.getInstansiDetailById(instansiId);
                if (jabatanId != null) {
                    jabatan = jabatanService.getJabatanDetailById(jabatanId);
                    hasilPencarian = pegawaiService.getPegawaiByInstansiAndJabatan(instansi, jabatan);
                }
                else {
                    hasilPencarian = pegawaiService.getPegawaiByInstansi(instansi);
                }
            }
            else if (jabatanId != null) {
                jabatan = jabatanService.getJabatanDetailById(jabatanId);
                hasilPencarian = pegawaiService.getPegawaiByProvinsiAndJabatan(provinsiId, jabatan);
            }
            else {
                hasilPencarian = pegawaiService.getPegawaiByProvinsi(provinsiId);
            }
        }
        else {
            if (jabatanId != null) {
                jabatan = jabatanService.getJabatanDetailById(jabatanId);
                hasilPencarian = pegawaiService.getPegawaiByJabatan(jabatan);
            }
        }
        model.addAttribute("provinsi", provinsi);
        model.addAttribute("instansi", instansi);
        model.addAttribute("jabatan", jabatan);
        model.addAttribute("hasilPencarian", hasilPencarian);

        return"search-pegawai";
    }

    @RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
    public String tambahPegawai (@RequestParam String nip, Model model) {
        PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);

        List<ProvinsiModel> listAllProvinsi = provinsiService.getAllProvinsi();
        List<JabatanModel> listAllJabatan = jabatanService.getAllJabatan();
        List<InstansiModel> listInstansi = instansiService.getInstansiFromProvinsi(pegawai.getInstansi().getProvinsi());

        model.addAttribute("pegawai", pegawai);
        model.addAttribute("listProvinsi", listAllProvinsi);
        model.addAttribute("listJabatan", listAllJabatan);
        model.addAttribute("listInstansi", listInstansi);

        return "update-pegawai";
    }

    @RequestMapping(value="/pegawai/ubah", params={"addRow"}, method = RequestMethod.POST)
    private String addRowUbah(@ModelAttribute PegawaiModel pegawai, Model model) {
        pegawai.getJabatan().add(new JabatanModel());

        List<InstansiModel> listInstansi = instansiService.getInstansiFromProvinsi(pegawai.getInstansi().getProvinsi());
        List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
        List<JabatanModel> listJabatan = jabatanService.getAllJabatan();

        model.addAttribute("pegawai", pegawai);
        model.addAttribute("listJabatan", listJabatan);
        model.addAttribute("listInstansi", listInstansi);
        model.addAttribute("listProvinsi", listProvinsi);

        return "update-pegawai";
    }

    @RequestMapping(value="/pegawai/ubah", params={"removeRow"}, method = RequestMethod.POST)
    private String deleteRowUbah(@ModelAttribute PegawaiModel pegawai, Model model, HttpServletRequest req) {
        Integer rowId =  Integer.valueOf(req.getParameter("removeRow"));
        pegawai.getJabatan().remove(rowId.intValue());

        List<InstansiModel> listInstansi = instansiService.getInstansiFromProvinsi(pegawai.getInstansi().getProvinsi());
        List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
        List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();

        model.addAttribute("pegawai", pegawai);
        model.addAttribute("listInstansi", listInstansi);
        model.addAttribute("listJabatan", listJabatan);
        model.addAttribute("listProvinsi", listProvinsi);

        return "update-pegawai";
    }

    @RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
    public String updatePegawai (@ModelAttribute PegawaiModel pegawai, Model model) {
        String nipLama = pegawai.getNip();
        pegawaiService.updatePegawai(pegawai, nipLama);
        model.addAttribute("pegawai", pegawai);
        return "update-pegawai-success";
    }






}
