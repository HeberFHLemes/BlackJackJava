package model;

public class Carta {
    private final NAIPE naipe;
    private final VALOR valor;

    public Carta(NAIPE naipe, VALOR valor){
        this.naipe = naipe;
        this.valor = valor;
    }

    public int getValor(){
        return valor.getValor();
    }

    /*
    public String getNaipe(){
        return naipe.getNaipe();
    }
     */

    public boolean isAs(){
        return valor.isAs();
    }

    @Override
    public String toString(){
        return "|" + this.valor.getValor() + this.naipe.getNaipe() + "|";
    }
}
