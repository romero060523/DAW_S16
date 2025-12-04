package com.tecsup.evaluacion03.controller;


import com.tecsup.evaluacion03.model.Plato;
import com.tecsup.evaluacion03.service.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/plato")
public class PlatoController {

    private final PlatoService platoService;

    @Autowired
    public PlatoController(PlatoService platoService) {
        this.platoService = platoService;
    }

    @GetMapping
    public String getAllPlatos(Model model) {
        List<Plato> platos = platoService.findAll();
        model.addAttribute("platos", platos);
        return "plato/lista";
    }

    @GetMapping("/activos")
    public String getAllActivos(Model model) {
        List<Plato> platos = platoService.findActivos();
        model.addAttribute("platos", platos);
        return "plato/activos";
    }
    

}
