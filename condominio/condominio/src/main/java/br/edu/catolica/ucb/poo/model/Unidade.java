package br.edu.catolica.ucb.poo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "unidades")
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero", nullable = false, length = 20)
    private String numero;

    @Column(name = "bloco", length = 20)
    private String bloco;

    public Unidade() {
    }

    public Unidade(String numero, String bloco) {
        this.numero = numero;
        this.bloco = bloco;
    }

    public Integer getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBloco() {
        return bloco;
    }

    public void setBloco(String bloco) {
        this.bloco = bloco;
    }

    @Override
    public String toString() {
        String local = bloco == null || bloco.isBlank() ? "" : "Bloco " + bloco + " - ";
        return String.format("[%d] %sUnidade %s", id, local, numero);
    }
}
