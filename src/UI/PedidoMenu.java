package UI;

import Exceptions.EntityNotFoundException;
import entities.Pedido;
import enums.FormaPago;
import java.util.List;
import java.util.Scanner;
import service.PedidoService;

public class PedidoMenu {
    private final PedidoService pedidoService;
    private final UsuarioMenu usuarioMenu;
    private final ProductoMenu productoMenu;
    private final ConsolaHelper consola;

    public PedidoMenu(PedidoService pedidoService, UsuarioMenu usuarioMenu, ProductoMenu productoMenu, ConsolaHelper consola) {
        this.pedidoService = pedidoService;
        this.usuarioMenu = usuarioMenu;
        this.productoMenu = productoMenu;
        this.consola = consola;
    }

    public void mostrarMenu() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Pedidos ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear pedido con detalles");
            System.out.println("3. Actualizar estado / forma de pago");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            switch (consola.leerEntero("Seleccione: ")) {
                case 1 -> listar();
                case 2 -> crearConDetalles();
                case 3 -> actualizar();
                case 4 -> eliminar();
                case 0 -> volver = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    public void listar() {
        List<Pedido> pedidos = pedidoService.listar();
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos cargados.");
            return;
        }
        pedidos.forEach(System.out::println);
    }

    private void crearConDetalles() {
        try {
            usuarioMenu.listar();
            Long usuarioId = consola.leerLong("Id del usuario: ");
            FormaPago formaPago = consola.leerFormaPago();

            Pedido pedido = pedidoService.iniciarPedido(usuarioId, formaPago);

            boolean agregarMas = true;
            while (agregarMas) {
                productoMenu.listar();
                Long productoId = consola.leerLong("Id del producto a agregar: ");
                int cantidad = consola.leerEntero("Cantidad: ");
                try {
                    pedidoService.agregarDetalle(pedido, productoId, cantidad);
                    System.out.println("Detalle agregado.");
                } catch (EntityNotFoundException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                agregarMas = consola.confirmar("¿Desea agregar otro producto? (S/N): ");
            }

            Pedido confirmado = pedidoService.confirmarPedido(pedido);
            System.out.println("Pedido registrado con id " + confirmado.getId() + ". Total: $" + confirmado.getTotal());
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void actualizar() {
        listar();
        Long id = consola.leerLong("Ingrese el id del pedido: ");
        try {
            System.out.println("1. Cambiar estado");
            System.out.println("2. Cambiar forma de pago");
            int opcion = consola.leerEntero("Seleccione: ");
            if (opcion == 1) {
                pedidoService.actualizarEstado(id, consola.leerEstado());
                System.out.println("Estado actualizado correctamente.");
            } else if (opcion == 2) {
                pedidoService.actualizarFormaPago(id, consola.leerFormaPago());
                System.out.println("Forma de pago actualizada correctamente.");
            } else {
                System.out.println("Opción inválida.");
            }
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        listar();
        Long id = consola.leerLong("Ingrese el id del pedido a eliminar: ");
        if (!consola.confirmar("¿Confirma la eliminación del pedido " + id + "? (S/N): ")) {
            System.out.println("Operación cancelada.");
            return;
        }
        try {
            pedidoService.eliminar(id);
            System.out.println("Pedido eliminado (baja lógica).");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
