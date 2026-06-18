/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Categoria;
import entities.Producto;
import exceptions.EntidadNoEncontradaException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Pc
 */
public class CategoriaService {
    private List<Categoria> categorias;
    private Long contadorId; 
    private ProductoServicio productoServicio; 
    
    //constructor: creo la lista vacia
    public CategoriaService() {
        categorias = new ArrayList<>();
        contadorId = 1L;
    }
    
    //getter
    public List<Categoria> getCategorias() {
        return categorias;
    }  
    
    // se la seteás después de crear ambos services, porque hay dependencia circular
    //esta puedo para cuando quiero eliminar una categoria, verif si hay productos asociados
    public void setProductoServicio(ProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
    }
    
    
    public Categoria agregar(String nombre, String descripcion){
        
        //1° verificacion. Valido nombre que no sea null o vacio con espacios
        if (!Validaciones.textoValido(nombre)) {
            throw new IllegalArgumentException("El nombre de la categoria debe ser obligatorio");
        }
        
        //2° Valido duplicados
        for (Categoria c : categorias) {
            if (c.getNombre().equalsIgnoreCase(nombre) && !c.isEliminado()) {
                throw new IllegalArgumentException("Ya existe una categoria con este nombre");
            }
        }
        
        Categoria nueva = new Categoria(nombre, descripcion, contadorId++, false, LocalDateTime.now());
        categorias.add(nueva);
        return nueva;
    
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
    
    
    public Categoria buscarPorID(Long id){
        for (Categoria c : categorias) {
            if (c.getId().equals(id) && !c.isEliminado()) {
                return c;
            }  
        }
        throw new EntidadNoEncontradaException("No existe categoria con id: " + id);
    }
    
    
    
    public void eliminar(Long id){
        Categoria categoria = buscarPorID(id);
        
        if (tieneProductosActivos(id)) {
            throw new IllegalArgumentException("No se puede eliminar la categoria '" + categoria.getNombre() +
                "' porque tiene productos activos asociados.");
        }
        categoria.setEliminado(true); 
    }
    
    //controlar la dependencia circular dado que prod necesita de categ 
    // y categ no puedo eliminarla si existe un producto activo asocia
    private boolean tieneProductosActivos(Long categId){
        for (Producto p : productoServicio.listar()) {
            if (!p.isEliminado() && p.getCategoria() != null && 
                    p.getCategoria().getId().equals(categId)) {
                return true;
            }
        }
        return false;
    } 
    
   
    public void editar(Long id, String nombre, String descripcion){
        Categoria categoria = buscarPorID(id);
        if (Validaciones.textoValido(nombre)) {
            //valido que el nuevo nombre no sea el mismo a la de otra categoria.
            for (Categoria c : categorias) {
                if (!c.getId().equals(id) && c.getNombre().equalsIgnoreCase(nombre)) {
                    throw new IllegalArgumentException("Ya existe otra categoria con este nombre");
                }
            }
            categoria.setNombre(nombre);
        }
       
        if (Validaciones.textoValido(descripcion)) {
            categoria.setDescripcion(descripcion);
        }
    }
    
    
    
 
}
