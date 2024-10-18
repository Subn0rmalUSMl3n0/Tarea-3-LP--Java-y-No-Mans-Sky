package Game.Protagonista;

import Game.Planetas.MapaGalactico;


public class Nave {
    private static float eficienciaPropulsor;
    private static float unidadesCombustible;

    //constructor inicial de nave, comienza con 100 de bencina y 0 de eficiencia
    public Nave(){
        eficienciaPropulsor = 0.0F;
        unidadesCombustible = 100.0F;
    }

    // procesa el viaje de la nave, revisando si la bencina actual de la nave es suficiente,
    //recibe el mapa actual, la direccion del viaje, y de cuanto es el tamaño del salto
    public boolean viajarPlaneta(MapaGalactico MP, int direccion, int tamanoSalto){
        int posActual = MP.getPosicion();
        int viaje = tamanoSalto * direccion; //direccion sera 1 o -1
        int newPos = viaje + posActual;
        System.out.println(newPos);

        float unidadesConsumidas =  (0.75F * (float) Math.pow(tamanoSalto*3.5,2) * (1 - Nave.getEficienciaPropulsor()));
        float newBencina = getUnidadesCombustible() - unidadesConsumidas;
        if (unidadesConsumidas<=100 && newBencina >=0) {
            MP.setPosicion(newPos);
            setUnidadesCombustible(newBencina);
            return true;
        }
        return false;
    }

    //muestra las estadisticas del jugador
    public void mostrarNave(){
        System.out.println("Nave:       Combustible Restante:    " + unidadesCombustible + "    Eficiencia Propulsor: " + eficienciaPropulsor );
    }

    //recarga automaticamente la bencina dependiendo de cuantos cristales de hidrogeno tenga el jugador actualmente y si tiene o no la nave al tope de bencina
    //recibe la cantidad de hidrogeno actual del inventario
    public static void recargarPropulsores(int hidrogeno){
        float bencinaNecesaria = 100 - getUnidadesCombustible();
        System.out.println("\nCombustible actual: " + getUnidadesCombustible() + ", cantidad de hidrogeno actual: " + hidrogeno+ "\nCada unidad de hidrogeno recarga: " + 0.6F*(1+getEficienciaPropulsor()) + " unidades de combustible");
        int hidroNecesario = (int) (bencinaNecesaria/(0.6*(1+getEficienciaPropulsor())));
        if (bencinaNecesaria == 0) {
            System.out.println("No se necesita recargar combustible\n");
        }else if(hidrogeno == 0){
            System.out.println("No hay hidrogeno en el inventario\n");
        } else if (hidrogeno >= hidroNecesario) { //si hay mas hidrogeno en el inventario que del necesario para cargar la nave
            System.out.println("Recargando combustible...");
            setUnidadesCombustible(100); // la nave se carga sin problemas
            Inventario.setHidrogeno(Inventario.getHidrogeno()-hidroNecesario);
            System.out.println("Recarga de combustible completada\nNueva cantidad de combustible: " + getUnidadesCombustible()+ "\nHidrogeno restante: " + Inventario.getHidrogeno()+ "\n");
        } else { // si no hay suficiente hidrogeno en el inventario para una recarga completa
            System.out.println("Recargando combustible...");
            setUnidadesCombustible(getUnidadesCombustible() +0.6F*hidrogeno*(1+getEficienciaPropulsor()));
            Inventario.setHidrogeno(Inventario.getHidrogeno()-hidrogeno);
            System.out.println("Recarga de combustible completada\nNueva cantidad de combustible: " + getUnidadesCombustible()+ "\nHidrogeno restante: " + Inventario.getHidrogeno()+ "\n");
        }
    }






    //GETTERS Y SETTERS
    public static float getUnidadesCombustible() {
        return unidadesCombustible;
    }
    public static void setUnidadesCombustible(float unidadesCombustible) {Nave.unidadesCombustible = unidadesCombustible;}

    public static float getEficienciaPropulsor() {
        return eficienciaPropulsor;
    }
    public static void setEficienciaPropulsor(float eficienciaPropulsor) {Nave.eficienciaPropulsor = eficienciaPropulsor;}
}
