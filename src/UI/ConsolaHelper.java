package UI;

import enums.Estado;
import enums.FormaPago;
import enums.Rol;
import java.util.Scanner;

public class ConsolaHelper {
    private final Scanner scanner;

    public ConsolaHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número entero válido.");
            }
        }
    }

    public Long leerLong(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un id numérico válido.");
            }
        }
    }

    public double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
            }
        }
    }

    public String leerTexto(String mensaje) {
        return leerTexto(mensaje, false);
    }

    public String leerTexto(String mensaje, boolean permitirVacio) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim();
            if (!linea.isEmpty() || permitirVacio) return linea;
            System.out.println("Este campo no puede estar vacío.");
        }
    }

    public boolean confirmar(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim().toUpperCase();
            if (linea.equals("S")) return true;
            if (linea.equals("N")) return false;
            System.out.println("Responda con S o N.");
        }
    }

    public Rol leerRol() {
        while (true) {
            System.out.println("Rol: 1. ADMIN  2. USUARIO");
            int op = leerEntero("Seleccione: ");
            if (op == 1) return Rol.ADMIN;
            if (op == 2) return Rol.USUARIO;
            System.out.println("Opción inválida.");
        }
    }

    public Estado leerEstado() {
        while (true) {
            System.out.println("Estado: 1. PENDIENTE  2. CONFIRMADO  3. TERMINADO  4. CANCELADO");
            switch (leerEntero("Seleccione: ")) {
                case 1: return Estado.PENDIENTE;
                case 2: return Estado.CONFIRMADO;
                case 3: return Estado.TERMINADO;
                case 4: return Estado.CANCELADO;
                default: System.out.println("Opción inválida.");
            }
        }
    }

    public FormaPago leerFormaPago() {
        while (true) {
            System.out.println("Forma de pago: 1. TARJETA  2. TRANSFERENCIA  3. EFECTIVO");
            switch (leerEntero("Seleccione: ")) {
                case 1: return FormaPago.TARJETA;
                case 2: return FormaPago.TRANSFERENCIA;
                case 3: return FormaPago.EFECTIVO;
                default: System.out.println("Opción inválida.");
            }
        }
    }

    public void cerrar() {
        scanner.close();
    }
}
