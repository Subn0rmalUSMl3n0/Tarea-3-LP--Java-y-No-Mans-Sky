package Game.LogicaNombres;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class NombresAsentamientos {

     //genera un nombre para un asentamiento de planeta Helado y Oceanico, y le agrega una cardinalidad
    //recibe un entero que indica el tipo de planeta, retorna el nombre y la cardinalidad que tendran el asentamiento
    public static String getNombreAsentamiento(int tipoPlaneta){
        String nombreAsentamiento = "";
        Random rand = new Random();
        switch (tipoPlaneta){
            case 1 -> nombreAsentamiento = nombresHelados(rand);
            case 2 -> nombreAsentamiento = nombresOceanicos(rand);
        }
        int cardinal = rand.nextInt(4);
        switch (cardinal){
            case 0 -> {
                return nombreAsentamiento + " del norte";
            }
            case 1 -> {
                return nombreAsentamiento + " del sur";
            }
            case 2 -> {
                return nombreAsentamiento + " del este";
            }
            default -> {
                return nombreAsentamiento + " del oeste";
            }
        }
    }

    //lee el archivo asentamientosHelados y de ahi selecciona un nombre al azar para los asentamientos de los planetas helados
    //recibe un objeto Random, retorna el nombre que tendra el asentamiento de un planeta helado
    private static String nombresHelados(Random rand){
        String chosen = "";
        String archivo = "Game/LogicaNombres/asentamientosHelados.txt";
        try {
            List<String> lineas = Files.readAllLines(Paths.get(archivo));
            int i = rand.nextInt(lineas.size());
            chosen = lineas.get(i);
        }  catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return chosen;
    }

    //lee el archivo asentamientosHelados y de ahi selecciona un nombre al azar para los asentamientos de los planetas oceanicos
    //recibe un objeto Random, retorna el nombre que tendra el asentamiento de un planeta oceanico
    private static String nombresOceanicos(Random rand){
        String chosen = "";
        String archivo = "Game/LogicaNombres/asentamientosOceanicos.txt";
        try {
            List<String> lineas = Files.readAllLines(Paths.get(archivo));
            int i = rand.nextInt(lineas.size());
            chosen = lineas.get(i);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return chosen;
    }

}
