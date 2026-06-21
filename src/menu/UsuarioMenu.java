/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

import entities.Usuario;
import enums.Rol;
import exceptions.EntidadNoEncontradaException;
import java.util.List;
import java.util.Scanner;
import services.UsuarioServicio;

/**
 *
 * @author Pc
 */
public class UsuarioMenu {
    private UsuarioServicio usuarioServicio;
    private Scanner sc;

    public UsuarioMenu(UsuarioServicio usuarioServicio, Scanner sc) {
        this.usuarioServicio = usuarioServicio;
        this.sc = sc;
    }
    
    public void mostrar(){
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE USUARIOS ---");
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
    
    private void listar(){
        List<Usuario> usuarios = usuarioServicio.listar();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios cargados");
            return;
        }
        for (Usuario u : usuarios) {
            System.out.println(u.getId() + " - " + u );
        }  
    }
      
    private void crear(){
        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine().trim();

            System.out.print("Apellido: ");
            String apellido = sc.nextLine().trim();

            System.out.print("Mail: ");
            String mail = sc.nextLine().trim();

            System.out.print("Celular: ");
            String celular = sc.nextLine().trim();

            System.out.print("Contraseña: ");
            String contrasenia = sc.nextLine().trim();
            
            Rol rol = pedirRol();
            
            Usuario nuevo = usuarioServicio.crear(nombre, apellido, mail, celular, contrasenia, rol);
            System.out.println("Usuario creado con id: " + nuevo.getId());
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
 
    }
    
    private Rol pedirRol(){
        System.out.println("Rol: 1) ADMIN  2) USUARIO");
        System.out.print("Seleccione: ");
        String opcion = sc.nextLine().trim();
        if (opcion.equals("1")) {
            return Rol.ADMIN;
        }
        else if (opcion.equals("2")) {
            return Rol.USUARIO;
        } else {
            System.out.println("Opción inválida, se asigna USUARIO por defecto.");
        }
        return Rol.USUARIO;
    }
    
    
    private void editar(){
        listar(); //para traerme el listado de los usuarios
        try {
            System.out.println("Ingrese el id del usuario a editar");
            Long id = Long.parseLong(sc.nextLine().trim());
            
            Usuario userEditar = usuarioServicio.buscarUserPorID(id);
            System.out.println("Editando: " + userEditar);
            System.out.println("(Toque ENTER para mantener el valor actual)");
            
            System.out.print("Nuevo nombre: ");
            String nombre = sc.nextLine().trim();

            System.out.print("Nuevo apellido: ");
            String apellido = sc.nextLine().trim();

            System.out.print("Nuevo mail: ");
            String mail = sc.nextLine().trim();

            System.out.print("Nuevo celular: ");
            String celular = sc.nextLine().trim();
            
            System.out.print("¿Modificar rol? (S/N): ");
            String modificarRol = sc.nextLine().trim().toUpperCase();
            Rol rol = null;
            if (modificarRol.equals("S")) {
                rol = pedirRol();
            }
                  
            usuarioServicio.editar(id, nombre, apellido, mail, celular, rol);
            System.out.println("Usuario actualizado correctamente.");
            
        } catch (NumberFormatException e) {
            System.out.println("Error: el id ingresado no es válido.");
        } catch (EntidadNoEncontradaException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void eliminar(){
        listar();
        try {
            System.out.print("Ingrese el id del usuario a eliminar: ");
            Long id = Long.parseLong(sc.nextLine().trim());
            
            Usuario userElim = usuarioServicio.buscarUserPorID(id);
            System.out.print("¿Confirma eliminar a \"" + userElim.getNombre() + " " + userElim.getApellido() + "\"? (S/N): ");
            String confirmacion = sc.nextLine().trim().toUpperCase();
            
            if (confirmacion.equals("S")) {
                usuarioServicio.eliminar(id);
                System.out.println("Usuario eliminado (baja lógica).");
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
