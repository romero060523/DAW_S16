package com.tecsup.evaluacion03.controller;

import com.tecsup.evaluacion03.model.Cliente;
import com.tecsup.evaluacion03.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteService.findAll();
        model.addAttribute("clientes", clientes);
        return "cliente/lista";
    }
}
