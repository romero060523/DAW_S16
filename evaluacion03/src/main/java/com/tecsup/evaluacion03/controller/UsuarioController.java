package com.tecsup.evaluacion03.controller;

import com.tecsup.evaluacion03.model.Usuario;
import com.tecsup.evaluacion03.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuario/lista";
    }
}
