package model;

import java.util.ArrayList;
import java.util.List;

public class Jogador {

    private List<Carta> cartas = new ArrayList<>();
    private boolean isBanca;
    private String nome;
    private STATUS_JOGADOR status;

    public Jogador(boolean isBanca){
        this.isBanca = isBanca;
        this.nome = isBanca ? "Banca" : "Você";
    }

    public void receberCarta(Carta carta){
        this.cartas.add(carta);
        if (this.temBlackJack()) this.status = STATUS_JOGADOR.BLACKJACK;
        else if (this.estourou()) this.status = STATUS_JOGADOR.ESTOUROU;
    }

    // Mao tem 21 com apenas duas cartas
    public boolean temBlackJack(){
        return (this.cartas.size() == 2 && calcularMao() == 21);
    }

    // Mao soma mais que 21
    public boolean estourou(){
        return this.calcularMao() > 21;
    }

    public int calcularMao(){
        int soma = 0;
        for (Carta carta : this.cartas){
            // Considerando que ao atingir ou passar de 21 o jogo pare,
            // somar ÁS como 11 apenas se a soma for menor que 11.
            if (carta.isAs() && soma >= 11){
                soma += 1;
            } else {
                soma += carta.getValor();
            }
        }
        return soma;
    }

    public List<Carta> getMao() {
        return this.cartas;
    }

    public void limparMao(){
        this.cartas.clear();
    }

    public STATUS_JOGADOR getStatus(){
        return this.status;
    }

    public void setStatus(STATUS_JOGADOR status){
        this.status = status;
    }

    public boolean isBanca(){
        return this.isBanca;
    }
}
