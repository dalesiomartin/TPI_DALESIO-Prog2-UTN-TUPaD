/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.time.LocalDateTime;

/**
 *
 * @author Pc
 */
public class DetallePedido extends Base{
    
    private static Long contadorDetalle = 1L;
    
    private int cantidad;
    private Double subtotal;
    private Producto producto;
    

    DetallePedido(int cantidad, Producto producto) {
        super(contadorDetalle++, false, LocalDateTime.now());
        this.cantidad = cantidad;
        this.producto = producto;
        calcularSubtotal(); //se calcula al crearse el pedido
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    private Double calcularSubtotal(){
        this.subtotal = this.cantidad * this.producto.getPrecio();
        return this.subtotal;
    }

    @Override
    public String toString() {
        return String.format("-DetallePedido #%d: %s x %d => Subtotal: $%.2f", getId(),getProducto(),getCantidad(), getSubtotal());
        
    }
    
    
    
    
}
