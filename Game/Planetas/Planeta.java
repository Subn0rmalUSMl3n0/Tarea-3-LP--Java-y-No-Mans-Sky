package Game.Planetas;

import Game.GameOverException;
import Game.LogicaNombres.NombresPlanetas;
import Game.Protagonista.Inventario;
import Game.Protagonista.Jugador;
import Game.Protagonista.Nave;
import java.util.Random;
import java.util.Scanner;

public abstract class Planeta {
    private int radio;
    private int cristalesHidrogeno;
    private int floresDeSodio;
    private float consumoEnergia;
    private int especialPlaneta;
    private double mineralEspecial;
    private final int tipoPLaneta;
    private String nombreAsentamiento;
    private final String nombrePlaneta;
    private boolean visitado;
    private static int cuentaVisitas = 0;
    private final Scanner leeconsola;

    //constructor planetas
    //recibe el tipo de planeta, y llama a la clase NombresPlanetas para obtener el nombre del planeta
    public Planeta(int tipoPlaneta){
        this.tipoPLaneta = tipoPlaneta;
        this.nombrePlaneta = NombresPlanetas.getNombrePlaneta(this.tipoPLaneta);
        this.visitado = false;
        leeconsola = new Scanner(System.in);
    }


    //logica de visitar Planetas, cada turno pregunta que quiere hacer el jugador, y se le quita energia al traje segun el planeta
    //recibe el jugador actual, retorna si se quiere salir del planeta o no
    public boolean visitar(Jugador jugador){
        boolean flagSalir = true;
        if(Jugador.getUnidadesEnergiaProteccion() <= 0){
            JuegoAcabado(true);
        }else{
            if (this.getTipoPLaneta() <=2){ //si es oceanico o helado, tienen asentamiento, hidrogeno y flores
                System.out.println("Planeta actual: " + this.getNombrePlaneta());
                System.out.println("Energia del traje restante: " + Jugador.getUnidadesEnergiaProteccion() + "\n" +
                        "Combustible restante: " + Nave.getUnidadesCombustible() + "\n");
                System.out.println("""
                        ¿Que deseas hacer?
                        1- Extraer cristales de hidrogeno
                        2- Extraer flores de sodio
                        3- Visitar el asentamiento de este planeta
                        4- Recargar escudo personal
                        5- Recargar combustible de la nave
                        6- Salir del planeta""");
                int eleccion = leeconsola.nextInt();
                switch (eleccion){ //dependiendo de la opcion, llama a una funcion  que la procesa u otra
                    case 1 -> Inventario.setHidrogeno(Inventario.getHidrogeno()+ extraerRecursos(1));
                    case 2 -> Inventario.setSodio(Inventario.getSodio()+ extraerRecursos(2));
                    case 3 -> ((tieneAsentamientos) this).visitarAsentamiento(jugador);
                    case 4 ->  jugador.recargarEnergiaProteccion(Inventario.getSodio());
                    case 5 -> Nave.recargarPropulsores(Inventario.getHidrogeno());
                    case 6 -> flagSalir= salir();
                }
                //reduce la energia del traje segun el planeta
                if (eleccion !=6) {
                    reduceEscudo((float) Math.abs(getEspecialPlaneta()) /5*(1-Jugador.getEficienciaEnergiaProteccion()));
                    if(this.getTipoPLaneta() ==1) {System.out.println("Debido a las fuertes ventiscas, pierdes " +  Math.abs(getEspecialPlaneta())/5*(1-Jugador.getEficienciaEnergiaProteccion())+ " unidades del escudo personal");
                    }else System.out.println("Debido a las fuertes maraeas, pierdes " + getEspecialPlaneta()/5*(1-Jugador.getEficienciaEnergiaProteccion()) +" unidades del escudo personal");
                }

            } else if (this.getTipoPLaneta() == 3) { //si es radiactivo
                System.out.println("Planeta actual: " + this.getNombrePlaneta());
                System.out.println("Energia del traje restante: " + Jugador.getUnidadesEnergiaProteccion());
                System.out.println("""
                        ¿Que deseas hacer?
                        1- Extraer cristales de hidrogeno
                        2- Extraer flores de sodio
                        3- Extraer uranio
                        4- Recargar escudo personal
                        5- Recargar combustible de la nave
                        6- Salir del planeta""");
                int eleccion = leeconsola.nextInt();
                switch (eleccion){ //dependiendo de la opcion, llama a una funcion  que la procesa u otra
                    case 1 -> Inventario.setHidrogeno(Inventario.getHidrogeno()+ extraerRecursos(1));
                    case 2 -> Inventario.setSodio(Inventario.getSodio() + extraerRecursos(2));
                    case 3 -> Inventario.setUranio(Inventario.getUranio() + extraerRecursos(3));
                    case 4 -> jugador.recargarEnergiaProteccion(Inventario.getSodio());
                    case 5 -> Nave.recargarPropulsores(Inventario.getHidrogeno());
                    case 6 -> flagSalir= salir();
                }
                if (eleccion!=6) {
                    //reduce la energia del traje segun el planeta
                    reduceEscudo((float) getEspecialPlaneta() /2*(1-Jugador.getEficienciaEnergiaProteccion()));
                    System.out.println("Debido a la radiacion, pierdes " + getEspecialPlaneta()/2*(1-Jugador.getEficienciaEnergiaProteccion()) +" unidades del escudo personal");
                }

            } else if (this.getTipoPLaneta() == 4) { //volcanico
                System.out.println("Planeta actual: " + this.getNombrePlaneta());
                System.out.println("Energia del traje restante: " + Jugador.getUnidadesEnergiaProteccion());
                System.out.println("""
                        ¿Que deseas hacer?
                        1- Extraer cristales de hidrogeno
                        2- Extraer platino
                        3- Recargar escudo personal
                        4- Recargar combustible de la nave
                        5- Salir del planeta""");
                int eleccion = leeconsola.nextInt();
                switch (eleccion) { //dependiendo de la opcion, llama a una funcion  que la procesa u otra
                    case 1 -> Inventario.setHidrogeno(Inventario.getHidrogeno() + extraerRecursos(1));
                    case 2 -> Inventario.setPlatino(Inventario.getPlatino() + extraerRecursos(4));
                    case 3 -> jugador.recargarEnergiaProteccion(Inventario.getSodio());
                    case 4 -> Nave.recargarPropulsores(Inventario.getHidrogeno());
                    case 5 -> flagSalir= salir();
                }
                if (eleccion!=5) {
                    //reduce la energia del traje segun el planeta
                    reduceEscudo((float) getEspecialPlaneta() /10*(1-Jugador.getEficienciaEnergiaProteccion()));
                    System.out.println("Debido a las altas temperaturas, pierdes " + getEspecialPlaneta()/10*(1-Jugador.getEficienciaEnergiaProteccion())+" unidades del escudo personal");
                }
            }
        }
        return flagSalir;
    }

