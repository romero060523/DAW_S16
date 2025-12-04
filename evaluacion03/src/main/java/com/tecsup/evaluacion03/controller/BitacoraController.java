package com.tecsup.evaluacion03.controller;

import com.tecsup.evaluacion03.model.Bitacora;
import com.tecsup.evaluacion03.service.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bitacora")
public class BitacoraController {

    private final BitacoraService bitacoraService;

    @Autowired
    public BitacoraController(BitacoraService bitacoraService) {
        this.bitacoraService = bitacoraService;
    }

    @GetMapping
    public String listarBitacora(Model model) {
        List<Bitacora> bitacora = bitacoraService.findAll();
        model.addAttribute("bitacora", bitacora);
        return "bitacora/lista";
    }
}
