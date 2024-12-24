package com.devsenior.cdiaz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman {

    public static void stargame() {
        // lista de palabras par adivinar

        var words = List.of("programacion", "java", "computadora", "teclado", "pantalla", "internet", "desarrollo",
                "software", "hardware", "algoritmo", "funcion", "clase", "Objeto",
                "herencia", "polimorfismo", "interzfaz", "compilador", "debuggin", "sintaxis");

        var scanner = new Scanner(System.in);
        var ramdon = new Random();

        var playAgain = false;
        do {
            var word = words.get(ramdon.nextInt(words.size())).toUpperCase();

            // Empieza el juego
            playGame(word, scanner);

            System.out.println("¿Desea jugar de nuevo? (S/n)");
            var option = scanner.nextLine();
            playAgain = !option.equalsIgnoreCase("N");

        } while (playAgain);
        System.out.println("Hasta luego!");
        scanner.close();
    }

    private static void playGame(String word, Scanner scanner) {

        // Lista de letras probadas
        var guessed = new ArrayList<Character>();
        // Lista de las letras de la palabra
        var letters = new ArrayList<Character>(word.length());
        for (int i = 0; i < word.length(); i++) {
            letters.add('_');
        }

        var errors = 0;
        var endGame = false;
        while (!endGame) {
            // Mostrar la pantalla
            showScreen(letters, guessed, errors);

            // Pedir la letra al usuario
            System.out.print("ingrese una nueva letra: ");
            var letter = scanner.nextLine().toUpperCase().charAt(0);
            
            if (!Character.isLetter(letter)) {
                System.out.println("Esta letra no es valida");
                scanner.nextLine();
                continue;
                
            }
            if (guessed.contains(letter)) {
                System.out.println("Esta letra ya ha sido ingresada anteriormente");
                scanner.nextLine();
                continue;
            }
            // Verificar la letra en la palabra
            if (word.indexOf(letter) != -1) {
                // si existe, agregamos la letra a la cadena adivinada
                guessed.add(letter);

                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == letter) {
                        letters.set(i, letter);
                        
                    }
                }
                // Sino,
            } else {
                System.out.println("Lo siento, esta letra no esta en la palabra");
                guessed.add(letter);
                 // incremento los errores
                errors++;
            }

            // verificar si ya perdio
            if (errors == 6) {
                // termina el juego
                showScreen(letters, guessed, errors);
                System.out.println("Perdiste!");
                endGame = true;
            }

            // verificar si ya gano, no hay mas giones bajos _ en las letras
            if (!letters.contains('_')) {
                 // termina el juego
                 showScreen(letters, guessed, errors);
                 System.out.println("Ganaste!");
            endGame = true;
            }
        }
    }

    private static void showScreen(List<Character> letters, List<Character> guessed, int errors) {
        System.out.print("Palabra a adivinar: ");
        for (var letter : letters) {
            System.out.printf("%c ", letter);
        }
        System.out.println();

        System.out.print("Letras intentadas: ");
        for (var letter : guessed) {
            System.out.printf("%c ", letter);
        }
        System.out.println();

        // la horca segun el numero de errores
        var draw = switch(errors){
            // tODO: DIBUJO DE CADA ERROR
            case 6 -> """
                +------+
                |      |
                |      O
                |     \\|/
                |      | 
                |     / \\
                |
             +--+--+
             |     |
             """;
            
            case 5 -> """
                +------+
                |      |
                |      O
                |     \\|/
                |      | 
                |     /
                |
             +--+--+
             |     |
             """;
            case 4 -> """
                +------+
                |      |
                |      O
                |     \\|/
                |      | 
                |
                |
             +--+--+
             |     |
             """;
            case 3 -> """
                +------+
                |      |
                |      O
                |     \\|
                |      | 
                |
                |
             +--+--+
             |     |
             """;
            case 2 -> """
                +------+
                |      |
                |      O
                |      |
                |      | 
                |
                |
             +--+--+
             |     |
             """;
            case 1 -> """
                +------+
                |      |
                |      O
                |
                |
                |
                |
             +--+--+
             |     |
             """;
            default -> """
                    +------+
                    |      |
                    |
                    |
                    |
                    |
                    |
                 +--+--+
                 |     |
                 """;
        };
        System.out.println(draw);
    }

}
