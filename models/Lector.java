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
                        lector.setId(id);
                        return lector;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al buscar lector: " + e.getMessage());
        }

        return null;
    }

    public static boolean eliminarLector(int idAEliminar) {
        File archivoOriginal = new File("lectores.csv");
        File archivoTemporal = new File("lectores_temp.csv");

        if (!archivoOriginal.exists()) return false;

        boolean encontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(archivoOriginal));
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTemporal))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 4) {
                    int id = Integer.parseInt(datos[0].trim());
                    if (id == idAEliminar) {
                        encontrado = true;
                        continue;
                    }
                }
                bw.write(linea);
                bw.newLine();
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al procesar archivo: " + e.getMessage());
            return false;
        }

        if (encontrado) {
            if (archivoOriginal.delete()) {
                archivoTemporal.renameTo(archivoOriginal);
                generarIndiceLectores();
            }
        } else {
            archivoTemporal.delete();
        }

        return encontrado;
    }


    public static void generarIndiceLectores() {
        File archivoLectores = new File("lectores.csv");
        File archivoIndice = new File("lectores_index.csv");

        if (!archivoLectores.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(archivoLectores));
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivoIndice))) {

            String linea;
            long posicionByte = 0;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 4) {
                    int id = Integer.parseInt(datos[0].trim());
                    
                    bw.write(id + "," + posicionByte);
                    bw.newLine();
                }
                posicionByte += linea.getBytes().length + System.lineSeparator().getBytes().length;
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reindexando: " + e.getMessage());
        }
    }

    public static void listarTodos() {
        File archivo = new File("lectores.csv");

        if (!archivo.exists()) {
            System.out.println("No hay lectores registrados aún.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            System.out.println("\n=== LISTA DE LECTORES ===");
            
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 4) {
                    System.out.println("ID: " + datos[0].trim() + 
                                    " | Nombre: " + datos[1].trim() + " " + datos[2].trim() + 
                                    " | Teléfono: " + datos[3].trim());
                }
            }
            System.out.println("=========================");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de lectores: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return this.id + "," + this.name + "," + this.lastName + "," + this.phoneNumber;
    }
}