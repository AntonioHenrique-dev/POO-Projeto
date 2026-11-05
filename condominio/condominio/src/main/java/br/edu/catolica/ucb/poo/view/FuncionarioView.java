package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.model.Funcionario;
import br.edu.catolica.ucb.poo.service.FuncionarioService;

import java.util.List;

public class FuncionarioView extends CrudView<Funcionario> {

    private final FuncionarioService service = new FuncionarioService();

    public FuncionarioView(Console console) {
        super(console, "FUNCIONARIOS");
    }

    @Override
    protected List<Funcionario> listarTodos() {
        return service.listar();
    }

    @Override
    protected String linha(Funcionario funcionario) {
        return funcionario.getId() + " - " + funcionario.getNome() + " | Cargo: " + funcionario.getCargo();
    }

    @Override
    protected void cadastrar() {
        String nome = console.lerTexto("Nome: ");
        String cargo = console.lerTexto("Cargo: ");
        Funcionario funcionario = service.cadastrar(nome, cargo);
        console.sucesso("Funcionario cadastrado com id " + funcionario.getId() + ".");
    }

    @Override
    protected void atualizar() {
        listar();
        int id = console.lerInteiro("Id do funcionario a atualizar (0 cancela): ");
        if (id == 0) {
            return;
        }
        String nome = console.lerTexto("Novo nome: ");
        String cargo = console.lerTexto("Novo cargo: ");
        service.atualizar(id, nome, cargo);
        console.sucesso("Funcionario atualizado.");
    }

    @Override
    protected void excluir(Integer id) {
        service.excluir(id);
    }
}
