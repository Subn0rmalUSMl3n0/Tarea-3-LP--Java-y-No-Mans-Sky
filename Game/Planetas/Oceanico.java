package Game.Planetas;

import Game.LogicaNombres.NombresAsentamientos;
import Game.Protagonista.Inventario;
import Game.Protagonista.Jugador;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Oceanico extends Planeta implements tieneAsentamientos{

    private final HashMap<Integer, Integer> inventarioAsentamiento;
    private final String nombreAsentamiento;
    private Scanner leeconsola;

    //constructor, a y b es el intervalo del radio,hidrogeno y sodio se sacan con base en eso
    public Oceanico(){
        super(2);
        int a = (int) Math.pow(10,4);
        int b = (int) Math.pow(10,6);
        int radio = rand(a,b);
        setRadio(radio);
        int hidrogeno = (int) (0.2* (4*Math.PI*Math.pow(radio,2)));
        setCristalesHidrogeno(hidrogeno);
        int sodio = (int) (.65 * (4*Math.PI*Math.pow(radio,2)));
        setFloresDeSodio(sodio);

        int profundidad = rand(30, 100);// para no romper la logica del juego, se redujo el intervalo de 3 a 100
        super.setConsumoEnergia (0.002F * (float)Math.pow(profundidad,2));// la variable de consumoenergia de la clase padre se actualiza
        setEspecialPlaneta(profundidad);
        setMineralEspecial(0);
        this.inventarioAsentamiento = tieneAsentamientos.generaInventarioAsentamiento();
        this.nombreAsentamiento = NombresAsentamientos.getNombreAsentamiento(2);
        super.setNombreAsentamiento(this.nombreAsentamiento);
    }


    //compra un item del asentamiento, si el jugador tiene suficiente platino o uranio, y si no tiene ya el item
    //recibe el estado del jugador actual
    @Override
    public void visitarAsentamiento(Jugador jugador) {
        leeconsola = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("Bienvenido al asentamiento de " + this.nombreAsentamiento + "\nSu Lista de items es:");
            mostrarItemAsentamiento();
            System.out.println("""
                    ¿Qué deseas hacer?
                    1- Comprar un item
                    2- Irse del asentamiento""");
            opcion = leeconsola.nextInt();
            switch (opcion) {
                case 1 -> compraAsentamiento(leeconsola, jugador);
                case 2 -> System.out.println("Hasta luego");
                default -> System.out.println("Opción no válida, ingresar de nuevo");
            }
        } while (opcion != 2);
    }

    //muestra los items que posee actualemente el asentamiento
    @Override
    public void mostrarItemAsentamiento(){
        for (Map.Entry<Integer, Integer> entry : this.inventarioAsentamiento.entrySet()) {
            int key = entry.getKey();
            int valor = entry.getValue();
            System.out.print("      ● ");
            switch (key){
                case 1 -> System.out.println("Propulsor MK1 (mejora la eficiencia del combustible en un 10%), coste: " + valor + " unidades de platino");
                case 2 -> System.out.println("Propulsor MK2 (mejora la eficiencia del combustible en un 25%), coste: " + valor + " unidades de platino");
                case 3 -> System.out.println("Propulsor MK3 (mejora la eficiencia del combustible en un 50%), coste: " + valor + " unidades de platino");
                case 4 -> System.out.println("Propulsor MK ULTRA (mejora la eficiencia del combustible en un 99%), coste: " + valor + " unidades de platino");
                case 5 -> System.out.println("Escudo de Energía MK1 (mejora la eficiencia del combustible en un 10%), coste: " + valor + " unidades de uranio");
                case 6 -> System.out.println("Escudo de Energía MK2 (mejora la eficiencia del combustible en un 25%), coste: " + valor + " unidades de uranio");
                case 7 -> System.out.println("Escudo de Energía MK3 (mejora la eficiencia del combustible en un 50%), coste: " + valor + " unidades de uranio");
                case 8 -> System.out.println("Escudo de Energía MK ULTRA (mejora la eficiencia del combustible en un 99%), coste: " + valor + " unidades de uranio");
            }
        }
    }

    //muestra los items que posee el asentamiento segun su tipo, version resumida de mostrarItemAsentamiento para usar en visitaAsentamiento
    private void mostrarItemsAsentamientoPorTipo(){
        for (Map.Entry<Integer, Integer> entry : this.inventarioAsentamiento.entrySet()) {
            int key = entry.getKey();
            int valor = entry.getValue();
            switch (key){
                case 1 -> System.out.println("1- Propulsor MK1, coste: " + valor + " unidades de platino");
                case 2 -> System.out.println("2- Propulsor MK2, coste: " + valor + " unidades de platino");
                case 3 -> System.out.println("3- Propulsor MK3, coste: " + valor + " unidades de platino");
                case 4 -> System.out.println("4- Propulsor MK ULTRA, coste: " + valor + " unidades de platino");
                case 5 -> System.out.println("5- Escudo de Energía MK1, coste: " + valor + " unidades de uranio");
                case 6 -> System.out.println("6- Escudo de Energía MK2, coste: " + valor + " unidades de uranio");
                case 7 -> System.out.println("7- Escudo de Energía MK3, coste: " + valor + " unidades de uranio");
                case 8 -> System.out.println("8- Escudo de Energía MK ULTRA, coste: " + valor + " unidades de uranio");
            }
        }
    }


    //compra un item del asentamiento
    //recibe el scanner de la consola y el jugador actual
    private void compraAsentamiento(Scanner leeconsola, Jugador jugador){
        System.out.println("Items disponibles en este asentamiento:");
        mostrarItemsAsentamientoPorTipo();
        System.out.println("Ingrese el tipo del item que desea comprar:");
        int tipoItem = leeconsola.nextInt();
        int precioItem = this.inventarioAsentamiento.get(tipoItem);
        if (this.inventarioAsentamiento.containsKey(tipoItem)) {
            if (tipoItem <= 4){ //mejoras de propulsores, pide platino
                if (Inventario.getPlatino() < precioItem){ //no da el platino
                    System.out.println("Platino insuficiente, actualmente posees: " +Inventario.getPlatino());
                } else if(Inventario.getItems().contains(tipoItem)){ //ya tiene el item
                    System.out.println("Ya tienes este item");
                } else { // tiene platino suficiente y no tiene el item
                    System.out.println("Platino actual: " + Inventario.getPlatino() +" unidades\n" +
                            "Deseas comprar el item por " + precioItem + " unidades de platino?\n1- Si\n2- No");
                    int opcion = leeconsola.nextInt();
                    if (opcion == 1){
                        jugador.setPlatino(Inventario.getPlatino()-precioItem);
                        Inventario.getItems().add(tipoItem);
                        this.inventarioAsentamiento.remove(tipoItem);
                        System.out.println("Item comprado, platino restante: " + Inventario.getPlatino());
                    } else {
                        System.out.println("Compra cancelada");
                    }
                }
            } else { //mejoras de escudo, pide uranio
                if (Inventario.getUranio() <= precioItem){ //no da el uranio
                    System.out.println("Uranio insuficiente, actualmente posees: " +  Inventario.getUranio());
                } else if(Inventario.getItems().contains(tipoItem)){ //ya tiene el item
                    System.out.println("Ya tienes este item");
                } else { // tiene uranio suficiente y no tiene el item
                    System.out.println("Uranio actual: " + Inventario.getUranio() +" unidades\n" +
                            "Deseas comprar el item por " + precioItem + " unidades de uranio?\n1- Si\n2- No");
                    int opcion = leeconsola.nextInt();
                    if (opcion == 1){
                        jugador.setUranio(Inventario.getUranio()-precioItem);
                        Inventario.getItems().add(tipoItem);
                        this.inventarioAsentamiento.remove(tipoItem);
                        System.out.println("Item comprado, uranio restante: " + Inventario.getUranio());
                    } else {
                        System.out.println("Compra cancelada");
                    }
                }
            }
        } else {
            System.out.println("Item no disponible en este asentamiento");
        }
    }

    //cierra el scanner de esta clase
    public void cierraScannerOceanico(){
        if (leeconsola != null){
            leeconsola.close();
        }
    }


    //GETTERS Y SETTERS
    @Override
    public String getNombreAsentamiento() {
        return this.nombreAsentamiento;
    }
    public void setRadio(int radio){
        super.setRadio(radio);
    }
    @Override
    public void setCristalesHidrogeno(int cristalesHidrogeno) {
        super.setCristalesHidrogeno(cristalesHidrogeno);
    }
    public void setFloresDeSodio(int floresDeSodio) {
        super.setFloresDeSodio(floresDeSodio);
    }

}
