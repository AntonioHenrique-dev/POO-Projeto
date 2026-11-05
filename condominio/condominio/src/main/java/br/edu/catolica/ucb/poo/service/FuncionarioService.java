package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.model.Funcionario;
import br.edu.catolica.ucb.poo.repository.Repositorio;

import java.util.List;

public class FuncionarioService {

    private final Repositorio<Funcionario> repositorio = new Repositorio<>(Funcionario.class);

    public List<Funcionario> listar() {
        return repositorio.listar();
    }

    public Funcionario buscarPorId(Integer id) {
        Funcionario funcionario = repositorio.buscarPorId(id);
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionario com id " + id + " nao encontrado.");
        }
        return funcionario;
    }

    public Funcionario cadastrar(String nome, String cargo) {
        validar(nome, cargo);
        return repositorio.salvar(new Funcionario(nome.trim(), cargo.trim()));
    }

    public Funcionario atualizar(Integer id, String nome, String cargo) {
        validar(nome, cargo);
        Funcionario funcionario = buscarPorId(id);
        funcionario.setNome(nome.trim());
        funcionario.setCargo(cargo.trim());
        return repositorio.atualizar(funcionario);
    }

    public void excluir(Integer id) {
        buscarPorId(id);
        repositorio.excluir(id);
    }

    private void validar(String nome, String cargo) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do funcionario e obrigatorio.");
        }
        if (nome.trim().length() > 100) {
            throw new IllegalArgumentException("O nome do funcionario deve ter no maximo 100 caracteres.");
        }
        if (cargo == null || cargo.isBlank()) {
            throw new IllegalArgumentException("O cargo do funcionario e obrigatorio.");
        }
        if (cargo.trim().length() > 50) {
            throw new IllegalArgumentException("O cargo deve ter no maximo 50 caracteres.");
        }
    }
}
