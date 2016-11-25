package aproximacion.viajante;

import java.util.ArrayList;

public class Arista {

    private int origen;
    private int destino;
    private int peso;

    public Arista(int origen, int destino, int peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    public int origen() {
        return origen;
    }

    public int destino() {
        return destino;
    }

    public int peso() {
        return peso;
    }

    public String toString() {

        return "( " + origen + ", " + destino + " , " + peso + " )";
    }
    
    @Override
    public boolean equals(Object otra) {
        Arista otraArista = (Arista) otra;
        return (origen == otraArista.origen) && (destino == otraArista.destino) && (peso == otraArista.peso);
    }
}
