package model;

public class Persona {
    private int perCod;
    private String perIden;
    private String perCor;
    private byte[] perFot; // BLOB representado como arreglo de bytes
    private int perCoo;
    private int perDat;
    private int perFecha;

    public Persona(int perCod, String perIden, String perCor, byte[] perFot, int perCoo, int perDat, int perFecha) {
        this.perCod = perCod;
        this.perIden = perIden;
        this.perCor = perCor;
        this.perFot = perFot;
        this.perCoo = perCoo;
        this.perDat = perDat;
        this.perFecha = perFecha;
    }

    public int getPerCod() { return perCod; }
    public String getPerIden() { return perIden; }
    public String getPerCor() { return perCor; }
    public byte[] getPerFot() { return perFot; }
    public int getPerCoo() { return perCoo; }
    public int getPerDat() { return perDat; }
    public int getPerFecha() { return perFecha; }
}
