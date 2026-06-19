/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Categoria;
import entities.Producto;
import exceptions.EntidadNoEncontradaException;
import exceptions.StockInvalidoException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pc
 */
public class ProductoServicio {
    
    
    private List<Producto> productos;
    private CategoriaService categoriaservicio;
    private Long contadorId;
    
    
    //constructor
    public ProductoServicio(CategoriaService categoriaservicio) {
        productos = new ArrayList<>();
        this.categoriaservicio = categoriaservicio;
        this.contadorId = 1L;
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
    
    public List<Producto> listarPorCategoria(Long categoriaId) {
        List<Producto> resultado = new ArrayList<>();
        for (Producto p : productos) {
            if (!p.isEliminado() && p.getCategoria() != null && p.getCategoria().getId().equals(categoriaId)) {
                resultado.add(p);
            }
        }
        return resultado;
    }
    
    
    
    public Producto agregar(String nombre, Double precio, String descripcion, int stock,
                             String imagen, boolean disponible, Long categoriaId) throws StockInvalidoException {

        // 1° nombre obligatorio
        if (!Validaciones.textoValido(nombre)) {
            throw new IllegalArgumentException("El nombre del producto debe ser obligatorio");
        }

        // 2° duplicados (mismo criterio que en Categoria)
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre) && !p.isEliminado()) {
                throw new IllegalArgumentException("Ya existe un producto con este nombre");
            }
        }

        // 3° precio y stock no negativos (regla de la consigna -> StockInvalidoException)
        if (!Validaciones.numeroPositivo(precio)) {
            throw new StockInvalidoException("El precio no puede ser negativo");
        }
        if (!Validaciones.cantidadValida(stock)) {
            throw new StockInvalidoException("El stock no puede ser negativo");
        }

        // 4° la categoria debe existir y no estar eliminada
        Categoria categoria = categoriaservicio.buscarPorID(categoriaId); // ya lanza EntidadNoEncontradaException si no existe

        Producto nuevo = new Producto(nombre, precio, descripcion, stock, imagen, disponible,
                    categoria, contadorId++, false, LocalDateTime.now());
        productos.add(nuevo);
        return nuevo;
    }
        
    public Producto buscarPorID(Long id) {
        for (Producto p : productos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
            throw new EntidadNoEncontradaException("No existe producto con id: " + id);
    }
    
    
    public void editar(Long id, String nombre, Double precio, String descripcion,
                        Integer stock, String imagen, Boolean disponible, Long categoriaId) throws StockInvalidoException {

        Producto producto = buscarPorID(id);

        if (Validaciones.textoValido(nombre)) {
            producto.setNombre(nombre);
        }
        if (precio != null) {
            if (!Validaciones.numeroPositivo(precio)) {
                throw new StockInvalidoException("El precio no puede ser negativo");
            }
            producto.setPrecio(precio);
        }
        if (Validaciones.textoValido(descripcion)) {
            producto.setDescripcion(descripcion);
        }
        if (stock != null) {
            if (!Validaciones.cantidadValida(stock)) {
                throw new StockInvalidoException("El stock no puede ser negativo");
            }
            producto.setStock(stock);
        }
        if (Validaciones.textoValido(imagen)) {
            producto.setImagen(imagen);
        }
        if (disponible != null) {
            producto.setDisponible(disponible);
        }
        if (categoriaId != null) {
            Categoria nuevaCategoria = categoriaservicio.buscarPorID(categoriaId);
            producto.setCategoria(nuevaCategoria);
        }
    }

    public void eliminar(Long id) {
        Producto producto = buscarPorID(id);
        producto.setEliminado(true); // baja lógica
    }
    
}
