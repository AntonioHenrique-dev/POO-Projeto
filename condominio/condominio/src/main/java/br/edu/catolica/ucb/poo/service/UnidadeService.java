package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.model.Unidade;
import br.edu.catolica.ucb.poo.repository.Repositorio;

import java.util.List;
import java.util.Map;

public class UnidadeService {

    private final Repositorio<Unidade> repositorio = new Repositorio<>(Unidade.class);

    public List<Unidade> listar() {
        return repositorio.listar();
    }

    public Unidade buscarPorId(Integer id) {
        Unidade unidade = repositorio.buscarPorId(id);
        if (unidade == null) {
            throw new IllegalArgumentException("Unidade com id " + id + " nao encontrada.");
        }
        return unidade;
    }

    public Unidade cadastrar(String numero, String bloco) {
        validar(numero, bloco);
        return repositorio.salvar(new Unidade(numero.trim(), normalizar(bloco)));
    }

    public Unidade atualizar(Integer id, String numero, String bloco) {
        validar(numero, bloco);
        Unidade unidade = buscarPorId(id);
        unidade.setNumero(numero.trim());
        unidade.setBloco(normalizar(bloco));
        return repositorio.atualizar(unidade);
    }

    public void excluir(Integer id) {
        buscarPorId(id);
        long moradores = repositorio.contar(
                "SELECT COUNT(m) FROM Morador m WHERE m.unidade.id = :id", Map.of("id", id));
        if (moradores > 0) {
            throw new IllegalStateException("Nao e possivel excluir a unidade: ha moradores vinculados a ela.");
        }
        repositorio.excluir(id);
    }

    private void validar(String numero, String bloco) {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("O numero da unidade e obrigatorio.");
        }
        if (numero.trim().length() > 20) {
            throw new IllegalArgumentException("O numero da unidade deve ter no maximo 20 caracteres.");
        }
        if (bloco != null && bloco.trim().length() > 20) {
            throw new IllegalArgumentException("O bloco deve ter no maximo 20 caracteres.");
        }
    }

    private String normalizar(String bloco) {
        return bloco == null || bloco.isBlank() ? null : bloco.trim();
    }
}
