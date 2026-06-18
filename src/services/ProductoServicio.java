/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Producto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pc
 */
public class ProductoServicio {
    
    
    private List<Producto> productos;
    private CategoriaService categoriaservicio;
    
    
    //constructor
    public ProductoServicio(CategoriaService categoriaservicio) {
        productos = new ArrayList<>();
        this.categoriaservicio = categoriaservicio;
    }
    
    
    
    public List<Producto> listar(){
        List<Producto> resultado = new ArrayList<>();
        for (Producto p : productos) {
            if (!p.isEliminado()) {
                resultado.add(p);
            }
        }
        return resultado;
    }
    
    
    //VER PORQUE EN CATEGORIA, PONIA UN SOLO PARAMETRO Y EN PRODUCTO CARGO TODO
    public void agregar(Producto producto){
        
        //1° verificacion. Valido nombre que no sea null o vacio con espacios
        if (!Validaciones.textoValido(producto.getNombre())) {
            throw new IllegalArgumentException("El nombre del producto debe ser obligatorio");
        }
        
        //2° Valido duplicados
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(producto.getNombre()) && !p.isEliminado()) {
                throw new IllegalArgumentException("Ya existe un producto con este nombre");
            }
        }
        
        //si esta todo ok, asigno.
        productos.add(producto);
    
    }
    
    
    
}
