/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 *
 * @author USUARIO
 */
public class EntityNotFoundException extends SistemException {
    public EntityNotFoundException(String message) {
        super(message);
    } 
}

// EntityNotFound sirve para cuando el programa busque un registro en la bdd y no lo encuentre 