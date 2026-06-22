/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

import java.util.Scanner;
import services.CategoriaServicio;
import services.PedidoServicio;
import services.ProductoServicio;
import services.UsuarioServicio;

/**
 *
 * @author Pc
 */
public class MenuPrincipal {
    
    private CategoriaServicio categoriaServicio;
    private ProductoServicio productoServicio;
    private UsuarioServicio usuarioServicio;
    private PedidoServicio pedidoServicio;
    private Scanner sc;

    public MenuPrincipal(CategoriaServicio categoriaServicio, ProductoServicio productoServicio, UsuarioServicio usuarioServicio, PedidoServicio pedidoServicio) {
        this.categoriaServicio = categoriaServicio;
        this.productoServicio = productoServicio;
        this.usuarioServicio = usuarioServicio;
        this.pedidoServicio = pedidoServicio;
        this.sc = new Scanner(System.in);
    }
    
    
    public void iniciar(){
        boolean salir = false;
        while (!salir) {            
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorias");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");
            
            String opcion = sc.nextLine().trim();
            
            switch (opcion) {
                case "1":
                    new CategoriaMenu(categoriaServicio, productoServicio,sc).mostrar();
                    break;
                case "2":
                    new ProductoMenu(productoServicio, categoriaServicio, sc).mostrar();
                    break;
                case "3":
                    new UsuarioMenu(usuarioServicio, sc).mostrar();
                    System.out.println("Menú de usuarios pendiente de implementar");
                    break;
                case "4":
                    new PedidoMenu(pedidoServicio, usuarioServicio, productoServicio, sc).mostrar();
                    System.out.println("Menú de pedidos pendiente de implementar");
                    break;
                case "0":
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
            
        }
        sc.close();
    
    }
    
    
    
    
}
