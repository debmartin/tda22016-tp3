package aproximacion.mochila;

public class Main {

    public static void main(String[] args) {

        String templateArchivo = "/home/esteban/Repositorios/otros/tda22016-tp3/src/aproximacion/mochila/hardinstances_pisinger/knapPI_NROARCH_500_1000.csv";
        String archivo;

        for (int i = 11; i < 17; i++) {

            archivo = templateArchivo.replace("NROARCH", String.valueOf(i));

            Mochila m = new Mochila(archivo);

            double time = System.nanoTime();

            m.cargarMochila(2);

            double executeTime = (System.nanoTime() - time) * Math.pow(10, -9);

            int valorResultante = m.obtenerResultado();

            System.out.println("Archivo: " + archivo + " -> Tiempo: " + String.valueOf(executeTime) + ", Valor resultante: " + valorResultante);
        }
    }
}
