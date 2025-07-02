package model;

public class Fecha {
    private int fechaCod;
    private int fechaDia;
    private int fechaMes;
    private int fechaAño;

    public Fecha(int fechaCod, int fechaDia, int fechaMes, int fechaAño) {
        this.fechaCod = fechaCod;
        this.fechaDia = fechaDia;
        this.fechaMes = fechaMes;
        this.fechaAño = fechaAño;
    }

    public int getFechaCod() {
        return fechaCod;
    }

    public int getFechaDia() {
        return fechaDia;
    }

    public int getFechaMes() {
        return fechaMes;
    }

    public int getFechaAño() {
        return fechaAño;
    }
}
