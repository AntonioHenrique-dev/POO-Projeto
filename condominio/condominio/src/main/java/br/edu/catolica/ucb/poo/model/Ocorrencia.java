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
@Table(name = "ocorrencias")
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;

    @Column(name = "registrado_por", nullable = false, length = 100)
    private String registradoPor;

    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusOcorrencia status;

    public Ocorrencia() {
    }

    public Ocorrencia(String descricao, String registradoPor, LocalDateTime dataRegistro, StatusOcorrencia status) {
        this.descricao = descricao;
        this.registradoPor = registradoPor;
        this.dataRegistro = dataRegistro;
        this.status = status;
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

    public String getRegistradoPor() {
        return registradoPor;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public StatusOcorrencia getStatus() {
        return status;
    }

    public void setStatus(StatusOcorrencia status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s - %s (por: %s, em %s)",
                id, status, descricao, registradoPor, dataRegistro);
    }
}
