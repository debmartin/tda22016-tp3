package aproximacion.mochila;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Mochila {

    private BufferedReader lectorArchivo;

    private int capacidadMochila;
    private int cantItems;
    private int[] valores;
    private int[] pesos;
    private int[][] matrizMochila;

    public int valorResultante;
    public int pesoResultante;

    public Mochila(int cantItems, int capacidadMochila, int[] valores, int[] pesos) {
        this.cantItems = cantItems;
        this.capacidadMochila = capacidadMochila;
        this.valores = valores;
        this.pesos = pesos;
        this.valorResultante = cargarMochila();
    }

    public Mochila(String rutaArchivo) {
        try {
            FileReader fr = new FileReader(rutaArchivo);
            lectorArchivo = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        cantItems = 0;
        capacidadMochila = 0;

        cargarDatosDesdeArchivo();
        valorResultante = cargarMochila();

    }

    public void cargarDatosDesdeArchivo() {
        try {
            lectorArchivo.readLine();//salteo titulo del archivo
            cantItems = Integer.parseInt(lectorArchivo.readLine().split(" ")[1]);
            capacidadMochila = Integer.parseInt(lectorArchivo.readLine().split(" ")[1]);
            System.out.println("Optimo Pisinger: " + lectorArchivo.readLine()); //salteo optimo
            lectorArchivo.readLine(); //salteo tiempo
        } catch (IOException e) {
            e.printStackTrace();
        }

        valores = new int[cantItems + 1];
        pesos = new int[cantItems + 1];
        matrizMochila = new int[cantItems + 1][capacidadMochila + 1];

        //leo todos los valores
        for (int i = 1; i <= cantItems; i++) {
            String[] linea = null;
            try {
                linea = lectorArchivo.readLine().split(",");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            valores[i] = Integer.parseInt(linea[1]);
            pesos[i] = Integer.parseInt(linea[2]);
        }

    }

    public int cargarMochila() {
        int sumaTotal = 0;

        for (int i = 0; i < this.cantItems; i++) {
            sumaTotal += this.valores[i];
        }

        matrizMochila = new int[this.cantItems + 1][sumaTotal + 1];
        for (int i = 0; i <= this.cantItems; i++) {
            matrizMochila[i][0] = 0;
        }

        int sumaParcial = 0;
        for (int i = 1; i <= this.cantItems; i++) {
            sumaParcial += this.valores[i - 1];
            for (int j = 1; j <= sumaTotal; j++) {
                if (j > sumaParcial - this.valores[i - 1]) {
                    matrizMochila[i][j] = this.pesos[i - 1] + matrizMochila[i - 1][j];
                } else {
                    matrizMochila[i][j] = Math.min(matrizMochila[i - 1][j], this.pesos[i - 1] + matrizMochila[i - 1][Math.max(0, j - this.valores[i - 1])]);
                }
            }
        }

        int res = 0;
        int v = 1;

        while (v <= sumaTotal && matrizMochila[this.cantItems][v] <= this.capacidadMochila) {
            this.pesoResultante = matrizMochila[this.cantItems][v];
            res = v;
            v++;
        }

        return res;
    }

    public void mostrarDatos() {
        System.out.println("\nOptimo nuestro: " + valorResultante);
        System.out.println("\nPeso nuestro: " + pesoResultante);

        int i = cantItems;
        int j = capacidadMochila;
        int itemsCargados = 0;
        int pesoAcumulado = 0;
        int valorAcumulado = 0;

        System.out.println("\nItems cargados\n");

        //antes de q recorra todas las filas o el peso se hace cero
        while (i > 0 && j > 0) {
            //arranco desde la ultima fila y columna y voy retrocediendo
            // si la fila anterior cambia quiere decir que hubo un incrmento
            // y que el elemento i fue ingresado en la mochila
            if (matrizMochila[i][j] != matrizMochila[i - 1][j]) {

                itemsCargados++;
                valorAcumulado += valores[i];
                pesoAcumulado += pesos[i];
                j = j - pesos[i];

                System.out.println("Item : " + i + " valor: " + valores[i] + "  peso: " + pesos[i]);
            }
            i--;
        }

        System.out.println("\nItems en Total: " + itemsCargados + " valorTotal: " + valorAcumulado + "  pesoTotal: " + pesoAcumulado);
    }

}
