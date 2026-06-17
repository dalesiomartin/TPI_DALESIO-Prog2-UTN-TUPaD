/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Categoria;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pc
 */
public class CategoriaService {
    private List<Categoria> categorias;

    public CategoriaService() {
        categorias = new ArrayList<>();
    }
    
    public List<Categoria> listar() {
        return null;
    }
    
    public void agregar(Categoria categoria){
        
        //1° verificacion. Valido nombre
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
    
    
    public void eliminar(){}
    
    
    
    
    
    
    
    
    
    
}
