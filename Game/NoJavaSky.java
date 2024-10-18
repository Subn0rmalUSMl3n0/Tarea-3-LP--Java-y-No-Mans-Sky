package Game;


import Game.Planetas.*;
import Game.Protagonista.Inventario;
import Game.Protagonista.Jugador;
import Game.Protagonista.Nave;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NoJavaSky {
    //player, nave y mapa seran el mismo por todo el codigo asi que static
    static final Jugador player;
    static final Nave nave;
    static final MapaGalactico mapa;
    static final Inventario inventario;
    static boolean flagCentroUnico = true;
    static{
        player = new Jugador();
        nave = new Nave();
        mapa = new MapaGalactico();
        inventario = new Inventario();
    }

    //funcion main
    public static void main(String[] args) {
        boolean flag = true; //la flag comienza como true
        try (Scanner leeConsola = new Scanner(System.in)) {
            System.out.println("\n\nComienza la aventura:");
            player.mostrarJugador();
            nave.mostrarNave();
            //hasta que el jugador no se rinda o no llegue al centro galactico, el while no se rompe
            try {
                while(flag) {
                    try {
                        if ((MapaGalactico.isCentroCreado()) && (flagCentroUnico)) { //si el centro ya fue creado y si aun no se ejecuta este if
                            List<Planeta> listaPlanetas = mapa.getPlanetas();
                            int tipoUltimoPlaneta = listaPlanetas.getLast().getTipoPLaneta();
                            if (tipoUltimoPlaneta == 5) { //si el ultimo planeta creado es el Centro Galactico
                                Planeta newPlaneta = mapa.generadorPlanetas();
                                mapa.agregaPlanetas(newPlaneta);
                                flagCentroUnico = false; //cabmia el valor para que el if no se vuelva a ejecutar
                            }
                            Planeta.setCuentaVisitas(Planeta.getCuentaVisitas() + 1); //suma uno al contador de planetas visitados
                            System.out.println("\nDescubriste el Centro Galactico, ademas de un nuevo planeta\n");
                        }
                        
                        mostrarMenu();
                        flag = ejecutarOpciones(leeConsola);
                    } catch (Exception e) {
                        gameOver(mapa);
                    }
                }
            } catch (GameOverException e) {
                System.out.println("Game Over: " + e.getMessage());
                gameOver(mapa);
            }
        }
        cierraScanners();
    }

    //funcion que muestra el menu de opciones cuando el jugador esta en orbita
    private static void mostrarMenu(){
        System.out.println("Estas en la orbita del planeta " + mapa.getPlaneta(mapa.getPosicion()).getNombrePlaneta() +
                """
                
                Selecciona una opcion:
                1. Revisar estado de la nave y el traje
                2. Administrar inventario y mejorar equipamiento
                3. Ver lista de planetas ya descubiertos y recursos disponibles
                4. Viajar a un planeta
                5. Aterrizar en el planeta actual
                6. Rendirse""");
    }

    //ejecuta la opcion del menu elegida por el jugador
    //recibe un scanner para leer la consola, retorna true si el jugador no se rinde
    private static boolean ejecutarOpciones(Scanner leeconsola){
        int opcion = Integer.parseInt(leeconsola.nextLine());
        switch (opcion){
            case 1 -> { // muestra stats nave y jugador
                nave.mostrarNave();
                player.mostrarJugador();
            }
            case 2 -> preguntaInventario(leeconsola); //mostrar inventario y mejorar equipamiento

            case 3 -> mapa.mostrarPlanetas(); //mostrar planetas descubiertos y sus recursos

            case 4 -> preguntaViajes(leeconsola); //viajar planetas

            case 5 -> preguntaAterrizaje(leeconsola); //aterrizaje

            case 6 ->{ //salir del juego
                return preguntaSalir(leeconsola);
            }
            default -> System.out.println("Opcion no valida");
        }
        return true;
    }

    //muestra el inventario actual y pregunta si el jugador quiere mejorar su equipamiento
    //recibe un scanner para leer la consola
    private static void preguntaInventario(Scanner leeconsola){
        inventario.mostrarInventario();
        List<Integer> Items = Inventario.getItems();
        if(!Items.isEmpty()) {
            System.out.println("""
                            Deseas mejorar tu equipamiento?
                            1. Si
                            2. No""");
            int opcion2 = Integer.parseInt(leeconsola.nextLine());
            if (opcion2 == 1) {
                inventario.seleccionaMejoraInventario();
            } else System.out.println("Opcion no válida, no se realizaron mejoras");
        } else System.out.println("No hay items en el inventario");
    }

    //pregunta al jugador a que planeta quiere viajar y pregunta para confirmar el viaje
    //recibe un scanner para leer la consola
    private static void preguntaViajes(Scanner leeconsola) {
        int planetaElegido = NoJavaSky.mapa.mostrarDistancias(leeconsola);
        if (planetaElegido == NoJavaSky.mapa.getPosicion()) {
            System.out.println("Ya estás en este planeta");
        } else {

            System.out.println("Confirmas el viaje al planeta en la posicion " + planetaElegido + "\n1- Si\n2- No");
            int opcion2 = Integer.parseInt(leeconsola.nextLine());
            if (opcion2 == 1) {
                int distancia = Math.abs(NoJavaSky.mapa.getPosicion() - planetaElegido);
                boolean viajePosible;
                if (NoJavaSky.mapa.getPosicion() - planetaElegido > 0) { //la posicion actual es mayor que la del planeta, se mueve hacia la izquierda
                    viajePosible = NoJavaSky.nave.viajarPlaneta(NoJavaSky.mapa, -1, distancia);
                } else
                    viajePosible = NoJavaSky.nave.viajarPlaneta(NoJavaSky.mapa, 1, distancia); //la posicion actual es menor que la del planeta, se mueve a la derecha
                if (viajePosible) {
                    System.out.println("Viajando al planeta destino...");
                    System.out.println("Viaje al planeta " + NoJavaSky.mapa.getPosicion() + " completado"); //POPNER PONERP ONER PONER PONMER listaPlanetas.get(mapa.getPosicion()).getNombre()
                } else System.out.println("Imposible viajar al planeta seleccionado: combustible insuficiente");

            } else System.out.println("No se ha viajado a ningun planeta");
        }
    }

    // pregunta si el jugador quiere aterrizar en el planeta actual
    //recibe un scanner para leer la consola
    public static void preguntaAterrizaje(Scanner leeconsola){
        System.out.println("""
                        ¿Aterrizar en el planeta actual?
                        1. Si
                        2. No""");
        boolean flagAterrizaje;
        int opcion2 = Integer.parseInt(leeconsola.nextLine());
        if (opcion2 == 1) {

            Planeta planeta = mapa.getPlaneta(mapa.getPosicion());
            if (planeta.getTipoPLaneta() !=5) { // si es cualquier otro planeta salvo el centro galactico
                do {
                    flagAterrizaje = planeta.visitar(player);
                } while (flagAterrizaje);
                System.out.println("Vuelves a la orbita del planeta");

                if (!planeta.isVisitado()) {
                    planeta.setVisitado(true); // el estado del planeta cambia a visitado
                    Planeta.setCuentaVisitas(Planeta.getCuentaVisitas()+1); // y el contador de planetas aumenta en uno
                }
                if (Planeta.getCuentaVisitas() == MapaGalactico.getContadorPlanetas()) { // si se visitaron los n planetas disponibles, crea un planeta en la posicion n+1
                    Planeta newPlaneta = mapa.generadorPlanetas();
                    mapa.agregaPlanetas(newPlaneta);
                    System.out.println("Se ha descubierto el planeta " + newPlaneta.getNombrePlaneta() + "!"); // +nombreplaneta  );//POPNER PONERP ONER PONER PONMER listaPlanetas.get(mapa.getPosicion()).getNombre()
                }

            } else{ // el planeta es el centro galactico
                boolean flagAterrizajeCentro = CentroGalactico.puedeAterrizarCentro();
                if (flagAterrizajeCentro){ // la eficiencia del propulsor es mayor a 0.5 y se acaba el juego
                    System.out.println("\n\nFelicidades, lograste llegar al Centro Galáctico y completaste tu aventura");
                    cierraScanners();
                    System.exit(0);
                } else{ // no da la eficiciencia, muestra la eficiencia actual
                    System.out.println("La eficiencia actual es: " +Nave.getEficienciaPropulsor() + ", necesitas al menos 0.5 de eficiencia para aterrizar en el Centro Galactico");
                }
            }
        } else System.out.println("No se ha aterrizado en el planeta");
    }

    //pregunta si el jugador quiere salir del juego
    //recibe un scanner para leer la consola, retorna true si el jugador no se rinde
    public static boolean preguntaSalir(Scanner leeconsola){
        System.out.println("""
                        ¿Abandonas el viaje?
                        1. Si
                        2. No""");
        int opcion2 = Integer.parseInt(leeconsola.nextLine());
        if (opcion2 == 1) {
            System.out.println("Adiós");
            return false;
        } else {
            System.out.println("Que continúe el viaje");
            return true;
        }
    }


    //si el jugador se queda sin escudo en su exploracion, se resetean las estadisticas de la nave, del jugador y del inventario. El jugador vuelve a la posicion 0 del mapa con todos los planetas descubiertos hasta la fecha
    //recibe el mapa actual
    public static void gameOver(MapaGalactico mapa){
        Nave.setUnidadesCombustible(100);
        Nave.setEficienciaPropulsor(0);
        Jugador.setUnidadesEnergiaProteccion(100);
        Jugador.setEficienciaEnergiaProteccion(0);
        Inventario.setUranio(0);
        Inventario.setSodio(0);
        Inventario.setPlatino(0);
        Inventario.setHidrogeno(0);
        Inventario.setItems(new ArrayList<>());
        mapa.setPosicion(0);
    }

    //cierra los scanners de cada planeta y el del inventario, para liberar memoria
    private static void cierraScanners(){
        for (Planeta planeta : mapa.getPlanetas()) {
            planeta.cierraScanner();
            switch (planeta) {
                case Oceanico oceanico -> oceanico.cierraScannerOceanico();
                case Helado helado -> helado.cierraScannerHelado();
                default -> {
                }
            }
        }
        inventario.CierraScanner();
    }


}
