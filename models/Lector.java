package models;

import java.io.*;

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
        if (name == null || name.isEmpty() || !Character.isUpperCase(name.charAt(0))) {
            throw new IllegalArgumentException("Debe digitar el nombre con la primera letra en mayúscula");
        }
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.isEmpty() || !Character.isUpperCase(lastName.charAt(0))) {
            throw new IllegalArgumentException("Debe digitar el apellido con la primera letra en mayúscula");
        }
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static void crearUsuario(Lector lector) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("lectores.csv", true))) {
            bw.write(lector.toString());
            bw.newLine();
        }
    }

    // Método que soluciona el error al buscar un lector por su ID desde el archivo CSV
    public static Lector buscarPorId(int idBuscado) {
        File file = new File("lectores.csv");
        if (!file.exists()) {
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 4) {
                    int id = Integer.parseInt(datos[0].trim());
                    if (id == idBuscado) {
                        String name = datos[1].trim();
                        String lastName = datos[2].trim();
                        String phoneNumber = datos[3].trim();

                        Lector lector = new Lector(name, lastName, phoneNumber);
                        lector.setId(id); // Asigna el ID exacto registrado en el archivo
                        return lector;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al buscar lector: " + e.getMessage());
        }

        return null;
    }

    @Override
    public String toString() {
        return this.id + "," + this.name + "," + this.lastName + "," + this.phoneNumber;
    }
}