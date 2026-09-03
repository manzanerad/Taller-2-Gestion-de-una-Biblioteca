import models.Lector;
import models.Prestamo;
import java.io.*;
import java.util.*;

public class BibliotecaApp {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> registrarLector();
                case 2 -> listarLectores();
                case 3 -> eliminarLector();
                case 4 -> registrarPrestamo();
                case 5 -> listarPrestamoLector();
                case 6 -> System.out.println("Cerrando el sistema. Hasta pronto.");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
            System.out.println();
        } while (opcion != 6);

        sc.close();
    }

    static void mostrarMenu() {
        System.out.println("=== Biblioteca ===");
        System.out.println("1. Registrar un lector");
        System.out.println("2. Listar lectores");
        System.out.println("3. Eliminar lector");
        System.out.println("4. Registrar prestamo");
        System.out.println("5. Listar prestamos de lector");
        System.out.println("6. Salir");
    }

    static void registrarLector() {
        try {
            String name = BibliotecaApp.leerTexto("Digite el nombre del usuario: ");
            String lastName = BibliotecaApp.leerTexto("Digite el apellido del usuario: ");
            String phoneNumber = BibliotecaApp.leerTexto("Digite el teléfono del usuario: ");
            Lector u = new Lector(name, lastName, phoneNumber);
            Lector.crearUsuario(u);
            System.out.println(u);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    static void listarLectores() {

    }

    static void eliminarLector() {

    }

    static void registrarPrestamo() {
        try {
            int idLector = leerEntero("Digite el ID del lector que presta el libro: ");
            Lector lector = Lector.buscarPorId(idLector);

            if (lector == null) {
                System.out.println("No se encontró un lector con ese ID.");
                return;
            }

            String bookName = leerTexto("Digite el nombre del libro: ");
            String fechaPrestamo = leerTexto("Digite la fecha del prestamo: ");

            // Instanciación limpia sin gestionar variable id
            Prestamo prestamo = new Prestamo(lector, bookName, fechaPrestamo);
            Prestamo.crearPrestamo(prestamo);
            System.out.println("Préstamo registrado correctamente.");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    static void listarPrestamoLector() {
        int idLector = leerEntero("Digite el ID del lector: ");
        Lector lector = Lector.buscarPorId(idLector);

        if (lector == null) {
            System.out.println("El lector no existe.");
            return;
        }

        List<Prestamo> prestamos = Prestamo.buscarPorIdLector(idLector);

        if (prestamos.isEmpty()) {
            System.out.println("No existen préstamos asociados a este lector.");
            return;
        }

        System.out.println("=== Préstamos del lector " + lector.getName() + " " + lector.getLastName() + " ===");
        for (Prestamo p : prestamos) {
            System.out.println("ID del préstamo: " + p.getId());
            System.out.println("ID del lector: " + p.getLector().getId());
            System.out.println("Nombre del libro: " + p.getBookName());
            System.out.println("Fecha del préstamo: " + p.getFechaPrestamo());
            System.out.println("Fecha de devolución: " + (p.getFechaDevolucion() != null && !p.getFechaDevolucion().isEmpty() ? p.getFechaDevolucion() : "No registrada"));
            System.out.println("----------------------------------------");
        }
    }

    // ====== Utilidades ======

    static int leerEntero(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    static double leerDecimal(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(",", "."));
            } catch (Exception e) {
                System.out.println("Ingrese un número válido (ej: 1500.50).");
            }
        }
    }

    static String leerTexto(String msg) {
        String valor;
        do {
            System.out.print(msg);
            valor = sc.nextLine().trim();
            if (valor.isEmpty()) System.out.println("Este campo no puede quedar vacío.");
        } while (valor.isEmpty());
        return valor;
    }
}