package br.edu.catolica.ucb.poo.view;

import java.util.List;

public abstract class CrudView<T> extends MenuView {

    protected CrudView(Console console, String titulo) {
        super(console, titulo, "Voltar");
    }

    protected abstract List<T> listarTodos();

    protected abstract String linha(T item);

    protected abstract void cadastrar();

    protected abstract void atualizar();

    protected abstract void excluir(Integer id);

    @Override
    protected List<Opcao> opcoes() {
        return List.of(
                new Opcao("Listar", this::listar),
                new Opcao("Cadastrar", this::cadastrar),
                new Opcao("Atualizar", this::atualizar),
                new Opcao("Excluir", this::excluirSelecionado));
    }

    protected void listar() {
        listarItens(listarTodos(), this::linha);
    }

    private void excluirSelecionado() {
        listar();
        int id = console.lerInteiro("Id para excluir (0 cancela): ");
        if (id == 0) {
            return;
        }
        excluir(id);
        console.sucesso("Registro excluido.");
    }
}
