package br.edu.catolica.ucb.poo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "acessos")
public class Acesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pessoa", nullable = false, length = 100)
    private String pessoa;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 10)
    private TipoAcesso tipo;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    public Acesso() {
    }

    public Acesso(String pessoa, TipoAcesso tipo, LocalDateTime dataHora) {
        this.pessoa = pessoa;
        this.tipo = tipo;
        this.dataHora = dataHora;
    }

    public Integer getId() {
        return id;
    }

    public String getPessoa() {
        return pessoa;
    }

    public TipoAcesso getTipo() {
        return tipo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s - %s em %s", id, tipo, pessoa, dataHora);
    }
}
