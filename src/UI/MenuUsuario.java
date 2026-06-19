/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Exceptions.BusinessException;
import java.util.Scanner;

/**
 *
 * @author USUARIO
 */
public class MenuUsuario {

    private UsuarioService service;
    private Scanner scanner;

    public MenuUsuario(usuarioService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void menu() {
        int opcion;
        do {
            System.out.println("\n== GESTION DE USUARIOS ==");
            System.out.println("1. Listar usuario");
            System.out.println("2. Crear usuario");
            System.out.println("3. Editar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("0. Volver al menu principal");
            System.out.println("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException nfe) {
                opcion = -1;
            }
            switch (opcion) {
                case 1 ->
                    menu();
                case 2 ->
                    crearUsuario();
                case 3 ->
                    editarUsuario();
                case 4 ->
                    eliminarUsuario();
                case 0 ->
                    System.out.println("Volviendo al menu principal");
            }
        } while (opcion < 0 || opcion > 5);

        
        
        
        
    }
