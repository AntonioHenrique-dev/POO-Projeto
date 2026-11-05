package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.model.Perfil;

public class LoginView {

    private final Console console;

    public LoginView(Console console) {
        this.console = console;
    }

    public void exibir() {
        while (true) {
            console.titulo("SELECIONE O PERFIL");
            System.out.println("1 - Administrador");
            System.out.println("2 - Sindico");
            System.out.println("3 - Porteiro");
            System.out.println("4 - Morador");
            System.out.println("0 - Sair");
            int opcao = console.lerInteiro("Opcao: ");
            if (opcao == 0) {
                return;
            }
            Perfil perfil = switch (opcao) {
                case 1 -> Perfil.ADMINISTRADOR;
                case 2 -> Perfil.SINDICO;
                case 3 -> Perfil.PORTEIRO;
                case 4 -> Perfil.MORADOR;
                default -> null;
            };
            if (perfil == null) {
                console.erro("Opcao invalida.");
                continue;
            }
            if (login(perfil)) {
                new MenuPrincipal(console, perfil).exibir();
            }
        }
    }

    private boolean login(Perfil perfil) {
        console.titulo("LOGIN - " + perfil.getRotulo().toUpperCase());
        System.out.println("Acesso simulado: usuario e senha nao sao validados.");
        String usuario = console.lerTexto("Usuario (0 volta): ");
        if (usuario.equals("0")) {
            return false;
        }
        console.lerTexto("Senha: ");
        return true;
    }
}
