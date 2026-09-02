package models;
//import java.util.*;
import java.io.*;

public class Prestamo{
    private int id;
    private Lector lector;
    private String bookName;
    private String fechaPrestamo;
    private String fechaDevolucion;

    public Prestamo(int id, Lector lector, String bookName, String fechaPrestamo){
        this.lector = lector;
        this.bookName = bookName;
        this.fechaPrestamo = fechaPrestamo;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Lector getLector(){
        return lector;
    }

    public String getBookName(){
        return bookName;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setLector(int idLector){
        try{
            Lector lector = Lector.buscarPorId(idLector);
            if (lector == null){
                throw new NullPointerException("No existe un lector con ese ID.");
            }
        } catch (IOException e){
            System.out.println("Error al leer lectores.csv.");
        }
    }

    public void setBookName(String bookName){
        this.bookName = bookName;
    }

    public String getFechaPrestamo(){
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo){
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getFechaDevolucion(){
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion){
        this.fechaDevolucion = fechaDevolucion;
    }

    public static void crearPrestamo(Prestamo prestamo)
    throws IOException {
        FileWriter fw = new FileWriter("prestamos.csv", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(prestamo.toString());
        bw.newLine();
        bw.close();
    }

}
