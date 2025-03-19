package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.utilidades.Entrada; // Import de tu librería privada
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cliente {
    private static final String ER_NOMBRE = "([A-Z][a-z]+\\s+)+";
    private static final String ER_DNI = "\\d{8}[^IOU]{1}";
    private static final String ER_TELEFONO = "\\d{9}";
    private static final char[] LETRAS_DNI = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

    private String nombre;
    private String dni;
    private String telefono;

    public Cliente(String nombre, String dni, String telefono) {
        setNombre(nombre);
        setDni(dni);
        setTelefono(telefono);
    }

    public Cliente(Cliente cliente) {
        if (cliente == null) {
            throw new NullPointerException("No se puede copiar un cliente nulo.");
        }
        this.nombre = cliente.getNombre();
        this.dni = cliente.getDni();
        this.telefono = cliente.getTelefono();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || !nombre.matches(ER_NOMBRE)) {
            throw new IllegalArgumentException("El nombre no tiene un formato válido.");
        }
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    private void setDni(String dni) {
        if (dni == null || !dni.matches(ER_DNI)) {
            throw new IllegalArgumentException("El DNI no tiene un formato válido.");
        }
        comprobarLetraDni(dni);
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || !telefono.matches(ER_TELEFONO)) {
            throw new IllegalArgumentException("El teléfono no tiene un formato válido.");
        }
        this.telefono = telefono;
    }

    // Método para comprobar la letra del DNI (sin modificar)
    private void comprobarLetraDni(String dni) {
        Pattern patron;
        Matcher comparador;
        patron = Pattern.compile(ER_DNI);
        do {
            System.out.print("Introduce un DNI: ");
            dni = Entrada.cadena();
            comparador = patron.matcher(dni);
        } while (!comparador.matches());
        char letraDNI = dni.charAt(dni.length() - 1);
        String numerosDNI = dni.substring(0, 8);
        int numero = Integer.parseInt(numerosDNI);
        char letraValida = LETRAS_DNI[numero % 23];
        do {
            dni = Entrada.cadena();
            letraValida = LETRAS_DNI[numero%23];
            throw new IllegalArgumentException("La letra del DNI no es correcta.");
        } while (letraDNI != letraValida);


    }

    public static Cliente get(String dni) {
        return new Cliente("Nombre Por Defecto", dni, "123456789");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(dni, cliente.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return String.format("Cliente: [nombre=%s, dni=%s, teléfono=%s]", nombre, dni, telefono);
    }
}