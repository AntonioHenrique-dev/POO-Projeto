package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.model.AreaComum;
import br.edu.catolica.ucb.poo.service.AreaComumService;

import java.util.List;

public class AreaComumView extends CrudView<AreaComum> {

    private final AreaComumService service = new AreaComumService();

    public AreaComumView(Console console) {
        super(console, "AREAS COMUNS");
    }

    @Override
    protected List<AreaComum> listarTodos() {
        return service.listar();
    }

    @Override
    protected String linha(AreaComum area) {
        return area.getId() + " - " + area.getNome() + " | Capacidade: " + area.getCapacidade();
    }

    @Override
    protected void cadastrar() {
        String nome = console.lerTexto("Nome: ");
        int capacidade = console.lerInteiro("Capacidade: ");
        AreaComum area = service.cadastrar(nome, capacidade);
        console.sucesso("Area comum cadastrada com id " + area.getId() + ".");
    }

    @Override
    protected void atualizar() {
        listar();
        int id = console.lerInteiro("Id da area a atualizar (0 cancela): ");
        if (id == 0) {
            return;
        }
        String nome = console.lerTexto("Novo nome: ");
        int capacidade = console.lerInteiro("Nova capacidade: ");
        service.atualizar(id, nome, capacidade);
        console.sucesso("Area comum atualizada.");
    }

    @Override
    protected void excluir(Integer id) {
        service.excluir(id);
    }
}
