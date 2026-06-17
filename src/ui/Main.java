/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ui;

import entities.Categoria;
import entities.DetallePedido;
import entities.Pedido;
import entities.Producto;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;
import enums.Rol;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pc
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // CATEGORIAS
        Categoria c1 = new Categoria("Hamburguesas", "Alimenticio", 1L, false, LocalDateTime.now());
        Categoria c2 = new Categoria("Bebidas", "Sin Alcohol", 2L, false, LocalDateTime.now());
        Categoria c3 = new Categoria("Postres", "El toque dulce", 3L , false, LocalDateTime.now());
        
        // PRODUCTOS
        Producto p1 = new Producto("Hamburguesa Clasica", 10000.00, "Clasica de los 80", 50, "imagen", true, c1, 101L, false, LocalDateTime.now());
        Producto p2 = new Producto("Hamburguesa Cheddar & Bacon", 15000.00 , "Lo nuevo", 30, "imagen", true, c1, 102L, false, LocalDateTime.now());
        Producto p3 = new Producto("Gaseosa Cola", 5000.00, "500ml", 100, "imagen", true, c2, 103L, false, LocalDateTime.now());
        Producto p4 = new Producto("Agua Mineral", 4000.00, "500ml", 80, "imagen", true, c2, 104L, false, LocalDateTime.now());
        Producto p5 = new Producto("Helado", 8000.00, "Sabor Chocolate", 25, "imagen", true, c3, 105L, false, LocalDateTime.now());
        Producto p6 = new Producto("Flan", 7000.00, "Sabor Vainilla", 20, "imagen", true, c3, 106L, false, LocalDateTime.now());
        
        
        // AGREGAR PRODCUTO A CATEGORIA
        c1.agregarProducto(p1);
        c1.agregarProducto(p2);
        
        c2.agregarProducto(p3);
        c2.agregarProducto(p4);

        c3.agregarProducto(p5);
        c3.agregarProducto(p6);
        
        
        // USUARIOS
        Usuario admini = new Usuario("Martin", "Dalesio", "martin@gmail.com", "0261123123", "Adm123", Rol.ADMIN, 10L, false, LocalDateTime.now());
        Usuario user = new Usuario("Roberto", "Dalesio", "rober@gmail.com", "01123456", "Rober123", Rol.USUARIO, 11L, false, LocalDateTime.now());
        
        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(admini);     
        listaUsuarios.add(user);
        
        // PEDIDOS ADMIN
        LocalDate fechape1 = LocalDate.of(2026, Month.MARCH, 5);
        Pedido pe1 = new Pedido(1001L, fechape1, Estado.PENDIENTE, FormaPago.TARJETA, admini);
        
        LocalDate fechape2 = LocalDate.of(2026, Month.APRIL, 10);
        Pedido pe2 = new Pedido(1002L, fechape2, Estado.CONFIRMADO, FormaPago.TARJETA, admini);

        // Pedido 1 Admin
        admini.agregarPedido(pe1);
        pe1.addDetallePedido(2, p1); 
        pe1.addDetallePedido(1, p3); 
        pe1.addDetallePedido(1, p5); 
        
        // Pedido 2 Admin
        admini.agregarPedido(pe2);
        pe2.addDetallePedido(1, p2); 
        pe2.addDetallePedido(2, p4); 
        pe2.addDetallePedido(1, p6); 
        
        // PEDIDOS USER 
        LocalDate fechape3 = LocalDate.of(2026, Month.APRIL, 15);
        Pedido pe3 = new Pedido(1003L, fechape3, Estado.TERMINADO, FormaPago.EFECTIVO, user);
       
        LocalDate fechape4 = LocalDate.of(2026, Month.APRIL, 30);
        Pedido pe4 = new Pedido(1004L, fechape4, Estado.PENDIENTE, FormaPago.TRANSFERENCIA, user);
       
        // Pedido 1 User
        user.agregarPedido(pe3);    
        pe3.addDetallePedido(1, p1); 
        pe3.addDetallePedido(1, p4); 
        pe3.addDetallePedido(1, p6); 
        
        // Pedido 2 User
        user.agregarPedido(pe4);
        pe4.addDetallePedido(3, p2); 
        pe4.addDetallePedido(2, p5); 
        pe4.addDetallePedido(1, p6); 
        
        //MOSTRAR LA INFORMACION DE LOS PEDIDOS POR USUARIO
        mostrarInfo(listaUsuarios);
        
        
    
    }
    
    public static void mostrarInfo(List<Usuario> listaUsuarios ){    
        
        for (Usuario listaUser : listaUsuarios) {
            System.out.println("==============================================================================");
            System.out.println(listaUser);
            System.out.println("==============================================================================");  
            
            double acumUsuario = 0;
            
            for (Pedido ped : listaUser.getPedidos()) {
                ped.calcularTotal();
                System.out.println(ped);
                System.out.println("-----------------------------------------------------------------------"); 
                               
                for (DetallePedido det : ped.getDetalles()) {
                     System.out.println("  " + det);
                }
                
                System.out.printf("TOTAL DEL PEDIDO: $%.2f%n", ped.getTotal());
                acumUsuario += ped.getTotal();
                System.out.println("-----------------------------------------------------------------------\n");
            }
            System.out.printf("TOTAL ACUMULADO del usuario: $%.2f%n", acumUsuario);
            System.out.println("==============================================================================");
            System.out.println("\n*************************************************************************************************\n");
        }

    }
    
    
}


