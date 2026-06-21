/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Pedido;
import entities.Producto;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;
import exceptions.EntidadNoEncontradaException;
import exceptions.StockInvalidoException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pc
 */
public class PedidoServicio {
    private List<Pedido>pedidos;
    private Long contadorId;
    private Long contadorDetalleId;

    public PedidoServicio() {
        pedidos = new ArrayList<>();
        this.contadorId = 1L;
        this.contadorDetalleId = 1L;
    }
    
    public List<Pedido> listar(){
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.isEliminado()) {
                resultado.add(p);
            }
        }
        return resultado; 
    }
    
    //Lista pedidos por usuario
    public List<Pedido> listarPorUsuario(Long userId){
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.isEliminado() && p.getUsuario().getId().equals(userId)) {
                resultado.add(p);
            }
        }
        return resultado; 
    }
    
    //Para crear el Pedido, lo hare en 3 pasos
    //Ya que debe estar en memoria antes de agregarlo de forma definitiva
    
    //Paso1: Creo el pedido en memoria
    public Pedido iniciarPedido(Usuario usuario, FormaPago formaPago){
        if (usuario == null || usuario.isEliminado()) {
            throw new IllegalArgumentException("El pedido debe tener un usuario válido");
        }
        //si existe usuario, creo el pedido y asigno un id, independientemente que se confirme el pedido
        return new Pedido(contadorId++, LocalDate.now(), Estado.PENDIENTE, formaPago, usuario);
    }
    
    //Paso2: Agrega un detalle al pedido que se está armando (todavía no confirmado).
    public void agregarDetalle(Pedido pedido, int cantidad, Producto producto){
        if (!Validaciones.cantidadValida(cantidad) || cantidad == 0) {
            throw new StockInvalidoException("La cantidad debe ser mayor a 0");
        }
        //control de stock
        if (producto.getStock()< cantidad) {
            throw new StockInvalidoException("Stock insuficiente para "+ producto.getNombre() +
                    " Disponible: " + producto.getStock());
        }
        pedido.addDetallePedido(contadorDetalleId++, cantidad, producto);
    }
    
    //Paso3:validado el stock y detalle confirmado mediante addDetallePedido. El pedido ingresa coleccion
    public void confirmarPedido(Pedido pedido){
        if (pedido.getDetalles().isEmpty()) {
           throw new IllegalStateException("No se puede confirmar un pedido sin detalles"); 
        }
        pedido.calcularTotal();
        pedidos.add(pedido);
    }
    
    //Paso Auxiliar: en caso que falle el pedido durante la carga, desde el menu se llama
     public void descartarPedido(Pedido pedido) {
        // intencionalmente vacío: deja explícita la intención en el flujo del menú
    }
    
    public Pedido buscarPedPorID(Long id) {
        for (Pedido p : pedidos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("No existe pedido con id: " + id);
    }
    
    public void actualizarEstadoYFormaPago(Long id, Estado estado, FormaPago formaPago){
        Pedido pedido = buscarPedPorID(id);
        
        if (estado != null) {
            pedido.setEstado(estado);
        }
        if (formaPago != null) {
            pedido.setFormaPago(formaPago);
        }
    }
    
    public void eliminar(Long id){
        Pedido pedido = buscarPedPorID(id);
        pedido.setEliminado(true);
//        for (var detalle : pedido.getDetalles()) {
//            detalle.setEliminado(true);
//        }
    }
    
    
}
