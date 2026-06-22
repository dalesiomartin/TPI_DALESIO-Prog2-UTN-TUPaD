/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 * Excepción de negocio utilizada cuando se incumplen
 * reglas relacionadas con stock o cantidades.
 *
 * Se propaga desde la capa de servicios y es capturada
 * en la capa de menú para informar al usuario.
 */
public class StockInvalidoException extends RuntimeException{

    public StockInvalidoException(String mensaje) {
        super(mensaje);
    }
    
}
