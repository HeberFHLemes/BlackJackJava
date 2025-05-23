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
                        iniciarJogo(scanner);
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

    private static void iniciarJogo(Scanner scanner){

        // Início do jogo
        Jogo jogo = Jogo.iniciarJogo();

        // Jogo em si
        jogar(jogo, scanner);

    }

    private static void jogar(Jogo jogo, Scanner scanner){

        boolean continuar;
        do {
            continuar = true; // Até acabar saldo do jogador, da banca, ou jogador sair.
            if (jogo.getJogador().getFichas() < 1){
                System.out.println("Acabaram as fichas... ");
                System.out.println("Tome um tempo, pense na sua vida, e só depois volte a gastar mais aqui.");
                return;
            } else if (jogo.getBanca().getFichas() < 1){
                System.out.println("Você quebrou a banca mesmo! ");
                System.out.println("Infelizmente terá que se retirar...\nObrigado por jogar... (-_-)");
                return;
            }

            int aposta;
            do {

                System.out.println("Suas fichas: " + jogo.getJogador().getFichas() + " fichas ");
                System.out.println("Fichas da banca: " + jogo.getBanca().getFichas() + " fichas ");
                System.out.println("Digite o valor da aposta:");

                aposta = getEscolha(scanner);

            } while (aposta <= 0 || aposta > jogo.getJogador().getFichas());

            // Primeira mostra de cada mão
            jogo.informarMaos();

            // Verificar se um ou mais possuem blackjack
            if (jogo.getJogador().getStatus() != STATUS_JOGADOR.JOGANDO || jogo.getBanca().getStatus() != STATUS_JOGADOR.JOGANDO){
                if (jogo.getJogador().getStatus().equals(STATUS_JOGADOR.BLACKJACK)) System.out.println("Você saiu com Blackjack!");
                if (jogo.getBanca().getStatus().equals(STATUS_JOGADOR.BLACKJACK)) System.out.println("A Banca saiu com Blackjack!");
                jogo.finalizarJogo(aposta);
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
            jogo.finalizarJogo(aposta);

            int escolhaContinuar;
            do {

            System.out.println("""
                    \n
                    --------------------
                     CONTINUAR JOGANDO?
                    --------------------
                    [1] - CLARO!
                    [2] - Nem pensar, já perdi tudo!
                    [3] - Nem pensar, já ganhei o bastante!
                    [4] - Brigar com o croupier e ser expulso...
                    [0] - Levantar e sair.
                    """);

                escolhaContinuar = getEscolha(scanner);

                if (escolhaContinuar != 1) continuar = false;

                switch (escolhaContinuar) {
                    case 1:
                        jogo.prepararNovaRodada();
                        break;
                    case 2:
                        System.out.println("Bom, então tome um tempo, pense na sua vida, e só depois volte a gastar mais aqui.");
                        break;
                    case 3:
                        System.out.println("Ah, sério... agora que estava ficando interessante! Bom, até logo então... ");
                        break;
                    case 4:
                        System.out.println("Você derrubou tudo da mesa, xingou o croupier, deu um tapa no ficheiro e foi expulso pelos seguranças!\nApesar disso, ainda aceitaremos teu dinheiro um outro dia!\nAté breve!");
                        break;
                    case 0:
                        System.out.println("Você levantou e saiu sem dizer uma palavra. Seja chateado ou feliz com o resultado, parece que toda essa jogatina não satisfez o vazio que parece haver em ti.\nAté breve.");
                        break;
                    default:
                        System.out.println("Opção inválida, digite novamente.");
                }
            } while (escolhaContinuar < 0 || escolhaContinuar > 4);

        } while (continuar); // Outras rodadas
    }
}
