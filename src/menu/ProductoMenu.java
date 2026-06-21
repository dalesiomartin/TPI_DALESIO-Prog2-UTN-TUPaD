/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

import entities.Categoria;
import entities.Producto;
import exceptions.EntidadNoEncontradaException;
import exceptions.StockInvalidoException;
import java.util.List;
import java.util.Scanner;
import services.CategoriaServicio;
import services.ProductoServicio;

/**
 *
 * @author Pc
 */
public class ProductoMenu {
    private ProductoServicio productoServicio;
    private CategoriaServicio categoriaServicio;
    private Scanner sc;

    public ProductoMenu(ProductoServicio productoServicio, CategoriaServicio categoriaService, Scanner sc) {
        this.productoServicio = productoServicio;
        this.categoriaServicio = categoriaService;
        this.sc = sc;
    }
    
      public void mostrar() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Listar (general)");
            System.out.println("2. Listar por categoría");
            System.out.println("3. Crear");
            System.out.println("4. Editar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            String opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1": listar(); break;
                case "2": listarPorCategoria(); break;
                case "3": crear(); break;
                case "4": editar(); break;
                case "5": eliminar(); break;
                case "0": volver = true; break;
                default: System.out.println("Opción inválida.");
            }
        }
    }
      
    
    private void listar() {
        List<Producto> productos = productoServicio.listar();
        if (productos.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }
        for (Producto p : productos) {
            System.out.println(p.getId() + " - " + p);
        }
    } 
    
    private void listarPorCategoria() {
        listarCategoriasDisponibles();
        try {
            System.out.print("Ingrese el id de la categoría: ");
            Long categoriaId = Long.parseLong(sc.nextLine().trim());

            categoriaServicio.buscarCatPorID(categoriaId); // valida que exista antes de listar

            List<Producto> productos = productoServicio.listarPorCategoria(categoriaId);
            if (productos.isEmpty()) {
                System.out.println("No hay productos cargados para esa categoría.");
                return;
            }
            for (Producto p : productos) {
                System.out.println(p.getId() + " - " + p);
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: el id ingresado no es válido.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
     private void crear() {
        listarCategoriasDisponibles();
        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine().trim();

            System.out.print("Descripción: ");
            String descripcion = sc.nextLine().trim();

            System.out.print("Precio: ");
            Double precio = Double.parseDouble(sc.nextLine().trim());

            System.out.print("Stock: ");
            int stock = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Imagen (url o nombre de archivo): ");
            String imagen = sc.nextLine().trim();

            System.out.print("¿Disponible? (S/N): ");
            boolean disponible = sc.nextLine().trim().equalsIgnoreCase("S");

            System.out.print("Id de la categoría: ");
            Long categoriaId = Long.parseLong(sc.nextLine().trim());

            Producto nuevo = productoServicio.crear(nombre, precio, descripcion, stock,
                    imagen, disponible, categoriaId);

            System.out.println("Producto creado con id: " + nuevo.getId());

        } catch (NumberFormatException e) {
            System.out.println("Error: precio, stock o id de categoría inválidos.");
        } catch (IllegalArgumentException | StockInvalidoException | EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void editar(){
        listar();
        try {
            System.out.print("Ingrese el id del producto a editar: ");
            Long id = Long.parseLong(sc.nextLine().trim());

            Producto actual = productoServicio.buscarProdPorID(id);
            System.out.println("Editando: " + actual);
            System.out.println("(Toque ENTER para mantener el valor actual)");
            
            System.out.print("Nuevo nombre: ");
            String nombre = sc.nextLine().trim();

            System.out.print("Nuevo precio: ");
            String precioTexto = sc.nextLine().trim();
            Double precio = precioTexto.isBlank() ? null : Double.parseDouble(precioTexto);

            System.out.print("Nueva descripción: ");
            String descripcion = sc.nextLine().trim();

            System.out.print("Nuevo stock: ");
            String stockTexto = sc.nextLine().trim();
            Integer stock = stockTexto.isBlank() ? null : Integer.parseInt(stockTexto);

            System.out.print("Nueva imagen: ");
            String imagen = sc.nextLine().trim();
            
            System.out.print("¿Modificar disponibilidad? (S/N): ");
            String modificarDisp = sc.nextLine().trim().toUpperCase();
            Boolean disponible = null;
            if (modificarDisp.equals("S")) {
                System.out.print("¿Disponible? (S/N): ");
                disponible = sc.nextLine().trim().equalsIgnoreCase("S");
            }
            
            System.out.print("¿Modificar categoría? (S/N): ");
            String modificarCat = sc.nextLine().trim().toUpperCase();
            Long categoriaId = null;
            if (modificarCat.equals("S")) {
                listarCategoriasDisponibles();
                System.out.print("Nuevo id de categoría: ");
                categoriaId = Long.parseLong(sc.nextLine().trim());
            }
            
            productoServicio.editar(id, precio, stock, categoriaId);
            System.out.println("Producto actualizado correctamente.");
            
        } catch (NumberFormatException e) {
            System.out.println("Error: ingresó un valor numérico inválido.");
        } catch (EntidadNoEncontradaException | StockInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        }   
    }
    
    
    
    private void listarCategoriasDisponibles() {
        List<Categoria> categorias = categoriaServicio.listar();
        if (categorias.isEmpty()) {
            System.out.println("(No hay categorías cargadas. Cree una categoría antes de continuar.)");
            return;
        }
        System.out.println("Categorías disponibles:");
        for (Categoria c : categorias) {
            System.out.println("  " + c.getId() + " - " + c.getNombre());
        }
    }
    
    private void eliminar() {
        listar();
        try {
            System.out.print("Ingrese el id del producto a eliminar: ");
            Long id = Long.parseLong(sc.nextLine().trim());

            Producto producto = productoServicio.buscarProdPorID(id);
            System.out.print("¿Confirma eliminar \"" + producto.getNombre() + "\"? (S/N): ");
            String confirmacion = sc.nextLine().trim().toUpperCase();

            if (confirmacion.equals("S")) {
                productoServicio.eliminar(id);
                System.out.println("Producto eliminado (baja lógica).");
            } else {
                System.out.println("Operación cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: el id ingresado no es válido.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    
      
      
}
