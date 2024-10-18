package Game.Protagonista;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Inventario {
    private static List<Integer> Items; //los integers son los tipos de items
    private static int hidrogeno;
    private static int sodio;
    private static int uranio;
    private static int platino;
    private Scanner leeconsola;

    //constructor inicial, crea un nuevo Arraylist
    public Inventario(){
        Items = new ArrayList<>();
    }

    //printea la cantidad de minerales actuales, y los items disponibles en el inventario
    public void mostrarInventario() {
        System.out.println("Inventario:");
        System.out.println("Hidrogeno: " + hidrogeno + " unidades\nSodio: " + sodio + " unidades\nUranio: " + uranio + " unidades\nPlatino: " + platino + " unidades");
        System.out.println("Lista de items disponibles:");
        int index = 1;
        for (int item : Items) {
            switch (item) {
                case 1 -> printeaItemsDisponibles(index, "Propulsor MK1","del combustible","10%");
                case 2 -> printeaItemsDisponibles(index, "Propulsor MK2","del combustible", "25%");
                case 3 -> printeaItemsDisponibles(index, "Propulsor MK3","del combustible", "50%");
                case 4 -> printeaItemsDisponibles(index, "Propulsor MK ULTRA", "del combustible", "99%");
                case 5 -> printeaItemsDisponibles(index, "Escudo de Energia MK1", "del escudo personal", "10%");
                case 6 -> printeaItemsDisponibles(index, "Escudo de Energia MK2", "del escudo personal", "25%");
                case 7 -> printeaItemsDisponibles(index, "Escudo de Energia MK3", "del escudo personal", "50%");
                case 8 -> printeaItemsDisponibles(index, "Escudo de Energia MK ULTRA", "del escudo personal", "99%");
            }
            index++;
        }
    }

    //printea los items disponibles en el inventario
    public void printeaItemsDisponibles(int index, String nombre, String itemPara, String porcentaje){
        System.out.println(index + "- " + nombre + ": mejora la eficiencia " + itemPara + " en un " + porcentaje);
    }

    //funcion que pregunta cual item quiere usar el jugador, y llama a mejora Inventario para procesarlo
    public void seleccionaMejoraInventario(){
        System.out.println("Selecciona la posicion del item con el que mejorar tu equipamiento:");
        int itemElegido;
        leeconsola = new Scanner(System.in);
        do {
            itemElegido= Integer.parseInt(leeconsola.nextLine()) - 1; //las listas empiezan desde index 0 asi que esto para compensar
            if (itemElegido<0 || itemElegido > Items.size()-1) {
                System.out.println("Opcion incorrecta, elige de nuevo");
            }
        } while (itemElegido<0 || itemElegido > Items.size()-1);
        mejoraInventario(Items.get(itemElegido));
        Items.remove(itemElegido); // saca el item de la lista
    }

    //utiliza el tipo de item elegido, viendo si es item de nave o jugador
    private void mejoraInventario(int tipoItem){
        if(tipoItem <=4){ // si el item es de la nave
            float eficienciaActual = Nave.getEficienciaPropulsor();
            if (eficienciaActual < 0.99F) { // si aun no llega al tope
                float nuevaEficiencia = 0;
                switch (tipoItem){
                    case 1 -> nuevaEficiencia = eficienciaActual+.1F;
                    case 2 -> nuevaEficiencia = eficienciaActual+.25F;
                    case 3 -> nuevaEficiencia = eficienciaActual+.50F;
                    case 4 -> nuevaEficiencia = eficienciaActual+0.99F;
                }
                // si aun no llega al tope manda nuevaEficiencia
                // si sobrepasa el tope, se guarda el tope fijo
                Nave.setEficienciaPropulsor(Math.min(nuevaEficiencia, 0.99F));
                System.out.println("Nueva eficiencia del propulsor:    " + Nave.getEficienciaPropulsor());
                if(Nave.getEficienciaPropulsor()==0.99F) System.out.println("Propulsor mejorado al maximo");
            } else System.out.println("Eficiencia del escudo personal mejorado al maximo, se deshizo del item para ahorrar espacio");

        } else{ //se aplica la misma logica para el jugador
            float eficienciaActual = Jugador.getEficienciaEnergiaProteccion();
            if (eficienciaActual < 0.99F) {
                float nuevaEfeciencia = 0;
                switch (tipoItem){
                    case 5 -> nuevaEfeciencia = eficienciaActual+.1F;
                    case 6 -> nuevaEfeciencia = eficienciaActual+.25F;
                    case 7 -> nuevaEfeciencia = eficienciaActual+.50F;
                    case 8 -> nuevaEfeciencia = eficienciaActual+0.99F;
                }
                Jugador.setEficienciaEnergiaProteccion(Math.min(nuevaEfeciencia,0.99F));
                System.out.println("Nueva eficiencia del escudo personal:    " + Jugador.getEficienciaEnergiaProteccion());
                if(Nave.getEficienciaPropulsor()==0.99F) System.out.println("Escudo mejorado al maximo");
            } else System.out.println("Eficiencia de la Nave mejorado al maximo, se deshizo del item para ahorrar espacio");
        }
    }

    //cierra el scanner
    public void CierraScanner(){
        if (leeconsola != null){
            leeconsola.close();
        }
    }

    //GETTERS Y SETTERS
    public static List<Integer> getItems() {
        return Items;
    }
    public static void setItems(List<Integer> items) {
        Items = items;
    }

    public static int getHidrogeno() {
        return hidrogeno;
    }
    public static void setHidrogeno(int hidrogeno) {
        Inventario.hidrogeno = hidrogeno;
    }

    public static int getSodio() {
        return sodio;
    }
    public static void setSodio(int sodio) {
        Inventario.sodio = sodio;
    }


    public static int getPlatino() {
        return platino;
    }
    public static void setPlatino(int platino) {
        Inventario.platino = platino;
    }

    public static int getUranio() {
        return uranio;
    }
    public static void setUranio(int uranio) {
        Inventario.uranio = uranio;
    }
}
