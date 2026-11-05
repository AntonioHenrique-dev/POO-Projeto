package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.model.Ocorrencia;
import br.edu.catolica.ucb.poo.model.StatusOcorrencia;
import br.edu.catolica.ucb.poo.repository.Repositorio;

import java.time.LocalDateTime;
import java.util.List;

public class OcorrenciaService {

    private final Repositorio<Ocorrencia> repositorio = new Repositorio<>(Ocorrencia.class);

    public List<Ocorrencia> listar() {
        return repositorio.listar();
    }

    public Ocorrencia buscarPorId(Integer id) {
        Ocorrencia ocorrencia = repositorio.buscarPorId(id);
        if (ocorrencia == null) {
            throw new IllegalArgumentException("Ocorrencia com id " + id + " nao encontrada.");
        }
        return ocorrencia;
    }

    public Ocorrencia registrar(String descricao, String registradoPor) {
        validar(descricao, registradoPor);
        return repositorio.salvar(new Ocorrencia(descricao.trim(), registradoPor.trim(),
                LocalDateTime.now(), StatusOcorrencia.ABERTA));
    }

    public Ocorrencia atualizar(Integer id, String descricao, StatusOcorrencia status) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descricao da ocorrencia e obrigatoria.");
        }
        if (descricao.trim().length() > 500) {
            throw new IllegalArgumentException("A descricao deve ter no maximo 500 caracteres.");
        }
        if (status == null) {
            throw new IllegalArgumentException("O status da ocorrencia e obrigatorio.");
        }
        Ocorrencia ocorrencia = buscarPorId(id);
        ocorrencia.setDescricao(descricao.trim());
        ocorrencia.setStatus(status);
        return repositorio.atualizar(ocorrencia);
    }

    public void excluir(Integer id) {
        buscarPorId(id);
        repositorio.excluir(id);
    }

    private void validar(String descricao, String registradoPor) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descricao da ocorrencia e obrigatoria.");
        }
        if (descricao.trim().length() > 500) {
            throw new IllegalArgumentException("A descricao deve ter no maximo 500 caracteres.");
        }
        if (registradoPor == null || registradoPor.isBlank()) {
            throw new IllegalArgumentException("Informe quem esta registrando a ocorrencia.");
        }
        if (registradoPor.trim().length() > 100) {
            throw new IllegalArgumentException("O nome de quem registra deve ter no maximo 100 caracteres.");
        }
    }
}
