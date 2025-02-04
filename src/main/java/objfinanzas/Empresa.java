package objfinanzas;

public class Empresa {
    private String ticker;
    private String nombre;
    private double per;
    private String sector;
    private String industria;
    private double precioActual;

    public Empresa(String ticker, String nombre, double per, String sector, String industria, double precioActual) {
        this.ticker = ticker;
        this.nombre = nombre;
        this.per = per;
        this.sector = sector;
        this.industria = industria;
        this.precioActual = precioActual;
    }

    public Empresa() {

    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPer() {
        return per;
    }

    public void setPer(double per) {
        this.per = per;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getIndustria() {
        return industria;
    }

    public void setIndustria(String industria) {
        this.industria = industria;
    }

    public double getPrecioActual() {
        return precioActual;
    }

    public void setPrecioActual(double precioActual) {
        this.precioActual = precioActual;
    }
}

