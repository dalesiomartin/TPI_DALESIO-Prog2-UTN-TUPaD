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

      
    }
    
    
}


