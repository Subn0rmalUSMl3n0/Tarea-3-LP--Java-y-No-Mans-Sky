package Game.LogicaNombres;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class NombresPlanetas {

    //genera un nombre para el planeta nuevo segun el tipo de planeta, y le agrega numeros al azar al final
    //recibe un entero que indica el tipo de planeta, retorna el nombre que tendra el planeta y numeros al azar
    public static String getNombrePlaneta(int tipoPlaneta){
        String nombrePlaneta = "";
        Random rand = new Random();
        switch (tipoPlaneta){
            case 1 -> nombrePlaneta = nombresHelados(rand);
            case 2 -> nombrePlaneta = nombresAcuaticos(rand);
            case 3 -> nombrePlaneta = nombresRadiactivos(rand);
            case 4 -> nombrePlaneta = nombresVolcanicos(rand);
            case 5 -> {
             return "Centro Galactico";
            }//si es el centro galactico, no necesita un nombre diferente
        }
        int a = rand.nextInt(903)+1;
        int b = rand.nextInt(50)+1;
        nombrePlaneta = nombrePlaneta + " " + a + "-" + b;
        return nombrePlaneta;
    }
    //lee el archivo helados.txt y de ahi selecciona un nombre al azar para los planetas helados
    //recibe un objeto Random, retorna el nombre que tendra el planeta helado
    static String nombresHelados(Random rand){
        String chosen = "";
        String archivo = "Game/LogicaNombres/helados.txt";
        try {
            List<String> lineas = Files.readAllLines(Paths.get(archivo));
            int i = rand.nextInt(lineas.size());
            chosen = lineas.get(i);
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
        }
        return chosen;
    }
    //lee el archivo acuaticos.txt y de ahi selecciona un nombre al azar para los planetas acuaticos
    //recibe un objeto Random, retorna el nombre que tendra el planeta acuatico
    private static String nombresAcuaticos(Random rand){
        String chosen = "";
        String archivo = "Game/LogicaNombres/acuaticos.txt";
        try {
            List<String> lineas = Files.readAllLines(Paths.get(archivo));
            int i = rand.nextInt(lineas.size());
            chosen = lineas.get(i);
        }  catch (IOException e) {
            System.out.println("Error " + e.getMessage());
        }
        return chosen;
    }

    //lee el archivo radiactivos.txt y de ahi selecciona un nombre al azar para los planetas radiactivos
    //recibe un objeto Random, retorna el nombre que tendra el planeta radiactivo
    private static String nombresRadiactivos(Random rand){
        String chosen = "";
        String archivo = "Game/LogicaNombres/radiactivos.txt";
        try {
            List<String> lineas = Files.readAllLines(Paths.get(archivo));
            int i = rand.nextInt(lineas.size());
            chosen = lineas.get(i);
        }  catch (IOException e) {
            System.out.println("Error " + e.getMessage());
        }
        return chosen;

    }

    //lee el archivo volcanicos.txt y de ahi selecciona un nombre al azar para los planetas volcanicos
    //recibe un objeto Random, retorna el nombre que tendra el planeta volcanico
    private static String nombresVolcanicos(Random rand){
        String chosen = "";
        String archivo = "Game/LogicaNombres/volcanicos.txt";
        try {
            List<String> lineas = Files.readAllLines(Paths.get(archivo));
            int i = rand.nextInt(lineas.size());
            chosen = lineas.get(i);
        }  catch (IOException e) {
            System.out.println("Error " + e.getMessage());
        }
        return chosen;

    }

}
