package model;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Baralho {

    // Uso de arraylist ao invés de stack ou deque para uso de Collections.shuffle.
    private final List<Carta> cartas = new ArrayList<>();

    public Baralho(){

        // Para cada conjunto de naipe e valor, cria-se uma carta.
        for (NAIPE naipe : NAIPE.values()){
            for (VALOR valor : VALOR.values()){
                this.cartas.add(new Carta(naipe, valor));
            }
        }

        // Embaralhar
        Collections.shuffle(cartas);
    }

    // Funcionalidade de comprar uma carta, removendo-a do fim da lista, como se fosse o topo da pilha.
    public Carta comprarCarta(){
        return cartas.isEmpty() ? null : cartas.remove(cartas.size() - 1);
    }

    /*
    public boolean estaVazio(){
        return cartas.isEmpty();
    }
     */
}
