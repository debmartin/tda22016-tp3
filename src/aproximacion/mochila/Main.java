package aproximacion.mochila;

public class Main {

    public static void main(String[] args) {

        String archivo = "/home/esteban/Proyectos/tda22016-tp3/src/aproximacion/mochila/hardinstances_pisinger/knapPI_13_50_1000.csv";

        Mochila m = new Mochila(archivo);

        //m.cargarDatosDesdeArchivo();
        //m.cargarMochila();
        m.mostrarDatos();
    }
}
