package Game.Protagonista;


public class Jugador {
    private static float unidadesEnergiaProteccion;
    private static float eficienciaEnergiaProteccion;


    //constructor inicial, comienza con 100 de bencina, y la eficiencia la devuelve a 0
    public Jugador(){
        unidadesEnergiaProteccion = 100.0F;
        eficienciaEnergiaProteccion = 0.0F;
    }

    //recarga automaticamente la energia del escudo dependiendo de cuantos cristales de sodio tenga el jugador actualmente y si tiene o no el escudo al tope
    //recibe la cantidad de sodio actual del inventario
    public void recargarEnergiaProteccion(int sodio){
        float escudoNecesario = 100 - getUnidadesEnergiaProteccion();
        System.out.println("\nEnergia del escudo actual: " + getUnidadesEnergiaProteccion() + ", cantidad de sodio actual: " + sodio+ "\nCada unidad de sodio recarga: " + 0.65F*(1+getEficienciaEnergiaProteccion()) + " unidades de energia");
        int sodioNecesario = (int) (escudoNecesario/(0.65*(1+getEficienciaEnergiaProteccion())));
        if(escudoNecesario == 0){
            System.out.println("No se necesita recargar energia\n");
        } else if (sodio == 0){
            System.out.println("No hay sodio en el inventario\n");
        } else if (sodio >= sodioNecesario) { //hay mas sodio en el inventario que el necesario para recargar
            System.out.println("Recargando energia...");
            setUnidadesEnergiaProteccion(100); // el escudo se carga sin problemas
            Inventario.setSodio(Inventario.getSodio()-sodioNecesario);
            System.out.println("Recarga de energia completada\nNueva cantidad de energia: " + getUnidadesEnergiaProteccion()+ "\nSodio restante: " + Inventario.getSodio()+ "\n");
        } else { // si no hay suficiente sodio en el inventario para una recarga completa
            System.out.println("Recargando energia...");
            setUnidadesEnergiaProteccion(getUnidadesEnergiaProteccion() +0.65F*sodio*(1+getEficienciaEnergiaProteccion()));
            Inventario.setSodio(Inventario.getSodio()-sodio);
            System.out.println("Recarga de energia completada\nNueva cantidad de energia: " + getUnidadesEnergiaProteccion()+ "\nSodio restante: " + Inventario.getSodio()+ "\n");
        }
    }

    //muestra las estadisticas del jugador
    public void mostrarJugador(){
        System.out.println("Jugador:     Energia Proteccion:     " + unidadesEnergiaProteccion+ "    Eficiencia Escudo: "+ eficienciaEnergiaProteccion);
    }




    //GETTERS Y SETTERS
    public static float getUnidadesEnergiaProteccion() {
        return unidadesEnergiaProteccion;
    }
    public static void setUnidadesEnergiaProteccion(float unidadesEnergiaProteccion) {
        Jugador.unidadesEnergiaProteccion = unidadesEnergiaProteccion;
    }

    public static float getEficienciaEnergiaProteccion() {
        return eficienciaEnergiaProteccion;
    }
    public static void setEficienciaEnergiaProteccion(float eficienciaEnergiaProteccion) {
        Jugador.eficienciaEnergiaProteccion = eficienciaEnergiaProteccion;
    }

    public void setPlatino(int platino){
        Inventario.setPlatino(platino);
    }
    public void setUranio(int uranio){
        Inventario.setUranio(uranio);
    }
}
