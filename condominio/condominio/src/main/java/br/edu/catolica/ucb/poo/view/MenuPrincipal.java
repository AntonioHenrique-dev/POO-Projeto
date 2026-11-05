package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.model.Perfil;
import br.edu.catolica.ucb.poo.service.EncomendaService;
import br.edu.catolica.ucb.poo.service.MoradorService;
import br.edu.catolica.ucb.poo.service.VisitanteService;

import java.util.List;

public class MenuPrincipal extends MenuView {

    private final Perfil perfil;
    private final MoradorService moradorService = new MoradorService();
    private final VisitanteService visitanteService = new VisitanteService();
    private final EncomendaService encomendaService = new EncomendaService();

    public MenuPrincipal(Console console, Perfil perfil) {
        super(console, "MENU " + perfil.getRotulo().toUpperCase(), "Voltar");
        this.perfil = perfil;
    }

    @Override
    protected List<Opcao> opcoes() {
        return switch (perfil) {
            case ADMINISTRADOR -> List.of(
                    new Opcao("Gerenciar funcionario", () -> new FuncionarioView(console).exibir()),
                    new Opcao("Gerenciar morador", () -> new MoradorView(console).exibir()),
                    new Opcao("Gerenciar unidade", () -> new UnidadeView(console).exibir()),
                    new Opcao("Gerenciar veiculo e vaga", () -> new VeiculoView(console).exibir()),
                    new Opcao("Gerar relatorios", () -> new RelatorioView(console).exibir()));
            case SINDICO -> List.of(
                    new Opcao("Gerenciar areas comuns", () -> new AreaComumView(console).exibir()),
                    new Opcao("Registrar ocorrencia", () -> new OcorrenciaView(console).exibir()),
                    new Opcao("Gerar relatorios", () -> new RelatorioView(console).exibir()));
            case PORTEIRO -> List.of(
                    new Opcao("Controlar acesso", () -> new ControleAcessoView(console).exibir()),
                    new Opcao("Cadastrar veiculo e vaga", () -> new VeiculoView(console).exibir()),
                    new Opcao("Registrar encomenda", () -> new EncomendaView(console).exibir()));
            case MORADOR -> List.of(
                    new Opcao("Reservar area comum", () -> new ReservaView(console).exibir()),
                    new Opcao("Consultar encomendas", this::consultarEncomendas),
                    new Opcao("Autorizar visitante", this::autorizarVisitante),
                    new Opcao("Registrar ocorrencia", () -> new OcorrenciaView(console).exibir()));
        };
    }

    private void consultarEncomendas() {
        int moradorId = identificarMorador();
        listarItens(encomendaService.listarPorDestinatario(moradorId),
                e -> e.getId() + " - " + e.getDescricao()
                        + " | Retirada: " + (e.isRetirada() ? "Sim" : "Nao")
                        + " | Recebida em: " + e.getDataRegistro().format(Console.FORMATO_DATA_HORA));
    }

    private void autorizarVisitante() {
        int moradorId = identificarMorador();
        listarItens(visitanteService.listarPorMorador(moradorId),
                v -> v.getId() + " - " + v.getNome()
                        + " | Documento: " + v.getDocumento()
                        + " | Autorizado: " + (v.isAutorizado() ? "Sim" : "Nao"));
        int visitanteId = console.lerInteiro("Id do visitante a autorizar (0 cancela): ");
        if (visitanteId == 0) {
            return;
        }
        visitanteService.autorizar(visitanteId, moradorId);
        console.sucesso("Visitante autorizado.");
    }

    private int identificarMorador() {
        int moradorId = console.lerInteiro("Informe seu id de morador: ");
        moradorService.buscarPorId(moradorId);
        return moradorId;
    }
}
