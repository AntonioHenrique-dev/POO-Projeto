package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.service.RelatorioService;

import java.util.List;

public class RelatorioView extends MenuView {

    private final RelatorioService service = new RelatorioService();

    public RelatorioView(Console console) {
        super(console, "RELATORIOS", "Voltar");
    }

    @Override
    protected List<Opcao> opcoes() {
        return List.of(
                new Opcao("Resumo geral", this::resumoGeral),
                new Opcao("Ocorrencias por status", this::ocorrenciasPorStatus));
    }

    private void resumoGeral() {
        service.resumoGeral().forEach((rotulo, valor) -> System.out.println(rotulo + ": " + valor));
    }

    private void ocorrenciasPorStatus() {
        service.ocorrenciasPorStatus().forEach((status, quantidade) ->
                System.out.println(status + ": " + quantidade));
    }
}
