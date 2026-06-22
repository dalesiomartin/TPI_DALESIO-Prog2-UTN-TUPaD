/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

import entities.Pedido;
import entities.Producto;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;
import exceptions.EntidadNoEncontradaException;
import exceptions.StockInvalidoException;
import java.util.List;
import java.util.Scanner;
import services.PedidoServicio;
import services.ProductoServicio;
import services.UsuarioServicio;
import services.Validaciones;

/**
 *
 * @author Pc
 */
public class PedidoMenu {
    
    private PedidoServicio pedidoServicio;
    private UsuarioServicio usuarioServicio;
    private ProductoServicio productoServicio;
    private Scanner sc;

    public PedidoMenu(PedidoServicio pedidoServicio, UsuarioServicio usuarioServicio, ProductoServicio productoServicio, Scanner sc) {
        this.pedidoServicio = pedidoServicio;
        this.usuarioServicio = usuarioServicio;
        this.productoServicio = productoServicio;
        this.sc = sc;
    }
    
    public void mostrar() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE PEDIDOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear pedido con detalles");
            System.out.println("3. Actualizar estado / forma de pago");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            String opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1": listar(); break;
                case "2": crearPedidoConDetalles(); break;
                case "3": actualizarEstado(); break;
                case "4": eliminar(); break;
                case "0": volver = true; break;
                default: System.out.println("Opción inválida.");
            }
        }
    }
    
    
    private void listar(){
        List<Pedido> pedidos = pedidoServicio.listar();
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos cargados.");
            return;
        }
        for (Pedido p : pedidos) {
            System.out.println(p);
            for (var detalle : p.getDetalles()) {
                if (!detalle.isEliminado()) {
                    System.out.println("   " + detalle);
                }
            }
        }
    }
    
    private void crearPedidoConDetalles(){
        if (Validaciones.esListaVacia(usuarioServicio.listar(), "No hay usuarios cargados en el sistema. Cree un usuario antes de continuar.")) {
            return; 
        }

        // Paso 1: seleccionar usuario
        listarUsuariosDisponibles(); 
        
        Usuario usuario;
        try {
            System.out.print("Ingrese el id del usuario que realiza el pedido: ");
            Long usuarioId = Long.parseLong(sc.nextLine().trim());
            usuario = usuarioServicio.buscarUserPorID(usuarioId);
        } catch (NumberFormatException e) {
            System.out.println("Error: id inválido.");
            return;
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
        
        // Paso 2: forma de pago
        FormaPago formaPago = pedirFormaPago();

        // Paso 3: iniciar el pedido (todavía no se persiste)
        Pedido pedido = pedidoServicio.iniciarPedido(usuario, formaPago);
        
        // Paso 4: cargar 1..N detalles
        boolean seguirCargando = true;
        boolean huboError = false;
        
        while (seguirCargando) { 
            
            listarProductosDisponibles();
            
            System.out.print("Ingrese id del producto a agregar: ");
            Long productoId;
            try {
                productoId = Long.parseLong(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: id inválido.");
                continue;
            }
            
            Producto producto;
            try {
                producto = productoServicio.buscarProdPorID(productoId);
            } catch (EntidadNoEncontradaException e) {
                System.out.println("Error: " + e.getMessage());
                continue;
            }
            
            System.out.print("Cantidad: ");
            int cantidad;
            try {
                cantidad = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: cantidad inválida.");
                continue;
            }
            
            try {
                pedidoServicio.agregarDetalle(pedido, cantidad, producto);
                System.out.println("Detalle agregado correctamente.");
            } catch (StockInvalidoException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Se cancela la creación del pedido.");
                pedidoServicio.descartarPedido(pedido);
                huboError = true;
                seguirCargando = false;
                continue;
            }
            
            System.out.print("¿Agregar otro producto? (S/N): ");
            String otra = sc.nextLine().trim().toUpperCase();
            seguirCargando = otra.equals("S");
        }
        
        // Paso 5: confirmar (si no hubo error)
        if (!huboError) {
            try {
                pedidoServicio.confirmarPedido(pedido);
        
                // TICKET COMPLETO
            System.out.println("\n==================================================");
            System.out.println("          ¡PEDIDO CONFIRMADO CON ÉXITO!           ");
            System.out.println("==================================================");
            System.out.println(pedido); // Invoca al toString() de Pedido
            System.out.println("--------------------------------------------------");
            System.out.println("Detalle de los productos:");
        
            // Recorremos los detalles cargados en el objeto que está en memoria
            for (var detalle : pedido.getDetalles()) {
                if (!detalle.isEliminado()) {
                    System.out.println("   " + detalle); // Invoca al toString() de DetallePedido
                }
            }
        
            System.out.println("--------------------------------------------------");
            System.out.printf(" TOTAL FINAL: $%.2f%n", pedido.getTotal());
            System.out.println("==================================================\n");
        
            } catch (IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    private FormaPago pedirFormaPago(){
        System.out.println("Forma de pago: 1) TARJETA  2) TRANSFERENCIA  3) EFECTIVO");
        System.out.print("Seleccione: ");
        String opcion = sc.nextLine().trim();
        return switch (opcion) {
            case "1" -> FormaPago.TARJETA;
            case "2" -> FormaPago.TRANSFERENCIA;
            case "3" -> FormaPago.EFECTIVO;
            default -> {
                System.out.println("Opción inválida, se asigna EFECTIVO por defecto.");
                yield FormaPago.EFECTIVO;
            }
        };
    }
    
    private void actualizarEstado(){
        // 1. El padre controla de forma directa su condición de ejecución
        if (Validaciones.esListaVacia(pedidoServicio.listar(), "No hay pedidos cargados en el sistema.")) {
            return;
        }
        
        // 2. Si pasó el control anterior, recién ahí invoca los procesos auxiliares
        listar();
        try {
            System.out.print("Ingrese el id del pedido a actualizar: ");
            Long id = Long.parseLong(sc.nextLine().trim());
            pedidoServicio.buscarPedPorID(id); // valida que exista antes de seguir preguntando

            Estado estado = pedirEstado();
            FormaPago formaPago = pedirFormaPago();

            pedidoServicio.actualizarEstadoYFormaPago(id, estado, formaPago);
            System.out.println("Pedido actualizado correctamente.");

        } catch (NumberFormatException e) {
            System.out.println("Error: id inválido.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    
    private Estado pedirEstado(){
        System.out.println("Estado: 1) PENDIENTE  2) CONFIRMADO  3) TERMINADO  4) CANCELADO");
        System.out.print("Seleccione: ");
        String opcion = sc.nextLine().trim();
        return switch (opcion) {
            case "1" -> Estado.PENDIENTE;
            case "2" -> Estado.CONFIRMADO;
            case "3" -> Estado.TERMINADO;
            case "4" -> Estado.CANCELADO;
            default -> {
                System.out.println("Opción inválida, se mantiene PENDIENTE por defecto.");
                yield Estado.PENDIENTE;
            }
        };
    
    }
    
    private void eliminar(){
        if (Validaciones.esListaVacia(pedidoServicio.listar(), "No hay pedidos cargados en el sistema.")) {
            return;
        }
        
        listar();
        try {
            System.out.print("Ingrese el id del pedido a eliminar: ");
            Long id = Long.parseLong(sc.nextLine().trim());

            pedidoServicio.buscarPedPorID(id);
            System.out.print("¿Confirma eliminar el pedido #" + id + "? (S/N): ");
            String confirmacion = sc.nextLine().trim().toUpperCase();

            if (confirmacion.equals("S")) {
                pedidoServicio.eliminar(id);
                System.out.println("Pedido eliminado (baja lógica).");
            } else {
                System.out.println("Operación cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: id inválido.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    
    private void listarUsuariosDisponibles() {
        List<Usuario> usuarios = usuarioServicio.listar();
        if (usuarios.isEmpty()) {
            System.out.println("(No hay usuarios cargados. Cree un usuario antes de continuar.)");
            return;
        }
        System.out.println("Usuarios disponibles:");
        for (Usuario u : usuarios) {
            System.out.println("  " + u.getId() + " - " + u.getNombre() + " " + u.getApellido());
        }
    }
    
     private void listarProductosDisponibles() {
        List<Producto> productos = productoServicio.listar();
        if (productos.isEmpty()) {
            System.out.println("(No hay productos cargados. Cree un producto antes de continuar.)");
            return;
        }
        System.out.println("Productos disponibles:");
        for (Producto p : productos) {
            System.out.println("  " + p.getId() + " - " + p);
        }
    }
    
    
    
    }


