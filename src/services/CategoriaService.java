/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Categoria;
import exceptions.EntidadNoEncontradaException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Pc
 */
public class CategoriaService {
    private List<Categoria> categorias;
    
    //constructor: creo la lista vacia
    public CategoriaService() {
        categorias = new ArrayList<>();
    }
    
    //getter
    public List<Categoria> getCategorias() {
        return categorias;
    }
       
    
    public List<Categoria> listar() {
        List<Categoria>resultado = new ArrayList<>(); 
        for (Categoria c : categorias) {
            //solo muestro las que no estan eliminadas
            if (!c.isEliminado()) {
                resultado.add(c);
            }
        }
        return resultado;
    }
    
    
    public void agregar(Categoria categoria){
        
        //1° verificacion. Valido nombre que no sea null o vacio con espacios
        if (Validaciones.textoValido(categoria.getNombre())) {
            throw new IllegalArgumentException("El nombre de la categoria debe ser obligatorio");
        }
        
        //2° Valido duplicados
        for (Categoria c : categorias) {
            if (c.getNombre().equalsIgnoreCase(categoria.getNombre())) {
                throw new IllegalArgumentException("Ya existe una categoria con este nombre");
            }
        }
        
        //si esta todo ok, asigno.
        categorias.add(categoria);
    
    }
    
    public Categoria buscarPorID(Long id) throws EntidadNoEncontradaException{
        for (Categoria c : categorias) {
            if (c.getId().equals(id) && !c.isEliminado()) {
                return c;
            }  
        }
        throw new EntidadNoEncontradaException("No existe categoria con id: " + id);
    }
    
    
    
    public void eliminar(Long id) throws EntidadNoEncontradaException{
        
        Categoria categoria = buscarPorID(id);
        
        categoria.setEliminado(true);
    
    }
    
   
    public void editar(Long id, String nombre, String descripcion)throws EntidadNoEncontradaException{
        Categoria categoria = buscarPorID(id);
        if (!Validaciones.textoValido(nombre)) {
           throw new IllegalArgumentException("Nombre obligatorio");
        }
        categoria.setNombre(nombre);
         
        
        if (!Validaciones.textoValido(descripcion)) {
            throw new IllegalArgumentException("Descripcion obligatoria");
        }
        categoria.setDescripcion(descripcion);
    
    }
    
    
 
}
