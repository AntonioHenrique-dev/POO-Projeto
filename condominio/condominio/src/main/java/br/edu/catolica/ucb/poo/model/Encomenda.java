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
@Table(name = "encomendas")
public class Encomenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "descricao", nullable = false, length = 200)
    private String descricao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Morador destinatario;

    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro;

    @Column(name = "retirada", nullable = false)
    private boolean retirada;

    public Encomenda() {
    }

    public Encomenda(String descricao, Morador destinatario, LocalDateTime dataRegistro) {
        this.descricao = descricao;
        this.destinatario = destinatario;
        this.dataRegistro = dataRegistro;
        this.retirada = false;
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Morador getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Morador destinatario) {
        this.destinatario = destinatario;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public boolean isRetirada() {
        return retirada;
    }

    public void setRetirada(boolean retirada) {
        this.retirada = retirada;
    }

    @Override
    public String toString() {
        String situacao = retirada ? "retirada" : "pendente";
        return String.format("[%d] %s - para: %s (%s, recebida em %s)",
                id, descricao, destinatario.getNome(), situacao, dataRegistro);
    }
}
