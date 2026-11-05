package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.model.Encomenda;
import br.edu.catolica.ucb.poo.model.Morador;
import br.edu.catolica.ucb.poo.repository.Repositorio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class EncomendaService {

    private final Repositorio<Encomenda> repositorio = new Repositorio<>(Encomenda.class);
    private final MoradorService moradorService = new MoradorService();

    public List<Encomenda> listar() {
        return repositorio.listar();
    }

    public List<Encomenda> listarPorDestinatario(Integer moradorId) {
        return repositorio.consultar("SELECT e FROM Encomenda e WHERE e.destinatario.id = :moradorId ORDER BY e.id",
                Map.of("moradorId", moradorId));
    }

    public Encomenda buscarPorId(Integer id) {
        Encomenda encomenda = repositorio.buscarPorId(id);
        if (encomenda == null) {
            throw new IllegalArgumentException("Encomenda com id " + id + " nao encontrada.");
        }
        return encomenda;
    }

    public Encomenda registrar(String descricao, Integer destinatarioId) {
        validar(descricao);
        Morador destinatario = moradorService.buscarPorId(destinatarioId);
        return repositorio.salvar(new Encomenda(descricao.trim(), destinatario, LocalDateTime.now()));
    }

    public Encomenda atualizar(Integer id, String descricao, Integer destinatarioId, boolean retirada) {
        validar(descricao);
        Encomenda encomenda = buscarPorId(id);
        encomenda.setDescricao(descricao.trim());
        encomenda.setDestinatario(moradorService.buscarPorId(destinatarioId));
        encomenda.setRetirada(retirada);
        return repositorio.atualizar(encomenda);
    }

    public void excluir(Integer id) {
        buscarPorId(id);
        repositorio.excluir(id);
    }

    private void validar(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descricao da encomenda e obrigatoria.");
        }
        if (descricao.trim().length() > 200) {
            throw new IllegalArgumentException("A descricao deve ter no maximo 200 caracteres.");
        }
    }
}
