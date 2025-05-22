package model;

import java.util.ArrayList;
import java.util.List;

public class Jogador {

    private final List<Carta> cartas;
    private STATUS_JOGADOR status;

    public Jogador(){
        this.cartas = new ArrayList<>();
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
        int ases = 0;
        for (Carta carta : this.cartas){
            // Contabiliza o número de Ás na mão para depois definir o melhor valor deles no contexto (1 ou 11)
            if (carta.isAs()){
                ases += 1;
            }
            soma += carta.getValor();
        }
        // Enquanto a soma for maior que 21 e houver ases contabilizados, diminui-se a soma por 10 até ficar abaixo de 21.
        while (soma > 21 && ases > 0){
            soma -= 10;
            ases -= 1;
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

    /*
    public boolean isBanca(){
        return this.isBanca;
    }
     */
}
