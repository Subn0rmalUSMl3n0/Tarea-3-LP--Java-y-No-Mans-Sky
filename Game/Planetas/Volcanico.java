package Game.Planetas;

public class Volcanico extends Planeta {

    //constructor, a y b es el intervalo del radio,hidrogeno y sodio se sacan con base en eso
    public Volcanico(){
        super(4);
        int a = (int) Math.pow(10,3);
        int b = (int) Math.pow(10,5);
        int radio = rand(a,b);
        setRadio(radio);
        int hidrogeno = (int) (0.3 *(4*Math.PI * Math.pow(radio,2)));
        setCristalesHidrogeno(hidrogeno);
        int sodio = 0;
        setFloresDeSodio(sodio);

        int temperatura = rand(120, 256);
        super.setConsumoEnergia( 0.08F * temperatura);
        double platino = 0.25 * (4 * Math.PI * Math.pow(radio, 2)) - (20.5 * Math.pow(temperatura, 2));
        setEspecialPlaneta(temperatura);
        setMineralEspecial(platino);

    }


    @Override
    public void mostrarItemAsentamiento() {}

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
