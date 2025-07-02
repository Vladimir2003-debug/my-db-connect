package model;

import java.math.BigDecimal;

public class Tasa {
    private int tasCod;
    private String tasIden;
    private String tasDes;
    private BigDecimal tasTas; // DECIMAL(10,2) se representa como BigDecimal
    private String tasPlaz;
    private int tasIniFecha;
    private int tasFinFecha;

    public Tasa(int tasCod, String tasIden, String tasDes, BigDecimal tasTas,
                String tasPlaz, int tasIniFecha, int tasFinFecha) {
        this.tasCod = tasCod;
        this.tasIden = tasIden;
        this.tasDes = tasDes;
        this.tasTas = tasTas;
        this.tasPlaz = tasPlaz;
        this.tasIniFecha = tasIniFecha;
        this.tasFinFecha = tasFinFecha;
    }

    public int getTasCod() { return tasCod; }
    public String getTasIden() { return tasIden; }
    public String getTasDes() { return tasDes; }
    public BigDecimal getTasTas() { return tasTas; }
    public String getTasPlaz() { return tasPlaz; }
    public int getTasIniFecha() { return tasIniFecha; }
    public int getTasFinFecha() { return tasFinFecha; }
}
