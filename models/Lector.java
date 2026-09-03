package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Lector {
    private static int contadorId = 0;
    private int id;
    private String name;
    private String lastName;
    private String phoneNumber;

    public Lector(String name, String lastName, String phoneNumber) {
        this.id = ++contadorId;
        setName(name);
        setLastName(lastName);
        this.phoneNumber = phoneNumber;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        if (!Character.isUpperCase(name.charAt(0)))
            throw new IllegalArgumentException("Debe digitar el nombre con la primera letra en mayúscula");
        this.name = name;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        if (!Character.isUpperCase(lastName.charAt(0)))
            throw new IllegalArgumentException("Debe digitar el apellido con la primera letra en mayúscula");
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static void crearUsuario(Lector lector) 
    throws IOException {
        FileWriter fw = new FileWriter("lectores.csv", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(lector.toString());
        bw.newLine();
        bw.close();
    }

    @Override
    public String toString() {
        return String.valueOf(this.id) + "," + this.name + "," + this.lastName + "," + this.phoneNumber;
    }
}
