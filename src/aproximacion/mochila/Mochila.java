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

            try {
                lectorArchivo.readLine();//salteo titulo del archivo
                cantItems = Integer.parseInt(lectorArchivo.readLine().split(" ")[1]);
                capacidadMochila = Integer.parseInt(lectorArchivo.readLine().split(" ")[1]);
                lectorArchivo.readLine(); //salteo optimo de pissinger
                lectorArchivo.readLine(); //salteo tiempo
            } catch (IOException e) {
                cantItems = 0;
                capacidadMochila = 0;
                e.printStackTrace();
            }

            valores = new int[cantItems + 1];
            pesos = new int[cantItems + 1];

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
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int cargarMochila() {
        int totalValueSum = 0;
        for (int i = 0; i < this.cantItems; i++)
            totalValueSum += this.valores[i];

        int[][] matrizPesos = new int[this.cantItems + 1][totalValueSum + 1];

        for (int i = 0; i <= this.cantItems; i++){
            matrizPesos[i][0] = 0;
        }

        int partialValueSum = 0;
        for (int i = 1; i <= this.cantItems; i++){
            partialValueSum += this.valores[i - 1];
            for (int v = 1; v <= totalValueSum; v++){

                //System.out.println("i : "+i+" - v : "+v);

                if (v > partialValueSum - this.valores[i - 1])
                    matrizPesos[i][v] = this.pesos[i - 1] + matrizPesos[i - 1][v];
                else
                    matrizPesos[i][v] = Math.min(matrizPesos[i - 1][v], this.pesos[i - 1] + matrizPesos[i  - 1][Math.max(0, v - this.valores[i - 1])]);
            }
        }

        // calculo y retorno el valor obtenido
        int mejorValorPosible = 0;
        int v = 1;
        while (v <= totalValueSum && matrizPesos[this.cantItems][v] <= this.capacidadMochila){
            this.pesoResultante = matrizPesos[this.cantItems][v];
            mejorValorPosible = v;
            v++;
        }

        return mejorValorPosible;
    }

}
