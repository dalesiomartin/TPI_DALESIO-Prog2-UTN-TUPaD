/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

/**
 *
 * @author Pc
 */
public class PedidoMenu {
    Pedido pedido = pedidoServicio.iniciarPedido(usuarioSeleccionado, formaPagoElegida);
    boolean huboError = false;

    try {
        pedidoServicio.agregarDetalle(pedido, cantidad1, producto1);
        pedidoServicio.agregarDetalle(pedido, cantidad2, producto2);
    } catch (StockInvalidoException e) {
        System.out.println("Error: " + e.getMessage());
        pedidoServicio.descartarPedido(pedido);
        huboError = true;
    }

    if (!huboError) {
        pedidoServicio.confirmarPedido(pedido);
        System.out.println("Pedido creado con id: " + pedido.getId());
    }
}


