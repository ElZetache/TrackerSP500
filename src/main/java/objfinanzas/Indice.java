package objfinanzas;

import java.util.ArrayList;
import java.util.List;

public class Indice {
    private String nombre;
    private List<Empresa> empresas;

    public Indice() {
        this.empresas = new ArrayList<>();
        this.nombre = new String();
    }

    public void agregarValor(Empresa valor) {
        empresas.add(valor);
    }

    public void mostrarValores() {
        for (Empresa valor : empresas) {
            System.out.print(valor.getTicker() + " | ");
            System.out.print(valor.getNombre() + " | ");
            System.out.print(valor.getPer() + " | ");
            System.out.println(valor.getSector());

        }
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmpresas(List<Empresa> valores) {
        this.empresas = valores;
    }
}
