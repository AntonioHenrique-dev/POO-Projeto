package br.edu.catolica.ucb.poo.model;

public enum Perfil {
    ADMINISTRADOR("Administrador"),
    SINDICO("Sindico"),
    PORTEIRO("Porteiro"),
    MORADOR("Morador");

    private final String rotulo;

    Perfil(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }
}
