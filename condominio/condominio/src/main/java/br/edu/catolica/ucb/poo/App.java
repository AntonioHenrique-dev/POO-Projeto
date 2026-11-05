package br.edu.catolica.ucb.poo;

import br.edu.catolica.ucb.poo.util.JPAUtil;
import br.edu.catolica.ucb.poo.view.Console;
import br.edu.catolica.ucb.poo.view.LoginView;

import java.io.File;

public class App {

    public static void main(String[] args) {
        try {
            new File("dados").mkdirs();
            System.out.println("Conectando ao banco SQLite: dados/condominio.db");
            JPAUtil.getEntityManagerFactory();
            new LoginView(new Console()).exibir();
        } catch (RuntimeException e) {
            System.out.println("ERRO: nao foi possivel iniciar o sistema. " + e.getMessage());
        } finally {
            JPAUtil.fechar();
        }
    }
}
