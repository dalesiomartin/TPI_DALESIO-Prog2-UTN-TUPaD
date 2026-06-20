/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import enums.Estado;
import enums.FormaPago;
import interfaces.Calculable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pc
 */
public class Pedido extends Base implements Calculable{
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private List<DetallePedido>detalles;
    private Usuario usuario;


    //CONSTRUCTOR PRINCIPAL
    public Pedido(LocalDate fecha, Estado estado, FormaPago formaPago, Usuario usuario, Long id, boolean eliminado, LocalDateTime createdAt) {
        super(id, eliminado, createdAt);
        this.fecha = fecha;
        this.estado = estado;
        this.total = 0.0;
        this.formaPago = formaPago;
        this.detalles = new ArrayList<>(); //inicializo la lista vacia
        this.usuario = usuario;
    }
    
    //CONSTRUCTOR CORTO
    public Pedido(Long id, LocalDate fecha, Estado estado, FormaPago formaPago, Usuario usuario){
        this(fecha, estado, formaPago, usuario, id, false, LocalDateTime.now());
    }
            
    
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
        calcularTotal();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    

    @Override
    public void calcularTotal() {
        double sum = 0.0;
        for (DetallePedido detalle : detalles) {
            sum += detalle.getSubtotal();
        }
        this.total = sum;
                
    }
    
    public void addDetallePedido(long idDetalle, int cantidad, Producto p){
        DetallePedido nuevoDetalle = new DetallePedido(idDetalle,cantidad, p); //COMPOSICION
        this.detalles.add(nuevoDetalle);

        calcularTotal(); //cada vez que agrego/elimino un producto debo recalcular el total
    }
    
    public DetallePedido findDetallePedidoByProducto(Producto p){
        
        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto().getId().equals(p.getId())) {
                return detalle;
            }   
        }
        return null;
    }
    
    public void deleteDetallePedidoByProducto(Producto p){
        DetallePedido eliminarDetalle = findDetallePedidoByProducto(p); //busco el detalle
        if (eliminarDetalle != null) {           
            detalles.remove(eliminarDetalle); //Elimino el detalle encontrado
            
            calcularTotal(); //vuelvo a calcular el total del pedido
        }
    }

    @Override
    public String toString() {
        return String.format("> Pedido #%d | Fecha: %s | Estado: %s | FormaPago: %s",
                getId(), fecha, estado, formaPago);
    }
    
    
    
    
}
