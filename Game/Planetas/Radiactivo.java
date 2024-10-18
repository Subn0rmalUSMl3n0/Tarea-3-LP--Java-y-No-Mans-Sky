package Game.Planetas;

public class Radiactivo extends Planeta {

    //constructor, a y b es el intervalo del radio,hidrogeno y sodio se sacan con base en eso
    public Radiactivo(){
        super(3);
        int a = (int) Math.pow(10,4);
        int b = (int) Math.pow(10,5);
        int radio = rand(a,b);
        setRadio(radio);
        int hidrogeno = (int) (0.2 *(4*Math.PI*Math.pow(radio,2)));
        setCristalesHidrogeno(hidrogeno);
        int sodio = (int) (0.25 *(4*Math.PI*Math.pow(radio,2)));
        setFloresDeSodio(sodio);

        int radiacion = rand(10, 50);
        double uranio = 0.25 * (4 * Math.PI * Math.pow(radio, 2)) * radiacion;
        super.setConsumoEnergia(0.3F * radiacion);
        setEspecialPlaneta(radiacion);
        setMineralEspecial(uranio);
    }

    @Override
    public void mostrarItemAsentamiento() {} //no se implementa en este planeta


    //GETTERS Y SETTERS
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
