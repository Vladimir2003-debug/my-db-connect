package model;

import java.sql.Date;

public class Tasa {
    private int tasCod;
    private String tasIden;
    private String tasDes;
    private float tasTas; // cambiado de BigDecimal a float
    private String tasPlaz;
    private Date tasIniFecha;
    private Date tasFinFecha;

    public Tasa(int tasCod, String tasIden, String tasDes, float tasTas,
            String tasPlaz, Date tasIniFecha, Date tasFinFecha) {
        this.tasCod = tasCod;
        this.tasIden = tasIden;
        this.tasDes = tasDes;
        this.tasTas = tasTas;
        this.tasPlaz = tasPlaz;
        this.tasIniFecha = tasIniFecha;
        this.tasFinFecha = tasFinFecha;
    }

    public int getTasCod() {
        return tasCod;
    }

    public String getTasIden() {
        return tasIden;
    }

    public String getTasDes() {
        return tasDes;
    }

    public float getTasTas() {
        return tasTas;
    }

    public String getTasPlaz() {
        return tasPlaz;
    }

    public Date getTasIniFecha() {
        return tasIniFecha;
    }

    public Date getTasFinFecha() {
        return tasFinFecha;
    }
}
