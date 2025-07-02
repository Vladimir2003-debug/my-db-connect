package model;

public class Socio {
    private int socCod;
    private String socIden;
    private String socCor;
    private String socTipPro;
    private String socCta;
    private String socDep;
    private String socPro;
    private String socDis;
    private int socEmp;
    private int socDat;
    private int socFecha;

    public Socio(int socCod, String socIden, String socCor, String socTipPro, String socCta,
                 String socDep, String socPro, String socDis, int socEmp, int socDat, int socFecha) {
        this.socCod = socCod;
        this.socIden = socIden;
        this.socCor = socCor;
        this.socTipPro = socTipPro;
        this.socCta = socCta;
        this.socDep = socDep;
        this.socPro = socPro;
        this.socDis = socDis;
        this.socEmp = socEmp;
        this.socDat = socDat;
        this.socFecha = socFecha;
    }

    public int getSocCod() { return socCod; }
    public String getSocIden() { return socIden; }
    public String getSocCor() { return socCor; }
    public String getSocTipPro() { return socTipPro; }
    public String getSocCta() { return socCta; }
    public String getSocDep() { return socDep; }
    public String getSocPro() { return socPro; }
    public String getSocDis() { return socDis; }
    public int getSocEmp() { return socEmp; }
    public int getSocDat() { return socDat; }
    public int getSocFecha() { return socFecha; }
}
