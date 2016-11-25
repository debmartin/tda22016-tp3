package aproximacion.viajante;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class Viajante {
    Grafo ciudades;
    LinkedList<Integer> camino;
    int costo;
    final int VERTICE_INICIAL = 0;
    
    public Viajante(Grafo grafo) {
        costo = 0;
        ciudades = grafo;
        
        Grafo arbolTendidoMinimo = ciudades.obtenerArbolTendidoMinimo();
        calcularArbolPreOrder(arbolTendidoMinimo);
        costo += ciudades.obtenerPeso(camino.getLast(), VERTICE_INICIAL);
        camino.add(VERTICE_INICIAL);
    }
    
    public Deque<Integer> obtenerCamino() {
        return camino;
    }
    
    private void preOrder(Grafo arbol, int raiz) {
        camino.add(raiz);
        ArrayList<Arista> aristasVertice = arbol.obtenerAristas(raiz);
        
        for (Arista aristaActual : aristasVertice) {
            costo += aristaActual.peso();
            preOrder(arbol, aristaActual.destino());
        }
    }
    
    private void calcularArbolPreOrder(Grafo arbol) {
        camino = new LinkedList<>();
        int raiz = 0;
        preOrder(arbol, raiz);
    }
    
    public int obtenerCostoCamino() {
        return costo;
    }
}
