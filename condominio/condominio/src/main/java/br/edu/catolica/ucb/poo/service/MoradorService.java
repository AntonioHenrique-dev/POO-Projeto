package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.model.Morador;
import br.edu.catolica.ucb.poo.model.Unidade;
import br.edu.catolica.ucb.poo.repository.Repositorio;

import java.util.List;
import java.util.Map;

public class MoradorService {

    private final Repositorio<Morador> repositorio = new Repositorio<>(Morador.class);
    private final UnidadeService unidadeService = new UnidadeService();

    public List<Morador> listar() {
        return repositorio.listar();
    }

    public Morador buscarPorId(Integer id) {
        Morador morador = repositorio.buscarPorId(id);
        if (morador == null) {
            throw new IllegalArgumentException("Morador com id " + id + " nao encontrado.");
        }
        return morador;
    }

    public Morador cadastrar(String nome, String telefone, Integer unidadeId) {
        validar(nome, telefone);
        Unidade unidade = unidadeService.buscarPorId(unidadeId);
        return repositorio.salvar(new Morador(nome.trim(), normalizar(telefone), unidade));
    }

    public Morador atualizar(Integer id, String nome, String telefone, Integer unidadeId) {
        validar(nome, telefone);
        Morador morador = buscarPorId(id);
        morador.setNome(nome.trim());
        morador.setTelefone(normalizar(telefone));
        morador.setUnidade(unidadeService.buscarPorId(unidadeId));
        return repositorio.atualizar(morador);
    }

    public void excluir(Integer id) {
        buscarPorId(id);
        long vinculos = repositorio.contar("SELECT COUNT(v) FROM Veiculo v WHERE v.morador.id = :id", Map.of("id", id))
                + repositorio.contar("SELECT COUNT(r) FROM Reserva r WHERE r.morador.id = :id", Map.of("id", id))
                + repositorio.contar("SELECT COUNT(e) FROM Encomenda e WHERE e.destinatario.id = :id", Map.of("id", id))
                + repositorio.contar("SELECT COUNT(v) FROM Visitante v WHERE v.morador.id = :id", Map.of("id", id));
        if (vinculos > 0) {
            throw new IllegalStateException("Nao e possivel excluir o morador: ha veiculos, reservas, encomendas ou visitantes vinculados a ele.");
        }
        repositorio.excluir(id);
    }

    private void validar(String nome, String telefone) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do morador e obrigatorio.");
        }
        if (nome.trim().length() > 100) {
            throw new IllegalArgumentException("O nome do morador deve ter no maximo 100 caracteres.");
        }
        if (telefone != null && telefone.trim().length() > 20) {
            throw new IllegalArgumentException("O telefone deve ter no maximo 20 caracteres.");
        }
    }

    private String normalizar(String telefone) {
        return telefone == null || telefone.isBlank() ? null : telefone.trim();
    }
}
