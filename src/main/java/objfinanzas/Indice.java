package objfinanzas;

import java.util.ArrayList;
import java.util.List;

public class Indice {
    private String nombre;
    private List<EmpresaAnalisis> empresaAnalises;

    public Indice() {
        this.empresaAnalises = new ArrayList<>();
        this.nombre = new String();
    }

    public void agregarValor(EmpresaAnalisis valor) {
        empresaAnalises.add(valor);
    }

    public void mostrarValores() {
        System.out.println("Ticker | Nombre | PER | EV/EBITDA | Sector");
        for (EmpresaAnalisis valor : empresaAnalises) {
            System.out.print(valor.getTicker() + " | ");
            System.out.print(valor.getNombre() + " | ");
            System.out.print(valor.getPer() + " | ");
            System.out.print(valor.getEvEbitda() + " | ");
            System.out.println(valor.getSector());

        }
    }

    public List<EmpresaAnalisis> getEmpresas() {
        return empresaAnalises;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmpresas(List<EmpresaAnalisis> valores) {
        this.empresaAnalises = valores;
    }

    public void perMenor(double per){
        System.out.println("Ticker | Nombre | PER | EV/EBITDA | Sector");
        for(EmpresaAnalisis empresaAnalisis : empresaAnalises){
            if(per >= empresaAnalisis.getPer()){
                System.out.print(empresaAnalisis.getTicker() + " | ");
                System.out.print(empresaAnalisis.getNombre() + " | ");
                System.out.print(empresaAnalisis.getPer() + " | ");
                System.out.print(empresaAnalisis.getEvEbitda() + " | ");
                System.out.println(empresaAnalisis.getSector());
            }
        }
    }

    public Indice evEbitdaMenor(double evEbitda){
        Indice res = new Indice();
        res.setNombre("filtroEvEbitda");
        for(EmpresaAnalisis empresaAnalisis : empresaAnalises){
            if(evEbitda >= empresaAnalisis.getEvEbitda()){
                res.agregarValor(empresaAnalisis);
            }
        }
        return res;
    }
}
