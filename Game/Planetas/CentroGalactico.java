package Game.Planetas;

public class CentroGalactico extends Planeta {

    //Constructor con todos los atributos al 0 salvo el tipo de planeta
    public CentroGalactico(){
        super(5);
        setRadio(0);
        super.setConsumoEnergia(0);
        setCristalesHidrogeno(0);
        setFloresDeSodio(0);
        setEspecialPlaneta(0);
        setMineralEspecial(0);
    }

    //revisa si la eficiencia del propulsor es mayor o igual a 0.5, y si lo es, se puede aterrizar en el centro galactico
    // retorna true si se puede aterrizar, false si no
    public static boolean puedeAterrizarCentro(){
        float eficienciaPropulsor = Game.Protagonista.Nave.getEficienciaPropulsor();
        return eficienciaPropulsor >= 0.5F;
    }
    //SETTERS
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

    //funciones no implementadas
    @Override
    public void mostrarItemAsentamiento() {}
}
