package org.principal;

import java.util.ArrayList;
import java.util.List;

public class Indice {
    private final List<Valor> valores;

    public Indice() {
        this.valores = new ArrayList<>();
    }

    public void agregarValor(Valor valor) {
        valores.add(valor);
    }

    public void mostrarValores() {
        for (Valor valor : valores) {
            System.out.print(valor.getsBursatil() + " | ");
            System.out.print(valor.getNombre() + " | ");
            System.out.print(valor.getPer() + " | ");
            System.out.println(valor.getSector());

        }
    }

    public List<Valor> getValores() {
        return valores;
    }


}
