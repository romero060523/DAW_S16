package com.tecsup.evaluacion03.config;

import com.tecsup.evaluacion03.enums.EstadoMesa;
import com.tecsup.evaluacion03.enums.EstadoPedido;
import com.tecsup.evaluacion03.enums.TipoPlato;
import com.tecsup.evaluacion03.enums.TipoServicio;
import com.tecsup.evaluacion03.model.*;
import com.tecsup.evaluacion03.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PlatoRepository platoRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Roles
        if (rolRepository.count() == 0) {
            Rol admin = new Rol("ADMIN", "Administrador del sistema");
            Rol mozo = new Rol("MOZO", "Personal de servicio (mozo)");
            Rol cocinero = new Rol("COCINERO", "Personal de cocina");
            rolRepository.saveAll(Arrays.asList(admin, mozo, cocinero));
            System.out.println("[DataLoader] Roles creados");
        }

        // Usuarios
        if (usuarioRepository.count() == 0) {
            Rol adminRole = rolRepository.findByNombre("ADMIN").orElse(null);
            Rol mozoRole = rolRepository.findByNombre("MOZO").orElse(null);
            Rol cocineroRole = rolRepository.findByNombre("COCINERO").orElse(null);

            Usuario admin = new Usuario("admin", passwordEncoder.encode("admin123"), "Admin", "Sistema");
            admin.setRoles(Arrays.asList(adminRole));

            Usuario mozo = new Usuario("mozo1", passwordEncoder.encode("mozo123"), "Jose", "Lopez");
            mozo.setRoles(Arrays.asList(mozoRole));

            Usuario cocinero = new Usuario("coc1", passwordEncoder.encode("coco123"), "Ana", "Gonzales");
            cocinero.setRoles(Arrays.asList(cocineroRole));

            usuarioRepository.saveAll(Arrays.asList(admin, mozo, cocinero));
            System.out.println("[DataLoader] Usuarios creados");
        }

        // Platos: crear un conjunto pequeño y realista
        if (platoRepository.count() == 0) {
            List<Plato> platos = new ArrayList<>();
            platos.add(new Plato("Ceviche", TipoPlato.ENTRADA, new BigDecimal("25.00"), "Ceviche clasico con limon", true));
            platos.add(new Plato("Papa a la Huancaina", TipoPlato.ENTRADA, new BigDecimal("18.00"), "Entrada tradicional", true));
            platos.add(new Plato("Lomo Saltado", TipoPlato.FONDO, new BigDecimal("42.50"), "Lomo salteado con papas", true));
            platos.add(new Plato("Aji de Gallina", TipoPlato.FONDO, new BigDecimal("35.00"), "Guiso de pollo en salsa de ají", true));
            platos.add(new Plato("Arroz con Leche", TipoPlato.POSTRE, new BigDecimal("12.50"), "Postre tradicional", true));
            platos.add(new Plato("Suspiro a la Limeña", TipoPlato.POSTRE, new BigDecimal("14.00"), "Dulce tipico", true));
            platos.add(new Plato("Inca Kola 500ml", TipoPlato.BEBIDA, new BigDecimal("6.00"), "Gaseosa peruana", true));
            platos.add(new Plato("Coca Cola 500ml", TipoPlato.BEBIDA, new BigDecimal("6.00"), "Bebida gaseosa", true));
            platos.add(new Plato("Tallarin Saltado", TipoPlato.FONDO, new BigDecimal("30.00"), "Tallarin a la peruana", true));
            platos.add(new Plato("Causa Rellena", TipoPlato.ENTRADA, new BigDecimal("22.00"), "Causa fría rellena", true));
            platos.add(new Plato("Pisco Sour", TipoPlato.BEBIDA, new BigDecimal("18.00"), "Coctel tradicional", true));
            platos.add(new Plato("Pollo a la Brasa", TipoPlato.FONDO, new BigDecimal("45.00"), "Pollo rostizado con papas", true));
            platoRepository.saveAll(platos);
            System.out.println("[DataLoader] Platos creados: " + platos.size());
        }

        // Mesas (pequeño conjunto)
        if (mesaRepository.count() == 0) {
            List<Mesa> mesas = Arrays.asList(
                    new Mesa("M1", 4, EstadoMesa.DISPONIBLE),
                    new Mesa("M2", 2, EstadoMesa.DISPONIBLE),
                    new Mesa("M3", 6, EstadoMesa.DISPONIBLE),
                    new Mesa("M4", 4, EstadoMesa.DISPONIBLE),
                    new Mesa("M5", 8, EstadoMesa.DISPONIBLE),
                    new Mesa("M6", 2, EstadoMesa.DISPONIBLE)
            );
            mesaRepository.saveAll(mesas);
            System.out.println("[DataLoader] Mesas creadas: " + mesas.size());
        }

        // Clientes (10 registros realistas sintéticos)
        if (clienteRepository.count() == 0) {
            List<Cliente> clientes = Arrays.asList(
                    new Cliente("10123456", "Juan", "Perez", "999111222", "juan.perez@example.com"),
                    new Cliente("10234567", "Maria", "Lopez", "999333444", "maria.lopez@example.com"),
                    new Cliente("10345678", "Carlos", "Gonzales", "999555666", "carlos.g@example.com"),
                    new Cliente("10456789", "Ana", "Martinez", "999777888", "ana.martinez@example.com"),
                    new Cliente("10567890", "Luis", "Ramirez", "999000111", "luis.ramirez@example.com"),
                    new Cliente("10678901", "Sofia", "Castro", "999222333", "sofia.castro@example.com"),
                    new Cliente("10789012", "Diego", "Vargas", "999444555", "diego.vargas@example.com"),
                    new Cliente("10890123", "Lucia", "Quispe", "999666777", "lucia.quispe@example.com"),
                    new Cliente("10901234", "Miguel", "Flores", "999888999", "miguel.flores@example.com"),
                    new Cliente("11012345", "Elena", "Sanchez", "999121212", "elena.sanchez@example.com")
            );
            clienteRepository.saveAll(clientes);
            System.out.println("[DataLoader] Clientes creados: " + clientes.size());
        }

        // Pedidos: generar ~10 pedidos distribuidos entre clientes y mesas
        if (pedidoRepository.count() == 0) {
            List<Cliente> clientes = clienteRepository.findAll();
            List<Mesa> mesas = mesaRepository.findAll();
            List<Plato> platos = platoRepository.findAll();

            Random rnd = new Random(42);
            int pedidosCrear = 10;

            for (int i = 0; i < pedidosCrear; i++) {
                Cliente cliente = clientes.get(rnd.nextInt(clientes.size()));
                Mesa mesa = mesas.get(rnd.nextInt(mesas.size()));

                Pedido pedido = new Pedido(EstadoPedido.PENDIENTE, BigDecimal.ZERO);
                pedido.setCliente(cliente);
                pedido.setMesa(mesa);
                pedido.setTipoServicio(TipoServicio.MESA);
                pedido.setObservaciones("Pedido de prueba " + (i + 1));

                int items = 1 + rnd.nextInt(3); // 1..3 platos
                for (int j = 0; j < items; j++) {
                    Plato plato = platos.get(rnd.nextInt(platos.size()));
                    int cantidad = 1 + rnd.nextInt(3);
                    DetallePedido detalle = new DetallePedido(plato, cantidad);
                    pedido.agregarDetalle(detalle);
                }

                pedidoRepository.save(pedido);
            }
            System.out.println("[DataLoader] Pedidos creados: " + pedidosCrear);
        }
    }
}
