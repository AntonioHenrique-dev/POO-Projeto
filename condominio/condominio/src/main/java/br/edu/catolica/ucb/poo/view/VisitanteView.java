package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.model.Visitante;
import br.edu.catolica.ucb.poo.service.MoradorService;
import br.edu.catolica.ucb.poo.service.VisitanteService;

import java.util.List;

public class VisitanteView extends CrudView<Visitante> {

    private final VisitanteService service = new VisitanteService();
    private final MoradorService moradorService = new MoradorService();

    public VisitanteView(Console console) {
        super(console, "VISITANTES");
    }

    @Override
    protected List<Visitante> listarTodos() {
        return service.listar();
    }

    @Override
    protected String linha(Visitante visitante) {
        return visitante.getId() + " - " + visitante.getNome()
                + " | Documento: " + visitante.getDocumento()
                + " | Morador: " + visitante.getMorador().getNome()
                + " | Autorizado: " + (visitante.isAutorizado() ? "Sim" : "Nao")
                + " | Registro: " + visitante.getDataRegistro().format(Console.FORMATO_DATA_HORA);
    }

    @Override
    protected void cadastrar() {
        String nome = console.lerTexto("Nome: ");
        String documento = console.lerTexto("Documento: ");
        listarMoradores();
        int moradorId = console.lerInteiro("Id do morador: ");
        Visitante visitante = service.cadastrar(nome, documento, moradorId);
        console.sucesso("Visitante registrado com id " + visitante.getId()
                + ". Aguardando autorizacao do morador.");
    }

    @Override
    protected void atualizar() {
        listar();
        int id = console.lerInteiro("Id do visitante a atualizar (0 cancela): ");
        if (id == 0) {
            return;
        }
        String nome = console.lerTexto("Novo nome: ");
        String documento = console.lerTexto("Novo documento: ");
        listarMoradores();
        int moradorId = console.lerInteiro("Id do morador: ");
        service.atualizar(id, nome, documento, moradorId);
        console.sucesso("Visitante atualizado.");
    }

    @Override
    protected void excluir(Integer id) {
        service.excluir(id);
    }

    private void listarMoradores() {
        listarItens(moradorService.listar(), m -> m.getId() + " - " + m.getNome());
    }
}
