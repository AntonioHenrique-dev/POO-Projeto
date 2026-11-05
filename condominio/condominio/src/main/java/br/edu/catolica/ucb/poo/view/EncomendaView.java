package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.model.Encomenda;
import br.edu.catolica.ucb.poo.service.EncomendaService;
import br.edu.catolica.ucb.poo.service.MoradorService;

import java.util.List;

public class EncomendaView extends CrudView<Encomenda> {

    private final EncomendaService service = new EncomendaService();
    private final MoradorService moradorService = new MoradorService();

    public EncomendaView(Console console) {
        super(console, "ENCOMENDAS");
    }

    @Override
    protected List<Encomenda> listarTodos() {
        return service.listar();
    }

    @Override
    protected String linha(Encomenda encomenda) {
        return encomenda.getId() + " - " + encomenda.getDescricao()
                + " | Destinatario: " + encomenda.getDestinatario().getNome()
                + " | Retirada: " + (encomenda.isRetirada() ? "Sim" : "Nao")
                + " | Recebida em: " + encomenda.getDataRegistro().format(Console.FORMATO_DATA_HORA);
    }

    @Override
    protected void cadastrar() {
        String descricao = console.lerTexto("Descricao: ");
        listarMoradores();
        int destinatarioId = console.lerInteiro("Id do destinatario: ");
        Encomenda encomenda = service.registrar(descricao, destinatarioId);
        console.sucesso("Encomenda registrada com id " + encomenda.getId() + ".");
    }

    @Override
    protected void atualizar() {
        listar();
        int id = console.lerInteiro("Id da encomenda a atualizar (0 cancela): ");
        if (id == 0) {
            return;
        }
        String descricao = console.lerTexto("Nova descricao: ");
        listarMoradores();
        int destinatarioId = console.lerInteiro("Id do destinatario: ");
        boolean retirada = console.lerTexto("Ja foi retirada? (s/n): ").equalsIgnoreCase("s");
        service.atualizar(id, descricao, destinatarioId, retirada);
        console.sucesso("Encomenda atualizada.");
    }

    @Override
    protected void excluir(Integer id) {
        service.excluir(id);
    }

    private void listarMoradores() {
        listarItens(moradorService.listar(), m -> m.getId() + " - " + m.getNome());
    }
}
