/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 *
 * @author USUARIO
 */
public class BusinessException extends SistemException {
    public BusinessException(String message){
        super(message);
    }
}

// Sirve por si el usuario intenta hacer algo que no esta "permitido" por codigo o logica