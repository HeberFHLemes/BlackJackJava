package model;

public class Jogo {

    private STATUS_JOGO status;
    private final Baralho baralho;
    private final Jogador jogador;
    private final Jogador banca;

    public Jogo(){
        this.baralho = new Baralho();
        this.jogador = new Jogador();
        this.banca = new Jogador();
        this.status = STATUS_JOGO.EM_ANDAMENTO;

        this.jogador.setStatus(STATUS_JOGADOR.JOGANDO);
        this.banca.setStatus(STATUS_JOGADOR.JOGANDO);
    }

    public void setStatus() {

        if (this.jogador.getMao().isEmpty()) {
            this.status = STATUS_JOGO.FINALIZADO;
            return;
        }

        int somaJogador = this.jogador.calcularMao();
        int somaBanca = this.banca.calcularMao();

        if (this.jogador.getStatus().equals(STATUS_JOGADOR.ESTOUROU)) {
            this.status = somaBanca > 21 ? STATUS_JOGO.EMPATE : STATUS_JOGO.JOGADOR_ESTOUROU;
        } else if (somaJogador == somaBanca){
            this.status = STATUS_JOGO.EMPATE;
        } else if (this.banca.getStatus().equals(STATUS_JOGADOR.ESTOUROU)){
            this.status = STATUS_JOGO.BANCA_ESTOUROU;
        } else if (somaJogador > somaBanca) {
            this.status = STATUS_JOGO.JOGADOR_VENCEU;
        } else {
            this.status = STATUS_JOGO.BANCA_VENCEU;
        }
    }

    public static Jogo iniciarJogo(){
        System.out.println("=== INICIANDO JOGO ===");
        Jogo jogo = new Jogo();
        jogo.distribuirCartasIniciais();
        jogo.informarMaos();
        return jogo;
    }

    public void finalizarJogo(){
        System.out.println();
        this.informarMaos();

        // Mudar status apenas quando ambos os participantes pararem de comprar ou um deles estourarem/alcançarem 21
        // (Quando finalizarJogo() for chamado)
        this.setStatus();
        System.out.println();

        // Resultado final
        this.informarResultado();

        this.jogador.limparMao();
        this.banca.limparMao();
    }

    public void informarResultado(){
        switch (this.status) {
            case EM_ANDAMENTO -> {
                System.out.println("Jogo ainda em andamento...");
                return;
            }
            case EMPATE -> {
                System.out.print("Jogo terminou empatado");
                if (this.jogador.getStatus().equals(STATUS_JOGADOR.BLACKJACK)) System.out.print(" com os dois somando 21");
                else if (this.jogador.getStatus().equals(STATUS_JOGADOR.ESTOUROU)) System.out.print(" com os dois estourando");
                System.out.println("! ");
            }
            case JOGADOR_ESTOUROU -> System.out.println("Sua mão passou de 21, estourou! Banca venceu.");
            case BANCA_ESTOUROU -> System.out.println("Banca passou de 21, estourou! Você venceu.");
            case JOGADOR_VENCEU -> {
                System.out.print("Parabéns, você venceu");
                if (this.jogador.getStatus().equals(STATUS_JOGADOR.BLACKJACK)) System.out.print(" com BlackJack");
                System.out.println("! ");
            }
            case BANCA_VENCEU -> {
                System.out.print("A Banca venceu");
                if (this.banca.getStatus().equals(STATUS_JOGADOR.BLACKJACK)) System.out.print(" com BlackJack");
                System.out.println("! ");
            }
        }
        this.status = STATUS_JOGO.FINALIZADO;
    }

    // Uma carta pra cada de cada vez até ficarem ambos com 2 -> possível comprar mais depois
    public void distribuirCartasIniciais(){
        if (this.jogador.getMao().isEmpty()){
            for (int i = 0; i < 2; i++){
                this.jogador.receberCarta(this.baralho.comprarCarta());
                this.banca.receberCarta(this.baralho.comprarCarta());
            }
        }
    }

    public void informarMaos(){
        if (!this.jogador.getMao().isEmpty()){
            System.out.print("Você\n>");
            for (Carta carta : this.jogador.getMao()) {
                System.out.print(carta + " ");
            }
            System.out.print("\nBanca\n>");
            for (Carta carta : this.banca.getMao()){
                System.out.print(carta + " ");
            }
        }
        System.out.println();
    }

    // Banca só compra até 17
    public void bancaCompra(){
        this.banca.receberCarta(this.baralho.comprarCarta());
    }

    public void bancaJoga(){
        while (this.banca.getStatus()  == STATUS_JOGADOR.JOGANDO){
            int maoBanca = this.banca.calcularMao();
            if (maoBanca <= 17 && maoBanca < this.jogador.calcularMao()){
                bancaCompra();
                System.out.print("Banca comprou uma carta:\n>");
                banca.getMao().forEach(carta -> System.out.print(carta + " "));
                System.out.println();
            } else {
                this.banca.setStatus(STATUS_JOGADOR.PAROU);
            }
        }
    }

    public void jogadorCompra(){
        if (this.jogador.calcularMao() < 21){
            this.jogador.receberCarta(this.baralho.comprarCarta());
        } else {
            System.out.println("Jogador já soma 21 ou mais, impossível comprar. ");
        }
    }

    public void jogadorPara(){
        if (this.jogador.getStatus().equals(STATUS_JOGADOR.JOGANDO)){
            this.jogador.setStatus(STATUS_JOGADOR.PAROU);
        }
    }

    public Jogador getJogador() {
        return jogador;
    }

    public Jogador getBanca() {
        return banca;
    }
}
