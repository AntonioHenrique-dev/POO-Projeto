package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.model.Morador;
import br.edu.catolica.ucb.poo.model.Veiculo;
import br.edu.catolica.ucb.poo.repository.Repositorio;

import java.util.List;
import java.util.Objects;

public class VeiculoService {

    private final Repositorio<Veiculo> repositorio = new Repositorio<>(Veiculo.class);
    private final MoradorService moradorService = new MoradorService();

    public List<Veiculo> listar() {
        return repositorio.listar();
    }

    public Veiculo buscarPorId(Integer id) {
        Veiculo veiculo = repositorio.buscarPorId(id);
        if (veiculo == null) {
            throw new IllegalArgumentException("Veiculo com id " + id + " nao encontrado.");
        }
        return veiculo;
    }

    public Veiculo cadastrar(String placa, String modelo, String cor, String vaga, Integer moradorId) {
        validar(placa, modelo, cor, vaga, null);
        Morador morador = moradorService.buscarPorId(moradorId);
        return repositorio.salvar(new Veiculo(placa.trim().toUpperCase(), modelo.trim(), normalizar(cor),
                vaga.trim().toUpperCase(), morador));
    }

    public Veiculo atualizar(Integer id, String placa, String modelo, String cor, String vaga, Integer moradorId) {
        validar(placa, modelo, cor, vaga, id);
        Veiculo veiculo = buscarPorId(id);
        veiculo.setPlaca(placa.trim().toUpperCase());
        veiculo.setModelo(modelo.trim());
        veiculo.setCor(normalizar(cor));
        veiculo.setVaga(vaga.trim().toUpperCase());
        veiculo.setMorador(moradorService.buscarPorId(moradorId));
        return repositorio.atualizar(veiculo);
    }

    public void excluir(Integer id) {
        buscarPorId(id);
        repositorio.excluir(id);
    }

    private void validar(String placa, String modelo, String cor, String vaga, Integer idIgnorado) {
        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException("A placa do veiculo e obrigatoria.");
        }
        if (placa.trim().length() > 10) {
            throw new IllegalArgumentException("A placa deve ter no maximo 10 caracteres.");
        }
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException("O modelo do veiculo e obrigatorio.");
        }
        if (modelo.trim().length() > 50) {
            throw new IllegalArgumentException("O modelo deve ter no maximo 50 caracteres.");
        }
        if (cor != null && cor.trim().length() > 30) {
            throw new IllegalArgumentException("A cor deve ter no maximo 30 caracteres.");
        }
        if (vaga == null || vaga.isBlank()) {
            throw new IllegalArgumentException("O numero da vaga e obrigatorio.");
        }
        if (vaga.trim().length() > 10) {
            throw new IllegalArgumentException("O numero da vaga deve ter no maximo 10 caracteres.");
        }
        String vagaNormalizada = vaga.trim();
        boolean ocupada = repositorio.listar().stream()
                .anyMatch(v -> v.getVaga().equalsIgnoreCase(vagaNormalizada)
                        && !Objects.equals(v.getId(), idIgnorado));
        if (ocupada) {
            throw new IllegalArgumentException("A vaga " + vagaNormalizada + " ja esta ocupada por outro veiculo.");
        }
    }

    private String normalizar(String cor) {
        return cor == null || cor.isBlank() ? null : cor.trim();
    }
}
