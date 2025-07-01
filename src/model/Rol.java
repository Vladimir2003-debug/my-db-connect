package model;

public class Rol {
    private int rolCod;
    private String rolRol;
    private String rolNom;
    private String rolUsu;

    public Rol(int rolCod, String rolRol, String rolNom, String rolUsu) {
        this.rolCod = rolCod;
        this.rolRol = rolRol;
        this.rolNom = rolNom;
        this.rolUsu = rolUsu;
    }

    public int getRolCod() { return rolCod; }
    public String getRolRol() { return rolRol; }
    public String getRolNom() { return rolNom; }
    public String getRolUsu() { return rolUsu; }
}