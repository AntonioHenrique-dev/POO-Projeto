package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.model.Morador;
import br.edu.catolica.ucb.poo.model.Visitante;
import br.edu.catolica.ucb.poo.repository.Repositorio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class VisitanteService {

    private final Repositorio<Visitante> repositorio = new Repositorio<>(Visitante.class);
    private final MoradorService moradorService = new MoradorService();

    public List<Visitante> listar() {
        return repositorio.listar();
    }

    public List<Visitante> listarPorMorador(Integer moradorId) {
        return repositorio.consultar("SELECT v FROM Visitante v WHERE v.morador.id = :moradorId ORDER BY v.id",
                Map.of("moradorId", moradorId));
    }

    public Visitante buscarPorId(Integer id) {
        Visitante visitante = repositorio.buscarPorId(id);
        if (visitante == null) {
            throw new IllegalArgumentException("Visitante com id " + id + " nao encontrado.");
        }
        return visitante;
    }

    public Visitante cadastrar(String nome, String documento, Integer moradorId) {
        validar(nome, documento);
        Morador morador = moradorService.buscarPorId(moradorId);
        return repositorio.salvar(new Visitante(nome.trim(), documento.trim(), morador, LocalDateTime.now()));
    }

    public Visitante atualizar(Integer id, String nome, String documento, Integer moradorId) {
        validar(nome, documento);
        Visitante visitante = buscarPorId(id);
        visitante.setNome(nome.trim());
        visitante.setDocumento(documento.trim());
        visitante.setMorador(moradorService.buscarPorId(moradorId));
        return repositorio.atualizar(visitante);
    }

    public Visitante autorizar(Integer id, Integer moradorId) {
        Visitante visitante = buscarPorId(id);
        if (!visitante.getMorador().getId().equals(moradorId)) {
            throw new IllegalArgumentException("Este visitante nao esta cadastrado para o morador informado.");
        }
        if (visitante.isAutorizado()) {
            throw new IllegalArgumentException("Este visitante ja esta autorizado.");
        }
        visitante.setAutorizado(true);
        return repositorio.atualizar(visitante);
    }

    public void excluir(Integer id) {
        buscarPorId(id);
        repositorio.excluir(id);
    }

    private void validar(String nome, String documento) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do visitante e obrigatorio.");
        }
        if (nome.trim().length() > 100) {
            throw new IllegalArgumentException("O nome do visitante deve ter no maximo 100 caracteres.");
        }
        if (documento == null || documento.isBlank()) {
            throw new IllegalArgumentException("O documento do visitante e obrigatorio.");
        }
        if (documento.trim().length() > 20) {
            throw new IllegalArgumentException("O documento deve ter no maximo 20 caracteres.");
        }
    }
}
