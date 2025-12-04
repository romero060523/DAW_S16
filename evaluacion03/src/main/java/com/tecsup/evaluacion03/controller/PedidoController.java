package com.tecsup.evaluacion03.controller;

import com.tecsup.evaluacion03.enums.EstadoPedido;
import com.tecsup.evaluacion03.model.DetallePedido;
import com.tecsup.evaluacion03.model.Pedido;
import com.tecsup.evaluacion03.service.ClienteService;
import com.tecsup.evaluacion03.service.MesaService;
import com.tecsup.evaluacion03.service.PedidoService;
import com.tecsup.evaluacion03.service.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PlatoService platoService;
    private final ClienteService clienteService;
    private final MesaService mesaService;

    @Autowired
    public PedidoController(PedidoService pedidoService,
                            PlatoService platoService,
                            ClienteService clienteService,
                            MesaService mesaService) {
        this.pedidoService = pedidoService;
        this.platoService = platoService;
        this.clienteService = clienteService;
        this.mesaService = mesaService;
    }

    @GetMapping
    public String listarPedidos(Model model) {
        List<Pedido> pedidos = pedidoService.findAll();
        model.addAttribute("pedidos", pedidos);
        return "pedido/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoPedido(Model model) {
        Pedido pedido = new Pedido();
        // Precrear 3 filas de detalle para el formulario
        for (int i = 0; i < 3; i++) {
            pedido.getDetalles().add(new DetallePedido());
        }
        model.addAttribute("pedido", pedido);
        model.addAttribute("platos", platoService.findAll());
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("mesas", mesaService.findAll());
        return "pedido/form";
    }

    @PostMapping("/crear")
    public String crearPedido(@ModelAttribute Pedido pedido, Model model) {
        // Validación básica manual
        if (pedido.getTipoServicio() == null) {
            model.addAttribute("error", "Debe seleccionar un tipo de servicio");
            model.addAttribute("platos", platoService.findAll());
            model.addAttribute("clientes", clienteService.findAll());
            model.addAttribute("mesas", mesaService.findAll());
            return "pedido/form";
        }

        // Re-fetch Cliente y Mesa completos desde DB para evitar referencias transitorias
        if (pedido.getCliente() != null && pedido.getCliente().getId() != null) {
            pedido.setCliente(clienteService.findById(pedido.getCliente().getId()));
        }
        if (pedido.getMesa() != null && pedido.getMesa().getId() != null) {
            pedido.setMesa(mesaService.findById(pedido.getMesa().getId()));
        }

        if (pedido.getDetalles() != null) {
            // eliminar filas vacias
            pedido.getDetalles().removeIf(d -> d.getPlato() == null || d.getPlato().getId() == null || d.getCantidad() == null || d.getCantidad() <= 0);
            for (DetallePedido d : pedido.getDetalles()) {
                Long platoId = d.getPlato().getId();
                if (platoId != null) {
                    d.setPlato(platoService.findById(platoId));
                    d.setPrecioUnitario(d.getPlato().getPrecio());
                    d.calcularSubtotal();
                    d.setPedido(pedido);
                }
            }
        }

        pedido.calcularTotal();
        pedidoService.save(pedido);
        return "redirect:/pedido?success=true";
    }

    // ========== VISTAS POR ROL ==========

    @GetMapping("/mozo")
    public String vistaMozo(Model model) {
        List<Pedido> pendientes = pedidoService.findByEstado(EstadoPedido.PENDIENTE);
        model.addAttribute("pedidos", pendientes);
        return "pedido/mozo";
    }

    @GetMapping("/cocina")
    public String vistaCocina(Model model) {
        List<Pedido> enPreparacion = pedidoService.findByEstado(EstadoPedido.EN_PREPARACION);
        model.addAttribute("pedidos", enPreparacion);
        return "pedido/cocina";
    }

    @GetMapping("/servidos")
    public String vistaServidos(Model model) {
        List<Pedido> servidos = pedidoService.findByEstado(EstadoPedido.SERVIDO);
        model.addAttribute("pedidos", servidos);
        return "pedido/servidos";
    }

    // ========== CAMBIOS DE ESTADO ==========

    @PostMapping("/{id}/enviar-cocina")
    public String enviarACocina(@PathVariable Long id) {
        Pedido pedido = pedidoService.findById(id);
        if (pedido != null && pedido.getEstado() == EstadoPedido.PENDIENTE) {
            pedido.setEstado(EstadoPedido.EN_PREPARACION);
            pedidoService.save(pedido);
        }
        return "redirect:/pedido/mozo";
    }

    @PostMapping("/{id}/marcar-servido")
    public String marcarServido(@PathVariable Long id) {
        Pedido pedido = pedidoService.findById(id);
        if (pedido != null && pedido.getEstado() == EstadoPedido.EN_PREPARACION) {
            pedido.setEstado(EstadoPedido.SERVIDO);
            pedidoService.save(pedido);
        }
        return "redirect:/pedido/cocina";
    }

    @PostMapping("/{id}/cerrar")
    public String cerrarPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.findById(id);
        if (pedido != null && pedido.getEstado() == EstadoPedido.SERVIDO) {
            pedido.setEstado(EstadoPedido.CERRADO);
            pedidoService.save(pedido);
        }
        return "redirect:/pedido/servidos";
    }

    // ========== FACTURA ==========

    @GetMapping("/{id}/factura")
    public String verFactura(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null || pedido.getEstado() != EstadoPedido.CERRADO) {
            return "redirect:/pedido";
        }
        model.addAttribute("pedido", pedido);
        return "pedido/factura";
    }
}
