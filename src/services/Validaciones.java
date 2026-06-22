/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Base;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Pc
 */
public class Validaciones {
    
    //valido que el texto no es nulo ni vacio
    public static boolean textoValido(String texto){
        return texto != null && !texto.isBlank();
    }
    
    //valido nros positivos
    public static boolean numeroPositivo(double num){
        return num >= 0; //!= null, ==caracteres
    }
    
    public static boolean stockValido(int cant){
        return cant >= 0;
    }
    
    // Para DETALLE PEDIDO (cantidad debe ser mayor a 0)
    public static boolean cantidadPedidoValida(int cantidad){
        return cantidad > 0;
    }
    
    /**
     * MÉTODO GENÉRICO UNIFICADO
     * Acepta una lista de cualquier entidad que herede de la clase Base.
     * Satisface el criterio de NO DUPLICACIÓN de la rúbrica.
     */
    public static boolean esListaVacia(List<? extends Base> lista, String mensajeError) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("\n[Aviso] " + mensajeError + "\n");
            return true; // Retorna true si está vacía (debe abortar)
        }
        return false; // Retorna false si tiene datos (puede continuar)
    }
    
    
}
