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
                case 6 -> mostrarConsultas();
                case 7 -> System.out.println("Cerrando el sistema. Hasta pronto.");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
            System.out.println();
        } while (opcion != 7);

        sc.close();
    }

    static void menuConsultas() {
        int opcion;
        do{
            mostrarConsultas();
            opcion = leerEntero("Seleccione una opcion: ");

            switch(opcion){
                case 1 -> historialLector();
                case 2 -> lectoresConMayorPrestamos();
                case 3 -> librosPrestados();
                case 4 -> reporteLectoresPrestamos();
                case 5 -> reportePrestamosVencidos();
                case 6 -> main(null);
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 6);
    }

    static void mostrarMenu() {
        System.out.println("=== Biblioteca ===");
        System.out.println("1. Registrar un lector");
        System.out.println("2. Listar lectores");
        System.out.println("3. Eliminar lector");
        System.out.println("4. Registrar prestamo");
        System.out.println("5. Listar prestamos de lector");
        System.out.println("6. Consultas");
        System.out.println("7. Salir");
    }

    static void mostrarConsultas(){
        System.out.println("1. Historial completo de un lector");
        System.out.println("2. Lectores con mayor cantidad de préstamos");
        System.out.println("3. Libros actualmente prestados");
        System.out.println("4. Generar reporte de lectores con préstamos activos");
        System.out.println("5. Generar reporte de préstamos vencidos");
        System.out.println("6. Volver al menú principal");
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
        Lector.listarTodos();
    }

    static void eliminarLector() {
        int id = leerEntero("Digite el ID del lector a eliminar: ");
        Lector lector = Lector.buscarPorId(id);
        if (lector == null) {
            System.out.println("El lector con ID " + id + " no existe.");
            return;
        }
        if (Lector.eliminarLector(id)) {
            System.out.println("Lector eliminado y archivo de índices actualizado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el lector.");
        }
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
            String fechaDevolucion = leerTexto("Digite la fecha de devolucion (opcional): ");

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

<<<<<<< HEAD
    static void consultarLectoresConMasPrestamos() {
        File archivoLectores = new File("lectores.csv");
        File archivoPrestamos = new File("prestamos.csv");

        try {
            if (!archivoLectores.exists()) {
                throw new FileNotFoundException("El archivo lectores.csv no existe.");
            }

            List<LectorResumen> lista = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(archivoLectores))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(",");
                    if (datos.length >= 4) {
                        try {
                            int id = Integer.parseInt(datos[0].trim());
                            String nombre = datos[1].trim() + " " + datos[2].trim();
                            lista.add(new LectorResumen(id, nombre));
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }

            if (lista.isEmpty()) {
                throw new IllegalArgumentException("No hay lectores registrados para realizar la consulta.");
            }

            if (archivoPrestamos.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(archivoPrestamos))) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        String[] datos = linea.split(",");
                        if (datos.length >= 4) {
                            try {
                                int idLector = Integer.parseInt(datos[1].trim());
                                boolean devuelto = datos.length >= 5 && !datos[4].trim().isEmpty();

                                for (LectorResumen r : lista) {
                                    if (r.id == idLector) {
                                        r.total++;
                                        if (devuelto) {
                                            r.devueltos++;
                                        } else {
                                            r.activos++;
                                        }
                                        break;
                                    }
                                }
                            } catch (NumberFormatException e) {
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < lista.size() - 1; i++) {
                for (int j = 0; j < lista.size() - 1 - i; j++) {
                    LectorResumen a = lista.get(j);
                    LectorResumen b = lista.get(j + 1);

                    boolean intercambiar = false;

                    if (b.total > a.total) {
                        intercambiar = true;
                    } else if (b.total == a.total) {
                        if (b.id < a.id) {
                            intercambiar = true;
                        }
                    }

                    if (intercambiar) {
                        lista.set(j, b);
                        lista.set(j + 1, a);
                    }
                }
            }

            System.out.println("=========================================");
            System.out.println("       LECTORES CON MÁS PRÉSTAMOS        ");
            System.out.println("=========================================");
            System.out.printf("%-5s %-20s %-7s %-9s %-9s\n", "ID", "Lector", "Total", "Activos", "Devueltos");
            System.out.println("-----------------------------------------");

            for (LectorResumen r : lista) {
                System.out.printf("%-5d %-20s %-7d %-9d %-9d\n", r.id, r.nombreCompleto, r.total, r.activos, r.devueltos);
            }

        } catch (FileNotFoundException | IllegalArgumentException e) {
            System.out.println("Error de consulta: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error de lectura de archivo: " + e.getMessage());
        }
    }


    static void historialLector() {
            try {
                int idLector = leerEntero("Ingrese el ID del lector: ");
                Lector lector = Lector.buscarPorId(idLector);

                if (lector == null) {
                    throw new IllegalArgumentException("El lector con ID " + idLector + " no existe.");
                }

                System.out.println("ID: " + lector.getId());
                System.out.println("Nombre: " + lector.getName() + " " + lector.getLastName());
                System.out.println("Teléfono: " + lector.getPhoneNumber());

                List<Prestamo> prestamos = Prestamo.buscarPorIdLector(idLector);
                for (int i = 1; i < prestamos.size(); i++) {
                    Prestamo actual = prestamos.get(i);
                    int j = i - 1;
                    while (j >= 0 && prestamos.get(j).getFechaPrestamo().compareTo(actual.getFechaPrestamo()) > 0) {
                        prestamos.set(j + 1, prestamos.get(j));
                        j--;
                    }
                    prestamos.set(j + 1, actual);
                }

                int activos = 0, devueltos = 0;
                System.out.println("\nID\tLibro\t\tFecha préstamo\tEstado");
                for (Prestamo p : prestamos) {
                    String estado = (p.getFechaDevolucion() == null || p.getFechaDevolucion().trim().isEmpty()) ? "ACTIVO" : "DEVUELTO";
                    if (estado.equals("ACTIVO")) activos++;
                    else devueltos++;
                    System.out.println(p.getId() + "\t" + p.getBookName() + "\t" + p.getFechaPrestamo() + "\t" + estado);
                }

                System.out.println("\nTotal de préstamos: " + prestamos.size());
                System.out.println("Préstamos activos: " + activos);
                System.out.println("Préstamos devueltos: " + devueltos);

            } catch (IllegalArgumentException e) {
                System.out.println("Error de validación: " + e.getMessage());
            }
        }




    // ====== Utilidades ======
>>>>>>> 41f20c5606fff2fb93112b78c2837bb9a42bd1f2

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
