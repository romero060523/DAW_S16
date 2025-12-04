package com.tecsup.evaluacion03.controller;

import com.tecsup.evaluacion03.model.Mesa;
import com.tecsup.evaluacion03.service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/mesa")
public class MesaController {

    private final MesaService mesaService;

    @Autowired
    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @GetMapping
    public String listarMesas(Model model) {
        List<Mesa> mesas = mesaService.findAll();
        model.addAttribute("mesas", mesas);
        return "mesa/lista";
    }
}