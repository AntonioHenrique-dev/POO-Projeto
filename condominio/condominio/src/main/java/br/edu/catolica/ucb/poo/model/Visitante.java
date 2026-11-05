package br.edu.catolica.ucb.poo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "visitantes")
public class Visitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "documento", nullable = false, length = 20)
    private String documento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "morador_id", nullable = false)
    private Morador morador;

    @Column(name = "autorizado", nullable = false)
    private boolean autorizado;

    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro;

    public Visitante() {
    }

    public Visitante(String nome, String documento, Morador morador, LocalDateTime dataRegistro) {
        this.nome = nome;
        this.documento = documento;
        this.morador = morador;
        this.autorizado = false;
        this.dataRegistro = dataRegistro;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Morador getMorador() {
        return morador;
    }

    public void setMorador(Morador morador) {
        this.morador = morador;
    }

    public boolean isAutorizado() {
        return autorizado;
    }

    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    @Override
    public String toString() {
        String situacao = autorizado ? "autorizado" : "nao autorizado";
        return String.format("[%d] %s - doc: %s - morador: %s (%s)",
                id, nome, documento, morador.getNome(), situacao);
    }
}
