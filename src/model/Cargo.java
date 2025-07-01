package model;

public class Cargo {
    private String codigo;
    private String descripcion;
    private boolean estadoRegistro;

    public Cargo(String codigo, String descripcion, boolean estadoRegistro) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estadoRegistro = estadoRegistro;
    }

    // Getters y Setters
}