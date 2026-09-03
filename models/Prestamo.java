package models;

import java.io.*;
import java.util.*;

public class Prestamo {
    private static int contadorId = 0;
    private int id;
    private Lector lector;
    private String bookName;
    private String fechaPrestamo;
    private String fechaDevolucion;

    public Prestamo(Lector lector, String bookName, String fechaPrestamo) {
        this.lector = lector;
        this.bookName = bookName;
        this.fechaPrestamo = fechaPrestamo;
        this.id = ++contadorId;
    }

    private Prestamo(int id, Lector lector, String bookName, String fechaPrestamo) {
        this.id = id;
        this.lector = lector;
        this.bookName = bookName;
        this.fechaPrestamo = fechaPrestamo;
    }

    public int getId() {
        return id;
    }

    public Lector getLector() {
        return lector;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public static void crearPrestamo(Prestamo prestamo) throws IOException {
        FileWriter fw = new FileWriter("prestamos.csv", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(prestamo.toString());
        bw.newLine();
        bw.close();
    }

    public static List<Prestamo> buscarPorIdLector(int idLector) {
        List<Prestamo> prestamos = new ArrayList<>();
        File file = new File("prestamos.csv");

        if (!file.exists()) {
            return prestamos;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
<<<<<<< HEAD
                String[] datos = linea.split(",", -1);
                if (datos.length >= 5 && Integer.parseInt(datos[1].trim()) == idLector) {
=======
                String[] datos = linea.split(",");
                if (datos.length >= 4 && Integer.parseInt(datos[1].trim()) == idLector) {
>>>>>>> 41f20c5606fff2fb93112b78c2837bb9a42bd1f2
                    int id = Integer.parseInt(datos[0].trim());
                    int idLec = Integer.parseInt(datos[1].trim());
                    String bookName = datos[2].trim();
                    String fechaPrestamo = datos[3].trim();
                    String fechaDevolucion = datos.length > 4 ? datos[4].trim() : "";

                    Lector lector = Lector.buscarPorId(idLec);
                    if (lector == null) {
                        lector = new Lector("Lector", "Registrado", "");
                        lector.setId(idLec);
                    }

                    Prestamo p = new Prestamo(id, lector, bookName, fechaPrestamo);
                    p.setFechaDevolucion(fechaDevolucion);
                    prestamos.add(p);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al leer préstamos: " + e.getMessage());
        }

        return prestamos;
    }

    @Override
    public String toString() {
        return id + "," + lector.getId() + "," + bookName + "," + fechaPrestamo + "," + fechaDevolucion;
    }
<<<<<<< HEAD

}
=======
}
>>>>>>> 41f20c5606fff2fb93112b78c2837bb9a42bd1f2
