package models;

import java.io.*;

public class Lector {
    private int id;
    private String name;
    private String lastName;
    private String phoneNumber;

    public Lector(int id, String name, String lastName, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }


    public int getId() {
        return id;
    }


    public Lector(int id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
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

    public static Lector buscarPorId(int id) throws IOException {
        File file = new File("lectores.csv");
        if (!file.exists()) return null;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 3 && Integer.parseInt(datos[0].trim()) == id) {
                    String phone = datos.length > 3 ? datos[3].trim() : "";
                    return new Lector(id, datos[1].trim(), datos[2].trim(), phone);
                }
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return String.valueOf(this.id) + "," + this.name + "," + this.lastName + "," + this.phoneNumber;
    }


}
