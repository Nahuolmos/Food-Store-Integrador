package UI;

import Exceptions.BusinessException;
import java.util.Scanner;
import service.UsuarioService;

public class UsuarioMenu {

    private UsuarioService service;
    private Scanner scanner;

    public UsuarioMenu(UsuarioService service, Scanner scanner) {
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
                    menu();
                case 3 ->
                   menu();
                case 4 ->
                   menu();
                case 0 ->
                    System.out.println("Volviendo al menu principal");
            }
        } while (opcion < 0 || opcion > 5);

        
        
        
        
    }
