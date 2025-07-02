package model;

public class Rol {
    private int rolCod;
    private String rolRol;
    private String rolNom;

    public Rol(int rolCod, String rolRol, String rolNom) {
        this.rolCod = rolCod;
        this.rolRol = rolRol;
        this.rolNom = rolNom;
    }

    public int getRolCod() { return rolCod; }
    public String getRolRol() { return rolRol; }
    public String getRolNom() { return rolNom; }
}