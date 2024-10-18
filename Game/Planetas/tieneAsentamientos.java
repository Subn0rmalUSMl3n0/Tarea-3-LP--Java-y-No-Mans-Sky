package Game.Planetas;

import Game.Protagonista.Jugador;

import java.util.HashMap;
import java.util.Random;

public interface  tieneAsentamientos { //intefaz = header

    //genera un inventario de 3 items al azar para cada asentamiento, y llama a procesaItems para asignarles un precio
    //retorna el inventario que tendra este asentamiento
    static HashMap<Integer, Integer> generaInventarioAsentamiento() { //cada asentamiento tendra 3 items de base, generados al azar
        HashMap<Integer, Integer> inventarioAsentamiento = new HashMap<>();
        Random rand = new Random();
        while (inventarioAsentamiento.size() < 3) { //cada asentamiento tendra 3 items de base
            int numeroItem = rand.nextInt(8) + 1;//genera tipo item del 1 al 8
            if (!inventarioAsentamiento.containsKey(numeroItem)) {// si el item no esta en el inventario de ese asentamiento, lo añade
                procesaItems(inventarioAsentamiento, numeroItem, rand);
            }
        }
        return inventarioAsentamiento;
    }

    //fumcion que asigna un precio a cada item al momento de ser creados y asignados a un asentamiento
    //recibe el inventario del asentamiento, el numero del item y un Random
    static void procesaItems(HashMap<Integer, Integer> inventarioAsentamiento, int numeroItem, Random rand) {
        switch (numeroItem) {
            case 1 -> {
                int precio = rand.nextInt(3) + 4;
                inventarioAsentamiento.put(1, precio);
            }
            case 2 -> {
                int precio = rand.nextInt(5) + 8;
                inventarioAsentamiento.put(2, precio);
            }
            case 3 -> {
                int precio = rand.nextInt(5) + 13;
                inventarioAsentamiento.put(3, precio);
            }
            case 4 -> {
                int precio = rand.nextInt(7) + 18;
                inventarioAsentamiento.put(4, precio);
            }
            case 5 -> {
                int precio = rand.nextInt(3) + 4;
                inventarioAsentamiento.put(5, precio);
            }
            case 6 -> {
                int precio = rand.nextInt(5) + 8;
                inventarioAsentamiento.put(6, precio);
            }
            case 7 -> {
                int precio = rand.nextInt(5) + 13;
                inventarioAsentamiento.put(7, precio);
            }
            case 8 -> {
                int precio = rand.nextInt(7) + 18;
                inventarioAsentamiento.put(8, precio);
            }
        }
    }

    //metodos a implementar en cada asentamiento
    //recibe el jugador actual
    void visitarAsentamiento(Jugador jugador);
}
