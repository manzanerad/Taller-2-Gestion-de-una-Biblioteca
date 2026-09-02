import models.Lector;
import models.Prestamo;
import java.io.IOException;
//import java.util.ArrayList;
import java.util.Scanner;

public class BibliotecaApp{

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){
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

    static void registrarLector(){
        try {
            int idSec = 0;
            String name = BibliotecaApp.leerTexto("Digite el nombre del usuario: ");
            String lastName = BibliotecaApp.leerTexto("Digite el apellido del usuario: ");
            String phoneNumber = BibliotecaApp.leerTexto("Digite el teléfono del usuario: ");
            Lector u = new Lector(++idSec, name, lastName, phoneNumber);
            Lector.crearUsuario(u);
            System.out.println(u);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    static void listarLectores(){

    }

    static void eliminarLector(){

    }
/*
    static void registrarPrestamo(){
        try{
        int id = 0;
        int idLector = leerEntero("Dijite el ID del lector que presta el libro: ");
        Lector lector = ;
        String bookName = leerTexto("Dijite el nombre del libro: ");
        String fechaPrestamo = leerTexto("Dijite la fecha del prestamo: ");
        Prestamo pretamo = Prestamo.crearPrestamo(++id, lector, bookName, fechaPrestamo);
        }
    }
*/
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

            int id = 0;
            Prestamo prestamo = new Prestamo(++id, lector, bookName, fechaPrestamo);
            Prestamo.crearPrestamo(prestamo);
            System.out.println("Préstamo registrado correctamente.");

        } catch (IOException e) {
            System.out.println("Error al leer los lectores: " + e.getMessage());
        }
    }


    static void listarPrestamoLector(){

    }




    // ====== Utilidades (ya implementadas, no es necesario modificarlas) ======

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
