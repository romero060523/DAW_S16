package com.tecsup.evaluacion03.controller;

import com.tecsup.evaluacion03.client.PaymentClient;
import com.tecsup.evaluacion03.enums.EstadoPedido;
import com.tecsup.evaluacion03.model.DetallePedido;
import com.tecsup.evaluacion03.model.Pedido;
import com.tecsup.evaluacion03.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    private static final Logger log = LoggerFactory.getLogger(PedidoController.class);

    private final PedidoService pedidoService;
    private final PlatoService platoService;
    private final ClienteService clienteService;
    private final MesaService mesaService;
    private final PaymentService paymentService;

    @Value("${stripe.public.key}")
    private String stripePublicKey;

    @Autowired
    public PedidoController(PedidoService pedidoService,
                            PlatoService platoService,
                            ClienteService clienteService,
                            MesaService mesaService,
                            PaymentService paymentService) {
        this.pedidoService = pedidoService;
        this.platoService = platoService;
        this.clienteService = clienteService;
        this.mesaService = mesaService;
        this.paymentService = paymentService;
    }

    /**
        * Endpoint de diagnóstico que devuelve un resumen JSON del pedido (no serializa todo el grafo).
    */
    @GetMapping("/{id}/json")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getPedidoJson(@PathVariable Long id) {
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> out = new HashMap<>();
        out.put("id", pedido.getId());
        out.put("estado", pedido.getEstado());
        out.put("paymentStatus", pedido.getPaymentStatus());
        out.put("stripePaymentIntentId", pedido.getStripePaymentIntentId());
        out.put("fechaHora", pedido.getFechaHora());
        out.put("paidAt", pedido.getPaidAt());
        out.put("total", pedido.getTotal());
        out.put("detallesCount", pedido.getDetalles() != null ? pedido.getDetalles().size() : 0);
        out.put("cliente", pedido.getCliente() != null ? pedido.getCliente().getNombreCompleto() : null);

        return ResponseEntity.ok(out);
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
        if (pedido.getTipoServicio() == null) {
            model.addAttribute("error", "Debe seleccionar un tipo de servicio");
            model.addAttribute("platos", platoService.findAll());
            model.addAttribute("clientes", clienteService.findAll());
            model.addAttribute("mesas", mesaService.findAll());
            return "pedido/form";
        }

        if (pedido.getCliente() != null && pedido.getCliente().getId() != null) {
            pedido.setCliente(clienteService.findById(pedido.getCliente().getId()));
        }
        if (pedido.getMesa() != null && pedido.getMesa().getId() != null) {
            pedido.setMesa(mesaService.findById(pedido.getMesa().getId()));
        }

        if (pedido.getDetalles() != null) {
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

    // ========== PAGO CON STRIPE ==========

    /**
     * Muestra la página de checkout con Stripe
     */
    @GetMapping("/{id}/pagar")
    public String mostrarPaginaPago(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null || pedido.getEstado() != EstadoPedido.SERVIDO) {
            return "redirect:/pedido/servidos";
        }

        // Crear PaymentIntent en Stripe via microservicio
        PaymentClient.PaymentResponse paymentResponse = paymentService.createPaymentIntent(pedido);

        if (paymentResponse == null || paymentResponse.clientSecret() == null) {
            model.addAttribute("error", "Error al inicializar el pago. Intente nuevamente.");
            return "redirect:/pedido/servidos";
        }

        // Guardar el PaymentIntent ID en el pedido
        pedido.setStripePaymentIntentId(paymentResponse.stripePaymentIntentId());
        pedido.setPaymentStatus("PENDING");
        pedidoService.save(pedido);

        model.addAttribute("pedido", pedido);
        model.addAttribute("clientSecret", paymentResponse.clientSecret());
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("totalConIgv", pedido.getTotal().multiply(java.math.BigDecimal.valueOf(1.18)));

        return "pedido/pago";
    }

    /**
     * Callback después de pago exitoso
     */
    @GetMapping("/{id}/pago-exitoso")
    public String pagoExitoso(@PathVariable Long id, @RequestParam(required = false) String payment_intent) {
        Pedido pedido = pedidoService.findById(id);
        if (pedido != null) {
            // Confirmar pago via microservicio
            if (payment_intent != null) {
                PaymentClient.PaymentResponse confirmacion = paymentService.confirmPayment(payment_intent);
                if (confirmacion != null && "COMPLETED".equals(confirmacion.status())) {
                    pedido.setPaymentStatus("PAID");
                    pedido.setPaidAt(LocalDateTime.now());
                }
            }
            pedido.setEstado(EstadoPedido.CERRADO);
            pedidoService.save(pedido);
        }
        return "redirect:/pedido/servidos";
    }

    /**
     * Cerrar pedido con pago en efectivo
     */
    @PostMapping("/{id}/cerrar")
    public String cerrarPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.findById(id);
        if (pedido != null && pedido.getEstado() == EstadoPedido.SERVIDO) {
            pedido.setEstado(EstadoPedido.CERRADO);
            pedido.setPaymentStatus("CASH");
            pedidoService.save(pedido);
        }
        return "redirect:/pedido/servidos";
    }

    // ========== FACTURA ==========

    // @GetMapping("/{id}/factura")
    // public String verFactura(@PathVariable Long id, Model model) {
    //     try {
    //         // Usar findByIdWithRelations para cargar eagerly cliente, mesa y detalles
    //         Pedido pedido = pedidoService.findByIdWithRelations(id);
    //         if (pedido == null || pedido.getEstado() != EstadoPedido.CERRADO) {
    //             log.warn("Factura no disponible para pedido {}. Estado o inexistente.", id);
    //             return "redirect:/pedido?error=factura_no_disponible";
    //         }
    //         model.addAttribute("pedido", pedido);
    //         return "pedido/factura";
    //     } catch (Exception e) {
    //         log.error("Error al mostrar factura para pedido {}: {}", id, e.getMessage(), e);
    //         return "redirect:/pedido?error=error_mostrar_factura";
    //     }
    // }
}
