package model;

public class Dato {
    private int datCod;
    private String datApeMat;
    private String datApePat;
    private String datNom;

    public Dato(int datCod, String datApeMat, String datApePat, String datNom) {
        this.datCod = datCod;
        this.datApeMat = datApeMat;
        this.datApePat = datApePat;
        this.datNom = datNom;
    }

    public int getDatCod() { return datCod; }
    public String getDatApeMat() { return datApeMat; }
    public String getDatApePat() { return datApePat; }
    public String getDatNom() { return datNom; }
}