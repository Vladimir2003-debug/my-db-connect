package model;

public class Cooperativa {
    private int cooCod;
    private String cooIde;
    private String cooNom;
    private String cooSig;
    private String cooDir;
    private int cooTel;
    private String cooCor;
    private String cooSlo;
    private byte[] cooLog; // BLOB se representa como arreglo de bytes
    private String cooUsu;

    public Cooperativa(int cooCod, String cooIde, String cooNom, String cooSig, String cooDir,
                       int cooTel, String cooCor, String cooSlo, byte[] cooLog, String cooUsu) {
        this.cooCod = cooCod;
        this.cooIde = cooIde;
        this.cooNom = cooNom;
        this.cooSig = cooSig;
        this.cooDir = cooDir;
        this.cooTel = cooTel;
        this.cooCor = cooCor;
        this.cooSlo = cooSlo;
        this.cooLog = cooLog;
        this.cooUsu = cooUsu;
    }

    public int getCooCod() { return cooCod; }
    public String getCooIde() { return cooIde; }
    public String getCooNom() { return cooNom; }
    public String getCooSig() { return cooSig; }
    public String getCooDir() { return cooDir; }
    public int getCooTel() { return cooTel; }
    public String getCooCor() { return cooCor; }
    public String getCooSlo() { return cooSlo; }
    public byte[] getCooLog() { return cooLog; }
    public String getCooUsu() { return cooUsu; }
}