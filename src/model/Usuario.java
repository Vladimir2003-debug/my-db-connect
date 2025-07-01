// src/model/Usuario.java
package model;

public class Usuario {
    private int usuCod;
    private String usuIde;
    private String usuUsu;
    private String usuPas;
    private int usuRol;
    private String usuEmp;

    public Usuario() {
    }

    public Usuario(int usuCod, String usuIde, String usuUsu, String usuPas, int usuRol, String usuEmp) {
        this.usuCod = usuCod;
        this.usuIde = usuIde;
        this.usuUsu = usuUsu;
        this.usuPas = usuPas;
        this.usuRol = usuRol;
        this.usuEmp = usuEmp;
    }

    public int getUsuCod() {
        return usuCod;
    }

    public void setUsuCod(int usuCod) {
        this.usuCod = usuCod;
    }

    public String getUsuIde() {
        return usuIde;
    }

    public void setUsuIde(String usuIde) {
        this.usuIde = usuIde;
    }

    public String getUsuUsu() {
        return usuUsu;
    }

    public void setUsuUsu(String usuUsu) {
        this.usuUsu = usuUsu;
    }

    public String getUsuPas() {
        return usuPas;
    }

    public void setUsuPas(String usuPas) {
        this.usuPas = usuPas;
    }

    public int getUsuRol() {
        return usuRol;
    }

    public void setUsuRol(int usuRol) {
        this.usuRol = usuRol;
    }

    public String getUsuEmp() {
        return usuEmp;
    }

    public void setUsuEmp(String usuEmp) {
        this.usuEmp = usuEmp;
    }
}
