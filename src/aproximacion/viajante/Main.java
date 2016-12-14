package aproximacion.viajante;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Deque;

public class Main {
    
    private static Grafo crearGrafo(String archivoMatriz, int tamanio) throws IOException {
        BufferedReader lectorArchivo = null;
        lectorArchivo = new BufferedReader(new FileReader(archivoMatriz));
        String linea = lectorArchivo.readLine();

        if (linea == null) throw new IOException("Archivo con formato erroneo");
        
        String[] valoresLinea = linea.split(" ");
        int cantVertices = tamanio;
        Grafo ciudades = new Grafo(cantVertices);
        
        for (int i = 0; i < cantVertices; i++) {
            valoresLinea = linea.split(" ");
            
            for (int j = 0; j < cantVertices; j++) {
                int peso = Integer.parseInt(valoresLinea[j]);
                ciudades.agregarArista(i, j, peso);
            }
            linea = lectorArchivo.readLine();
            if (linea == null && i < cantVertices -2) throw new IOException("Archivo con formato erroneo");
        }
        lectorArchivo.close();
        return ciudades;
    }
    
    private static void calcular(String archivoMatriz, int tamanio) throws IOException {
        Grafo ciudades = crearGrafo(archivoMatriz, tamanio);
        Viajante viajante = new Viajante(ciudades);
        int costo = viajante.obtenerCostoCamino();
        Deque<Integer> camino = viajante.obtenerCamino();
        System.out.println("Tamanio: " + tamanio + ", Camino:");
        System.out.println(camino);    
    }
    
    public static void main(String[] args) throws IOException {
        int[] tamaniosMatriz = {15, 17, 19, 21, 23, 48};
        for (int tamanio : tamaniosMatriz) {
            calcular(args[0], tamanio);
        }
    }
    
}
