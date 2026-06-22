/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 * Excepción de negocio utilizada cuando una operación
 * no puede realizarse porque una entidad no existe
 * o fue dada de baja lógicamente.
 *
 * Se implementa como RuntimeException porque representa
 * una condición esperable dentro del flujo de la aplicación.
 */
public class EntidadNoEncontradaException extends RuntimeException{

    public EntidadNoEncontradaException(String mensaje) {
        super(mensaje);
    }
    
}
