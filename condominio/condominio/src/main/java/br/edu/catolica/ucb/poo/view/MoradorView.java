package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.model.Morador;
import br.edu.catolica.ucb.poo.service.MoradorService;
import br.edu.catolica.ucb.poo.service.UnidadeService;

import java.util.List;

public class MoradorView extends CrudView<Morador> {

    private final MoradorService service = new MoradorService();
    private final UnidadeService unidadeService = new UnidadeService();

    public MoradorView(Console console) {
        super(console, "MORADORES");
    }

    @Override
    protected List<Morador> listarTodos() {
        return service.listar();
    }

    @Override
    protected String linha(Morador morador) {
        String telefone = morador.getTelefone() == null ? "-" : morador.getTelefone();
        String unidade = morador.getUnidade() == null ? "-" : morador.getUnidade().getNumero();
        return morador.getId() + " - " + morador.getNome() + " | Tel: " + telefone + " | Unidade: " + unidade;
    }

    @Override
    protected void cadastrar() {
        String nome = console.lerTexto("Nome: ");
        String telefone = console.lerTexto("Telefone (opcional): ");
        listarUnidades();
        int unidadeId = console.lerInteiro("Id da unidade: ");
        Morador morador = service.cadastrar(nome, telefone, unidadeId);
        console.sucesso("Morador cadastrado com id " + morador.getId() + ".");
    }

    @Override
    protected void atualizar() {
        listar();
        int id = console.lerInteiro("Id do morador a atualizar (0 cancela): ");
        if (id == 0) {
            return;
        }
        String nome = console.lerTexto("Novo nome: ");
        String telefone = console.lerTexto("Novo telefone (opcional): ");
        listarUnidades();
        int unidadeId = console.lerInteiro("Id da unidade: ");
        service.atualizar(id, nome, telefone, unidadeId);
        console.sucesso("Morador atualizado.");
    }

    @Override
    protected void excluir(Integer id) {
        service.excluir(id);
    }

    private void listarUnidades() {
        listarItens(unidadeService.listar(),
                u -> u.getId() + " - Unidade " + u.getNumero());
    }
}
