package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.model.TipoAcesso;
import br.edu.catolica.ucb.poo.service.AcessoService;

import java.util.List;

public class ControleAcessoView extends MenuView {

    private final AcessoService acessoService = new AcessoService();
    private final VisitanteView visitanteView;

    public ControleAcessoView(Console console) {
        super(console, "CONTROLE DE ACESSO", "Voltar");
        this.visitanteView = new VisitanteView(console);
    }

    @Override
    protected List<Opcao> opcoes() {
        return List.of(
                new Opcao("Registrar visitante", visitanteView::cadastrar),
                new Opcao("Registrar entrada/saida", this::registrarAcesso),
                new Opcao("Listar acessos", this::listarAcessos));
    }

    private void registrarAcesso() {
        String pessoa = console.lerTexto("Nome da pessoa: ");
        System.out.println("1 - Entrada");
        System.out.println("2 - Saida");
        int opcao = console.lerInteiro("Tipo: ");
        TipoAcesso tipo = switch (opcao) {
            case 1 -> TipoAcesso.ENTRADA;
            case 2 -> TipoAcesso.SAIDA;
            default -> throw new IllegalArgumentException("Tipo de acesso invalido.");
        };
        acessoService.registrar(pessoa, tipo);
        console.sucesso("Acesso registrado.");
    }

    private void listarAcessos() {
        listarItens(acessoService.listar(),
                a -> a.getId() + " - " + a.getPessoa()
                        + " | " + (a.getTipo() == TipoAcesso.ENTRADA ? "Entrada" : "Saida")
                        + " | " + a.getDataHora().format(Console.FORMATO_DATA_HORA));
    }
}
