package br.edu.catolica.ucb.poo.view;

import java.util.List;
import java.util.function.Function;

public abstract class MenuView {

    protected record Opcao(String rotulo, Runnable acao) {
    }

    protected final Console console;
    private final String titulo;
    private final String rotuloVoltar;

    protected MenuView(Console console, String titulo, String rotuloVoltar) {
        this.console = console;
        this.titulo = titulo;
        this.rotuloVoltar = rotuloVoltar;
    }

    protected abstract List<Opcao> opcoes();

    public void exibir() {
        List<Opcao> opcoes = opcoes();
        while (true) {
            console.titulo(titulo);
            for (int i = 0; i < opcoes.size(); i++) {
                System.out.println((i + 1) + " - " + opcoes.get(i).rotulo());
            }
            System.out.println("0 - " + rotuloVoltar);
            int opcao = console.lerInteiro("Opcao: ");
            if (opcao == 0) {
                return;
            }
            if (opcao < 1 || opcao > opcoes.size()) {
                console.erro("Opcao invalida.");
                continue;
            }
            executar(opcoes.get(opcao - 1).acao());
        }
    }

    protected <E> void listarItens(List<E> itens, Function<E, String> linha) {
        if (itens.isEmpty()) {
            System.out.println("Nenhum registro cadastrado.");
            return;
        }
        itens.forEach(item -> System.out.println(linha.apply(item)));
    }

    private void executar(Runnable acao) {
        try {
            acao.run();
        } catch (IllegalArgumentException | IllegalStateException e) {
            console.erro(e.getMessage());
        } catch (RuntimeException e) {
            console.erro("Falha ao acessar o banco de dados.");
        }
    }
}
