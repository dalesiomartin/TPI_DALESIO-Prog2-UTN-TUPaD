/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;


import services.CategoriaServicio;
import services.ProductoServicio;
import services.UsuarioServicio;
import services.PedidoServicio;
import menu.MenuPrincipal;


/**
 *
 * @author Pc
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //para resolver la dependencia circular entre CatServ y ProdServ
        CategoriaServicio categoriaServicio = new CategoriaServicio();
        ProductoServicio productoServicio = new ProductoServicio(categoriaServicio);
        categoriaServicio.setProductoServicio(productoServicio);
        
        
        UsuarioServicio usuarioServicio = new UsuarioServicio();
        PedidoServicio pedidoServicio = new PedidoServicio();
        
        
        MenuPrincipal menuPrincipal = new MenuPrincipal(
                categoriaServicio,
                productoServicio,
                usuarioServicio,
                pedidoServicio
        
        );
        
        menuPrincipal.iniciar();

        
        //MOSTRAR LA INFORMACION DE LOS PEDIDOS POR USUARIO
        //mostrarInfo(listaUsuarios);
        
        
    
    }
    
//    public static void mostrarInfo(List<Usuario> listaUsuarios ){    
//        
//        for (Usuario listaUser : listaUsuarios) {
//            System.out.println("==============================================================================");
//            System.out.println(listaUser);
//            System.out.println("==============================================================================");  
//            
//            double acumUsuario = 0;
//            
//            for (Pedido ped : listaUser.getPedidos()) {
//                ped.calcularTotal();
//                System.out.println(ped);
//                System.out.println("-----------------------------------------------------------------------"); 
//                               
//                for (DetallePedido det : ped.getDetalles()) {
//                     System.out.println("  " + det);
//                }
//                
//                System.out.printf("TOTAL DEL PEDIDO: $%.2f%n", ped.getTotal());
//                acumUsuario += ped.getTotal();
//                System.out.println("-----------------------------------------------------------------------\n");
//            }
//            System.out.printf("TOTAL ACUMULADO del usuario: $%.2f%n", acumUsuario);
//            System.out.println("==============================================================================");
//            System.out.println("\n*************************************************************************************************\n");
//        }
//
//    }
//    
    
}


