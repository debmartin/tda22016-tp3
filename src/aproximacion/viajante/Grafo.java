package aproximacion.viajante;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/* Para aproximar el problema del viajante de comercio usaremos el algoritmo 
descripto en el capítulo 35.2.1 de Cormen. Recordemos que el problema plantea 
que dado un grafo dirigido, completo y pesado, se encuentre un tour o ciclo 
hamiltoniano de costo mínimo que comience y termine en un vértice v_0 dado. (El 
costo se define como la suma de pesos de las aristas recorridas).
Para implementar este método se debe reutilizar el grafo implementado en el 
trabajo práctico 1, agregándole una primitiva para encontrar un árbol de tendido
mínimo usando los algoritmos de Prim o Kruskal. */

public class Grafo {

    private int cantVertices;
    private int cantAristas;
    private HashMap<Integer, ArrayList<Arista>> aristasAdyacentes;

    public Grafo(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("El numero de vertices no puede ser negativo");
        }
        this.cantVertices = V;
        this.cantAristas = 0;
        aristasAdyacentes = new HashMap<Integer, ArrayList<Arista>>();
        for (int v = 0; v < V; v++) {
            aristasAdyacentes.put(v, new ArrayList<Arista>());;
        }
    }

    public int v() {
        return cantVertices;
    }

    public int e() {
        return cantAristas;
    }

    private void validar_vertice(int v) {
        if (!aristasAdyacentes.containsKey(v)) {
            throw new IndexOutOfBoundsException("El vertice ingresado es invalido");
        }
    }

    public void agregarArista(int v, int w, int peso) {
        validar_vertice(v);
        validar_vertice(w);
        cantAristas++;
        aristasAdyacentes.get(v).add(new Arista(v, w, peso));
    }

    public void agregarArista(int v, int w) {
        agregarArista(v, w, 0);
    }

    public ArrayList<Arista> obtenerAristas(int v) {
        return aristasAdyacentes.get(v);
    }
    
    public int obtenerPeso(int v, int w) {
        ArrayList<Arista> aristas = aristasAdyacentes.get(v);
        if (aristas.isEmpty()) return -1;
        for (Arista arista : aristas) {
            if (arista.destino() == w) return arista.peso();
        }
        return -1;
    }

    public ArrayList<Integer> adj(int v) {
        ArrayList<Integer> adj = new ArrayList<>();
        ArrayList<Arista> aristasAdj = this.aristasAdyacentes.get(v);
        for (int i = 0; i < aristasAdj.size(); i++) {
            adj.add(aristasAdj.indexOf(i));
        }
        return adj;
    }

    public ArrayList<Integer> obtenerVertices() {
        ArrayList<Integer> vertices = new ArrayList<>();
        for (int v = 0; v < this.cantVertices; v++) {
            vertices.add(v);
        }
        return vertices;
    }

    public ArrayList<Arista> obtenerAristas() {
        ArrayList<Arista> aristas = new ArrayList<>();
        for (int v = 0; v < this.cantVertices; v++) {
            aristas.addAll(Grafo.this.obtenerAristas(v));
        }
        return aristas;
    }

    public String toString() {
        String s = "";
        for (int v = 0; v < cantVertices; v++) {
            s += v;
            s += " : ";
            s += aristasAdyacentes.get(v);
            s += "\n";
        }
        return s;
    }

    public Grafo obtenerArbolTendidoMinimo() {
        int[] distancia = new int[cantVertices];
        int[] padres = new int[cantVertices];
        final int verticeOrigen = 0;
        
        PriorityQueue<Integer> cola = new PriorityQueue<>(cantVertices, new ComparadorNodos(distancia));
        for (int vertice = 0; vertice < cantVertices; vertice++) {
            distancia[vertice] = Integer.MAX_VALUE;
            padres[vertice] = -1;
            cola.add(vertice);
        }
        distancia[verticeOrigen] = 0;
        while (!cola.isEmpty()) {
            int vertice = cola.poll();
            ArrayList<Arista> adyacentesVertice = aristasAdyacentes.get(vertice);
            for (Arista arista : adyacentesVertice) {
                int verticeAdyacente = arista.destino();
                if (cola.contains(verticeAdyacente) && (distancia[verticeAdyacente] > arista.peso())) {
                    padres[verticeAdyacente] = vertice;
                    distancia[verticeAdyacente] = arista.peso();
                    cola.remove(verticeAdyacente);
                    cola.add(verticeAdyacente);
                }
            }
        }
        Grafo resultado = new Grafo(cantVertices);
        for (int vertice = 0; vertice < cantVertices; vertice++) {
            if (vertice == verticeOrigen) continue;
            int verticePadre = padres[vertice];
            int pesoArista = distancia[vertice];
            resultado.agregarArista(verticePadre, vertice, pesoArista);
        }
        return resultado;
    }
/*
    @Override
    public boolean equals(Object otro) {
        if (otro == null) return false;
        Grafo otroGrafo = (Grafo) otro;
        if (otroGrafo.cantVertices != cantVertices) return false;
        if (otroGrafo.cantAristas != cantAristas) return false;
        
        for (int vertice = 0; vertice < cantVertices; vertice++) {
            ArrayList<Arista> misAristas = aristasAdyacentes.get(vertice);
            ArrayList<Arista> otroAristas = otroGrafo.aristasAdyacentes.get(vertice);
            if (misAristas != otroAristas) return false;
        }
        return true;
    }
  */  
    private class ComparadorNodos implements Comparator<Object> {

        int[] distancia;

        public ComparadorNodos(int[] distancia) {
            this.distancia = distancia;
        }

        @Override
        public int compare(Object o1, Object o2) {
            Integer nodo1 = (Integer) o1;
            Integer nodo2 = (Integer) o2;
            return Double.compare(distancia[nodo1], distancia[nodo2]);
        }
    }
}