    //pregunta si se quiere salir del planeta actual
    // retorna si se quiere salir del planeta o no
    private boolean salir(){
        System.out.println("""
                Deseas salir del planeta actual?
                1. Si
                2. No""");
        int eleccion = leeconsola.nextInt();
        if (eleccion == 1){
            System.out.println("Volviendo a órbita");
            return false;
        } else {
            System.out.println("Continuas en el planeta actual");
            return true;
        }
    }

    //segun el tipo de planeta, llama a la funcion extraerRecursosAux con el tipo de mineral a picar
    //recibe el tipo de mineral a picar, retorna la cantidad de mineral picado
    public int extraerRecursos(int tipo){
        float energiaActual = Jugador.getUnidadesEnergiaProteccion();
        int extraidos = 0;
        switch (tipo){
            case 1-> extraidos = extraeRecursosAux(leeconsola,energiaActual, this.consumoEnergia, "cristales de hidrogeno");//hidrogeno
            case 2-> extraidos = extraeRecursosAux(leeconsola,energiaActual,this.consumoEnergia,"flores de sodio");
            case 3-> extraidos = extraeRecursosAux(leeconsola,energiaActual,this.consumoEnergia,"uranio");
            case 4-> extraidos = extraeRecursosAux(leeconsola,energiaActual,this.consumoEnergia,"platino");
        }
        return extraidos;
    }

    //maneja la extraccion de recursos, actualizando los valores de los minerales en el inventario y en el planeta, y restando energia al trraje
    // recibe el scanner para leer la consola, la energia actual del traje, el costo unitario de la extraccion y el mineral a extraer, retorna la cantidad de mineral extraido
    private int extraeRecursosAux(Scanner opcion, float energiaActual, float costoUnitario, String mineral){
        float costoEnergia = (float) (0.5 * costoUnitario / 100 * (1 - Jugador.getEficienciaEnergiaProteccion()));
        System.out.println("Unidades de " +mineral + " restantes en este planeta:  " + this.getCristalesHidrogeno() + "\n" +
                "Costo de extraccion por unidad: " +costoEnergia+"\nEnergia del escudo restante: " + Jugador.getUnidadesEnergiaProteccion());
        boolean flag =true;
        int consolaExtraidos;
        do {
            System.out.println("Ingrese la cantidad de " + mineral + " (en enteros) a extraer:");
            consolaExtraidos = opcion.nextInt();
            System.out.println("Esto consumirá " + consolaExtraidos *costoEnergia + " unidades de energia, ¿Confirmas?\n1-Si\n2-No");
            int consolaConfirma = opcion.nextInt();
            if(consolaConfirma ==1) {
                if (costoEnergia * consolaExtraidos > energiaActual){
                    System.out.println();
                    JuegoAcabado(true);
                } else {
                    flag = false;
                    System.out.println("Minerales extraidos con exito");
                }
            }
        } while (flag);
        Jugador.setUnidadesEnergiaProteccion(Jugador.getUnidadesEnergiaProteccion()- (consolaExtraidos * costoEnergia));
        this.setCristalesHidrogeno(this.getCristalesHidrogeno()- consolaExtraidos);
        return consolaExtraidos;
    }



