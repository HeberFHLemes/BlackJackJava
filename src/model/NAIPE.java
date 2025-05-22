package model;

public enum NAIPE {
    BASTOS("♣"),
    COPAS("♥"),
    ESPADAS("♠"),
    OUROS("♦");

    private final String naipe;

    private NAIPE(String naipe){
        this.naipe = naipe;
    }

    public String getNaipe(){
        return this.naipe;
    }
}