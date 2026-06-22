/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Usuario;
import enums.Rol;
import exceptions.EntidadNoEncontradaException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pc
 */
public class UsuarioServicio {
    private List<Usuario>usuarios;
    private Long contadorId;

    public UsuarioServicio() {
        usuarios = new ArrayList<>();
        this.contadorId = 1L;
    }
    
    public List<Usuario> listar(){
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (!u.isEliminado()) {
                resultado.add(u);
            }
        }
        return resultado;
        
    }
    
    
    public Usuario crear(String nombre, String apellido, String mail,
                            String celular, String contrasenia, Rol rol){
    
        // 1° nombre obligatorio
        if (!Validaciones.textoValido(nombre)) {
            throw new IllegalArgumentException("El nombre del Usuario es obligatorio");
        }
        
        //2° valido apellido
        if (!Validaciones.textoValido(apellido)) {
            throw new IllegalArgumentException("El apellido del Usuario es obligatorio");
        }
        
        // 3° valido formato de mail (antes solo se validaba en el Menu)
        if (!Validaciones.mailValido(mail)) {
            throw new IllegalArgumentException("El mail es obligatorio y debe tener un formato válido (ejemplo@dominio.com)");
        }
        
        // 4° valido que el mail sea único
        validarMailUnico(mail, null);
        
        
        Usuario nuevo = new Usuario(nombre, apellido, mail.trim().toLowerCase(), celular, contrasenia, rol, contadorId++, false, LocalDateTime.now());
        usuarios.add(nuevo);
        return nuevo;
    
    }
    
    public Usuario buscarUserPorID(Long id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id) && !u.isEliminado()) {
                return u;
            }
        }
            throw new EntidadNoEncontradaException("No existe Usuario con id: " + id);
    }
    
    public void editar(Long id, String nombre, String apellido, String mail,
                        String celular, Rol rol){
        Usuario usuario = buscarUserPorID(id);
        
        if (Validaciones.textoValido(nombre)) {
            usuario.setNombre(nombre);
        }
        if (Validaciones.textoValido(apellido)) {
            usuario.setApellido(apellido);
        }
        if (Validaciones.textoValido(mail)) {
            if (!Validaciones.mailValido(mail)) {
                throw new IllegalArgumentException("El mail debe tener un formato válido (ejemplo@dominio.com)");
            }
            validarMailUnico(mail, id);
            usuario.setMail(mail.trim().toLowerCase());
        }
        if (Validaciones.textoValido(celular)) {
            usuario.setCelular(celular);
        }
        if (rol != null) {
            usuario.setRol(rol);
        }
    
    }
    
    public void eliminar(Long id){
        Usuario usuario = buscarUserPorID(id);
        usuario.setEliminado(true);
        //Pedidos existentes del usuario deben seguir pudiendo consultarse (historial).
    
    }
    
    private void validarMailUnico(String mail, Long id){
        for (Usuario u : usuarios) {
            boolean esMismoUser = id != null && u.getId().equals(id);
            if (u.getMail().equalsIgnoreCase(mail) && !u.isEliminado() && !esMismoUser) {
                throw new IllegalArgumentException("Ya existe un usuario con el mail: " + mail);
            }
        }
    }

}