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
        List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
        List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
        PegawaiModel pegawai = new PegawaiModel();
        pegawai.setJabatan(new ArrayList<JabatanModel>());
        pegawai.getJabatan().add(new JabatanModel());

        model.addAttribute("pegawai", pegawai);
        model.addAttribute("listProvinsi", listProvinsi);
        model.addAttribute("listJabatan", listJabatan);
        return "add-pegawai";
    }

    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
    private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai){
        pegawaiService.addPegawai(pegawai);
        return "add";
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

    @RequestMapping(value="/pegawai/tambah", params={"addRow"}, method = RequestMethod.POST)
    public String addRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
        pegawai.getJabatan().add(new JabatanModel());
        model.addAttribute("pegawai", pegawai);

        List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
        model.addAttribute("listJabatan", listJabatan);
        return "add-pegawai";
    }

    @RequestMapping(value="/pegawai/tambah", params={"removeRow"}, method = RequestMethod.POST)
    public String removeRow(@ModelAttribute PegawaiModel pegawai, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        pegawai.getJabatan().remove(rowId.intValue());

        model.addAttribute("pegawai", pegawai);
        return "add-pegawai";
    }


    @RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
    public String searchPegawaiResult(@RequestParam(value="provinsi", required = false) BigInteger provinsi,
                                      @RequestParam(value="instansi", required = false) BigInteger instansi,
                                      @RequestParam(value="jabatan", required = false) BigInteger jabatan, Model model){
        List<PegawaiModel> listPegawai = pegawaiService.getAllPegawaiForSearch(provinsi, instansi, jabatan);
        for (int i=0 ; i<listPegawai.size() ;i++){
            System.out.println(listPegawai.get(i));
        }
        List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
        List<JabatanModel> listJabatan = jabatanService.getAllJabatan();

        model.addAttribute("listProvinsi", listProvinsi);
        model.addAttribute("listJabatan", listJabatan);
        model.addAttribute("listPegawai", listPegawai);
        return"search-pegawai";
    }





}
