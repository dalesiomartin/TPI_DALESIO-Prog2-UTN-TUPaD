/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

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
    
    public static boolean cantidadValida(int cant){
        return cant >= 0;
    }
    
}