    //logica de creacion procedural de planetas, llama al constructor de un planeta especifico segun el tipo de planeta que haya sido generado
    //recibe el tipo de planeta, retorna un planeta del tipo correspondiente
    public static Planeta creaPlaneta(int tipoPlaneta) {
        return switch (tipoPlaneta) {
            case 1 -> new Helado(); // Devuelve un planeta Helado
            case 2 -> new Oceanico(); // Devuelve un planeta Oceanico
            case 3 -> new Radiactivo(); // Devuelve un planeta Radiactivo
            case 4 -> new Volcanico(); // Devuelve un planeta Volcánico
            case 5 -> new CentroGalactico(); // Devuelve el Centro Galáctico (si es que tienes una clase definida)
            default -> throw new IllegalStateException("Si aparece esto es que el codigo murio en creaPlaneta " + tipoPlaneta);
        };
    }

    //reduce la energia del traje segun la cantidad de energia que se le pase
    //recibe la cantidad de energia a quitar del traje
    public void reduceEscudo(float cantidad){
        Jugador.setUnidadesEnergiaProteccion(Jugador.getUnidadesEnergiaProteccion()-cantidad);
    }


    public abstract void mostrarItemAsentamiento(); //funcion abstracta que se implementara en Helado y en Oceanico




    //excepcion que se lanza si el jugador se queda sin energia del traje
    public void JuegoAcabado(boolean gameOver){
        if(gameOver){
            throw new GameOverException(" intentando extraer el mineral, te quedas sin energia para el traje y mueres.\nLos datos del mapa galactico fueron transmitidos a un nuevo explorador espacial, que comienza con un inventario vacío y un nuevo traje y nave espacial");
        }
    }


    //devuelve un numero aleatorio entre a y b
    //recibe los enteros 10^a y 10^b, retorna un numero entre 10^a y 10^b
    public static int rand(int a, int b){ // si es protected, las clases hija van a poder usar esta funcion
        Random rand = new Random();
        return  rand.nextInt(b-a + 1)+a; //retorna numero entre [a,b]

    }

    //cierra el scanner
    public void cierraScanner(){
        if (leeconsola != null){
            leeconsola.close();
        }
    }





    //Getter y setters
    public int getRadio() {return radio;}

    public int getCristalesHidrogeno() {return cristalesHidrogeno;}

    public void setCristalesHidrogeno(int cristalesHidrogeno) {
        this.cristalesHidrogeno = cristalesHidrogeno;
    }

    public int getFloresDeSodio() {return floresDeSodio;}

    public int getEspecialPlaneta() {return especialPlaneta;}
    public void setEspecialPlaneta(int especialPlaneta) {this.especialPlaneta = especialPlaneta;}

    public double getMineralEspecial() {return mineralEspecial;}
    public void setMineralEspecial(double mineralEspecial) {this.mineralEspecial = mineralEspecial;}

    public int getTipoPLaneta() {return tipoPLaneta;}

    public String getNombrePlaneta() {return nombrePlaneta;}

    public void setNombreAsentamiento(String nombreAsentamiento) {this.nombreAsentamiento = nombreAsentamiento;}
    public String getNombreAsentamiento() {return nombreAsentamiento;}

    public static int getCuentaVisitas() {return cuentaVisitas;}
    public static void setCuentaVisitas(int cuentaVisitas) {Planeta.cuentaVisitas = cuentaVisitas;}

    public boolean isVisitado() {return visitado;}
    public void setVisitado(boolean visitado) {this.visitado = visitado;}

    public void setRadio(int radio) {
        this.radio = radio;
    }
    public void setFloresDeSodio(int floresDeSodio) {
        this.floresDeSodio = floresDeSodio;
    }

    public void setConsumoEnergia(float consumoEnergia) {
        this.consumoEnergia = consumoEnergia;
    }

}
