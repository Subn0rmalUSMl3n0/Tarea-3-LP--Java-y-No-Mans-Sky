package Game;

//este error sirve para manejar el game over
public class GameOverException extends RuntimeException {
    //recibe un mensaje que se mostrara en pantalla
    public GameOverException(String mensaje) {
        NoJavaSky.gameOver(NoJavaSky.mapa);
        System.out.println("Game Over: " + mensaje);
    }
}
