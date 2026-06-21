/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

import entities.Categoria;
import exceptions.EntidadNoEncontradaException;
import java.util.List;
import java.util.Scanner;
import services.CategoriaServicio;
import services.ProductoServicio;

/**
 *
 * @author Pc
 */
public class CategoriaMenu {
    private CategoriaServicio categoriaService;
    private ProductoServicio productoServicio;
    private Scanner sc;

    public CategoriaMenu(CategoriaServicio categoriaService, ProductoServicio productoServicio, Scanner sc) {
        this.categoriaService = categoriaService;
        this.productoServicio = productoServicio;
        this.sc = sc;
    }
    
    public void mostrar() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            String opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1": listar(); break;
                case "2": crear(); break;
                case "3": editar(); break;
                case "4": eliminar(); break;
                case "0": volver = true; break;
                default: System.out.println("Opción inválida.");
            }
        }
    }
    
    public void listar(){
        List<Categoria> categorias = categoriaService.listar();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }
        for (Categoria c : categorias) {
            System.out.println(c.getId() + " - " + c);
        }
    }
    
    public void crear(){
        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine().trim();

            System.out.print("Descripción: ");
            String descripcion = sc.nextLine().trim();  
            
            Categoria nueva = categoriaService.crear(nombre, descripcion);
            System.out.println("Categoría creada con id: " + nueva.getId());
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void editar(){
        listar();
        try {
            System.out.print("Ingrese el id de la categoría a editar: ");
            Long id = Long.parseLong(sc.nextLine().trim());

            Categoria actual = categoriaService.buscarCatPorID(id);
            System.out.println("Editando: " + actual);
            System.out.println("(Toque ENTER para mantener el valor actual)");

            System.out.print("Nuevo nombre: ");
            String nombre = sc.nextLine().trim();

            System.out.print("Nueva descripción: ");
            String descripcion = sc.nextLine().trim();
            
            categoriaService.editar(id, nombre, descripcion);
            System.out.println("Categoría actualizada correctamente.");
            
        } catch (NumberFormatException e) {
            System.out.println("Error: el id ingresado no es válido.");
        } catch (EntidadNoEncontradaException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
    public void eliminar(){
        listar();
        try {
            System.out.print("Ingrese el id de la categoría a eliminar: ");
            Long id = Long.parseLong(sc.nextLine().trim());

            Categoria categoria = categoriaService.buscarCatPorID(id);
            System.out.print("¿Confirma eliminar la categoría \"" + categoria.getNombre() + "\"? (S/N): ");
            String confirmacion = sc.nextLine().trim().toUpperCase();
            
            if (confirmacion.equals("S")) {
                categoriaService.eliminar(id);
                System.out.println("Categoría eliminada (baja lógica).");
            } else {
                System.out.println("Operación cancelada.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Error: el id ingresado no es válido.");
        } catch (EntidadNoEncontradaException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    
}
