package controller;

import model.Jogo;
import model.STATUS_JOGADOR;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)){
            System.out.println("""
                    ===========================
                    === JAVA BLACKJACK GAME ===
                    ===========================
                    """);
            int escolha;
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
                        jogar(scanner);
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

    private static void jogar(Scanner scanner){

        // Início do jogo
        Jogo jogo = Jogo.iniciarJogo();

        // Verificar se um ou mais possuem blackjack
        if (jogo.getJogador().getStatus() != STATUS_JOGADOR.JOGANDO || jogo.getBanca().getStatus() != STATUS_JOGADOR.JOGANDO){
            if (jogo.getJogador().getStatus().equals(STATUS_JOGADOR.BLACKJACK)) System.out.println("Você saiu com Blackjack!");
            if (jogo.getBanca().getStatus().equals(STATUS_JOGADOR.BLACKJACK)) System.out.println("A Banca saiu com Blackjack!");
            jogo.finalizarJogo();
            return;
        }

        // Vez do jogador
        while (jogo.getJogador().getStatus() == STATUS_JOGADOR.JOGANDO){
            System.out.println("=====================");
            System.out.println("        ESCOLHA");
            System.out.println("=====================");
            System.out.println("""
                [1] - COMPRAR
                [2] - PARAR
                [3] - CONFERIR
                """);
            int escolha = getEscolha(scanner);
            switch (escolha){
                case 1:
                    jogo.jogadorCompra();
                    break;
                case 2:
                    jogo.jogadorPara();
                    break;
                case 3:
                    jogo.informarMaos();
                    break;
                default:
                    System.out.println("Opção inválida...");
            }
        }

        // Vez da banca
        jogo.bancaJoga();

        // Fim
        jogo.finalizarJogo();
    }
}
