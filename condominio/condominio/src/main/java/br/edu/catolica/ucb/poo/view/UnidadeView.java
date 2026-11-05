package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.model.Unidade;
import br.edu.catolica.ucb.poo.service.UnidadeService;

import java.util.List;

public class UnidadeView extends CrudView<Unidade> {

    private final UnidadeService service = new UnidadeService();

    public UnidadeView(Console console) {
        super(console, "UNIDADES");
    }

    @Override
    protected List<Unidade> listarTodos() {
        return service.listar();
    }

    @Override
    protected String linha(Unidade unidade) {
        String bloco = unidade.getBloco() == null ? "-" : unidade.getBloco();
        return unidade.getId() + " - Numero: " + unidade.getNumero() + " | Bloco: " + bloco;
    }

    @Override
    protected void cadastrar() {
        String numero = console.lerTexto("Numero: ");
        String bloco = console.lerTexto("Bloco (opcional): ");
        Unidade unidade = service.cadastrar(numero, bloco);
        console.sucesso("Unidade cadastrada com id " + unidade.getId() + ".");
    }

    @Override
    protected void atualizar() {
        listar();
        int id = console.lerInteiro("Id da unidade a atualizar (0 cancela): ");
        if (id == 0) {
            return;
        }
        String numero = console.lerTexto("Novo numero: ");
        String bloco = console.lerTexto("Novo bloco (opcional): ");
        service.atualizar(id, numero, bloco);
        console.sucesso("Unidade atualizada.");
    }

    @Override
    protected void excluir(Integer id) {
        service.excluir(id);
    }
}
