package aproximacion.mochila;

public class Main {

    public static void main(String[] args) {

        String archivo = "/home/esteban/Proyectos/tda22016-tp3/src/aproximacion/mochila/hardinstances_pisinger/knapPI_16_500_1000.csv";

        double time = System.nanoTime();

        Mochila m = new Mochila(archivo);

        double executeTime = (System.nanoTime() - time) * Math.pow(10, -9);

        System.out.println("Archivo: " + archivo + " -> Tiempo: " + String.valueOf(executeTime));

        //m.cargarDatosDesdeArchivo();
        //m.cargarMochila();
        m.mostrarDatos();
    }
}
