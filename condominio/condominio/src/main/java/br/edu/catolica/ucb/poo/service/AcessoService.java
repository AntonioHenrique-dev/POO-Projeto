package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.model.Acesso;
import br.edu.catolica.ucb.poo.model.TipoAcesso;
import br.edu.catolica.ucb.poo.repository.Repositorio;

import java.time.LocalDateTime;
import java.util.List;

public class AcessoService {

    private final Repositorio<Acesso> repositorio = new Repositorio<>(Acesso.class);

    public List<Acesso> listar() {
        return repositorio.listar();
    }

    public Acesso registrar(String pessoa, TipoAcesso tipo) {
        if (pessoa == null || pessoa.isBlank()) {
            throw new IllegalArgumentException("Informe o nome da pessoa.");
        }
        if (pessoa.trim().length() > 100) {
            throw new IllegalArgumentException("O nome deve ter no maximo 100 caracteres.");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("Informe o tipo de acesso.");
        }
        return repositorio.salvar(new Acesso(pessoa.trim(), tipo, LocalDateTime.now()));
    }
}
