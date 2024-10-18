package Game.Planetas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Game.Protagonista.Nave;

public class MapaGalactico {
    private int posicion;
    private static int contadorPlanetas = 0; //guarda la cantidad de planetas actualmente creados
    private static boolean CentroCreado; // el valor comienza fake
    private static final List<Planeta> planetas; // lista de planetas a guardar
    static{
        planetas = new ArrayList<>();
        CentroCreado = false;
    }


    //constructor vacio inicial, crea un planeta inicial
    public MapaGalactico() {
        Planeta newPlaneta = generadorPlanetas();
        agregaPlanetas(newPlaneta);
    }

    //constructor que agrega un nuevo planeta al mapa
    //recibe un planeta y lo agrega a la lista de planetas
    public void agregaPlanetas(Planeta planeta){
        planetas.add(planeta);
    }

    //genera un tipo de planeta aleatorio segun los porcentajes dados
    //retorna un planeta de un tipo aleatorio
    public Planeta generadorPlanetas() {
        contadorPlanetas++;

        Random rand = new Random();
        //crea un numero entre 1 y 100, asegurandose de que el centro galactico solo se cree una vez
        int decidePlanetas = rand.nextInt(100)+1;

        // el protected hace que generacionprocedural pueda usarla
        int tipoPlaneta;
        if (decidePlanetas <= 30) { // Planeta Helado
            tipoPlaneta = 1;
        } else if (decidePlanetas <= 60){ // Planeta Oceanico
            tipoPlaneta =2;
        } else if (decidePlanetas <= 80){ // Planeta Radiactivo
            tipoPlaneta = 3;
        } else if (decidePlanetas <= 99){ // Planeta Volcanico
            tipoPlaneta = 4;
        } else { // Centro Galáctico
            if (!CentroCreado) {
                tipoPlaneta = 5;
                CentroCreado = true;
            } else {
                tipoPlaneta = rand.nextInt(4) + 1; // Genera un planeta aleatorio (1 a 4) si el Centro Galáctico ya se creo
            }
        }

        return Planeta.creaPlaneta(tipoPlaneta);
    }

    //itera por toda la lista de planetas y llama a printeaPlaneta para que muestre por pantalla los distintos tipos de planetas con sus datos
    public void mostrarPlanetas(){
        System.out.println("Lista de Planetas localizados:");
        int index = 0;
        for (Planeta planeta : planetas) {
            int tipoPlaneta = planeta.getTipoPLaneta(); // Obtener el tipo de cada planeta
            switch (tipoPlaneta) {
                case 1 -> {
                    printeaPlaneta("helado", planeta.getNombrePlaneta(), "Temperatura: " + planeta.getEspecialPlaneta() + " °C", "", planeta, index);
                    System.out.println("  Civilizacion: Los " + planeta.getNombreAsentamiento() + "\n" +
                            "  Items que venden: ");
                    planeta.mostrarItemAsentamiento();
                    System.out.println();
                }
                case 2 -> {
                    printeaPlaneta("oceanico",planeta.getNombrePlaneta(), "Profundidad: " + planeta.getEspecialPlaneta() + " metros", "", planeta,index);
                    System.out.println("  Civilizacion: Los " + planeta.getNombreAsentamiento()+ "\n" +
                            "  Items que venden: ");
                    planeta.mostrarItemAsentamiento();
                    System.out.println();
                }
                case 3 -> printeaPlaneta("radiactivo",planeta.getNombrePlaneta(), "Radiación: " + planeta.getEspecialPlaneta() + " Unidades\n", planeta.getMineralEspecial() + " Uranio\n  ",planeta,index);
                case 4 -> printeaPlaneta("volcanico",planeta.getNombrePlaneta(), "Temperatura: " + planeta.getEspecialPlaneta() + " °C\n", planeta.getMineralEspecial() + " Platino\n  ", planeta,index);
                case 5 -> System.out.println(index + "  - Centro Galáctico, el final del viaje");
            }
            index++;
        }
    }


    //muestra por pantalla los datos de cada planeta descubierto, segun un formato
    //recibe los distintos parametros que se van a printear
    public void printeaPlaneta(String tipoPlaneta, String nombrePlaneta, String propiedad, String mineral, Planeta planActual, int index){
        System.out.println(index + "  - Planeta " + nombrePlaneta + ", de tipo " +tipoPlaneta + ", con:\n  " + planActual.getFloresDeSodio() + " Flores de Sodio\n  " +
                        planActual.getCristalesHidrogeno() + " Cristales de Hidrogeno\n  " + mineral + planActual.getRadio() +
                        " metros de radio\n   " + propiedad);

    }

    //muestra las distancias del planeta actual a los demas planetas, y pide al jugador que elija a cual viajar
    //recibe un Scanner para leer la consola, retorna el planeta al que se quiere viajar
    public int mostrarDistancias(Scanner leeConsolas){
        int index = 0;
        int planetaElegido;
        for (Planeta planeta : planetas) {
            int distancia = Math.abs(this.posicion - planetas.indexOf(planeta));
            int tamanoSalto = (int) (0.75F * (float) Math.pow(distancia*3.5,2) * (1 - Nave.getEficienciaPropulsor()));
            int tipoPlaneta = planeta.getTipoPLaneta(); // Obtener el tipo de cada planeta
            switch (tipoPlaneta) {
                case 1 -> System.out.print(index + "- Planeta " + planeta.getNombrePlaneta()+ ", de bioma helado, distancia:  " + distancia + " UA, costo del viaje:  " + tamanoSalto);
                case 2 -> System.out.print(index + "- Planeta " + planeta.getNombrePlaneta()+ ", de bioma oceanico, distancia:  " + distancia + " UA, costo del viaje:  " + tamanoSalto);
                case 3 -> System.out.print(index + "- Planeta " + planeta.getNombrePlaneta()+ ", de bioma radiactivo, distancia:  " + distancia + " UA, costo del viaje:  " + tamanoSalto);
                case 4 -> System.out.print(index + "- Planeta " + planeta.getNombrePlaneta()+ ", de bioma volcanico, distancia:  " + distancia + " UA, costo del viaje:  " + tamanoSalto);
                case 5 -> System.out.print(index + "- Centro Galactico, distancia:    " + distancia + "UA,  costo del viaje:  " + tamanoSalto);
            }
            index++;
            if (distancia  == 0) {
                System.out.println("     Esta es tu posicion actual");
            }else System.out.println();
        }
        System.out.println("Selecciona el planeta al cual viajar:");
        do {
            planetaElegido = Integer.parseInt(leeConsolas.nextLine());
            if(planetaElegido< 0 || planetaElegido > MapaGalactico.getContadorPlanetas()-1) System.out.println("Opcion incorrecta, elige de nuevo");
        } while(planetaElegido < 0 || planetaElegido > MapaGalactico.getContadorPlanetas()-1);
        return planetaElegido;
    }


    //getters y setters
    public Planeta getPlaneta(int posicion){
        return planetas.get(posicion);
    }

    public int getPosicion() {
        return posicion;
    }
    public void setPosicion(int posicion){
        this.posicion = posicion;
    }

    public List<Planeta> getPlanetas() {
        return planetas;
    }

    public static int getContadorPlanetas() {
        return contadorPlanetas;
    }

    public static boolean isCentroCreado() {
        return CentroCreado;
    }

}
