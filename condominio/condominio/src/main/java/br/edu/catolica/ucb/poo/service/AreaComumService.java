package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.model.AreaComum;
import br.edu.catolica.ucb.poo.repository.Repositorio;

import java.util.List;
import java.util.Map;

public class AreaComumService {

    private final Repositorio<AreaComum> repositorio = new Repositorio<>(AreaComum.class);

    public List<AreaComum> listar() {
        return repositorio.listar();
    }

    public AreaComum buscarPorId(Integer id) {
        AreaComum area = repositorio.buscarPorId(id);
        if (area == null) {
            throw new IllegalArgumentException("Area comum com id " + id + " nao encontrada.");
        }
        return area;
    }

    public AreaComum cadastrar(String nome, Integer capacidade) {
        validar(nome, capacidade);
        return repositorio.salvar(new AreaComum(nome.trim(), capacidade));
    }

    public AreaComum atualizar(Integer id, String nome, Integer capacidade) {
        validar(nome, capacidade);
        AreaComum area = buscarPorId(id);
        area.setNome(nome.trim());
        area.setCapacidade(capacidade);
        return repositorio.atualizar(area);
    }

    public void excluir(Integer id) {
        buscarPorId(id);
        long reservas = repositorio.contar(
                "SELECT COUNT(r) FROM Reserva r WHERE r.area.id = :id", Map.of("id", id));
        if (reservas > 0) {
            throw new IllegalStateException("Nao e possivel excluir a area: ha reservas vinculadas a ela.");
        }
        repositorio.excluir(id);
    }

    private void validar(String nome, Integer capacidade) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da area comum e obrigatorio.");
        }
        if (nome.trim().length() > 100) {
            throw new IllegalArgumentException("O nome da area deve ter no maximo 100 caracteres.");
        }
        if (capacidade == null || capacidade <= 0) {
            throw new IllegalArgumentException("A capacidade deve ser maior que zero.");
        }
    }
}
