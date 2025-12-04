package com.tecsup.evaluacion03.controller;

import com.tecsup.evaluacion03.model.Rol;
import com.tecsup.evaluacion03.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rol")
public class RolController {

    private final RolService rolService;

    @Autowired
    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public String listarRoles(Model model) {
        List<Rol> roles = rolService.findAll();
        model.addAttribute("roles", roles);
        return "rol/lista";
    }
}