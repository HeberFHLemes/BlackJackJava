package controller;

import model.Baralho;
import model.Jogador;
import model.Jogo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)){
            System.out.println("""
                    ===========================
                    === JAVA BLACKJACK GAME ===
                    ===========================
                    """);
            int escolha = 0;
            do {
                System.out.println("""
                        ---------------------------
                                 ESCOLHA
                        ---------------------------
                        [1] - Novo jogo
                        [0] - Sair
                        ---------------------------
                        """);
                escolha = getEscolha(scanner);

                switch (escolha){
                    case 0:
                        break;
                    case 1:
                        Jogo.iniciarJogo();
                        break;
                    default:
                        System.out.println("> Opção inválida!");
                        break;
                }
            } while (escolha != 0);
        }
    }

    public static int getEscolha(Scanner scanner){
        int escolha;
        try {
            escolha = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            escolha = -1;
        }
        return escolha;
    }
}
