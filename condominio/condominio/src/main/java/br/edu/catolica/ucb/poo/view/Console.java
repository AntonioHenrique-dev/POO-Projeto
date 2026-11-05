package br.edu.catolica.ucb.poo.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public class Console {

    public static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
    public static final DateTimeFormatter FORMATO_HORA =
            DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);
    public static final DateTimeFormatter FORMATO_DATA_HORA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);

    private final Scanner scanner = new Scanner(System.in);

    public String lerTexto(String rotulo) {
        System.out.print(rotulo);
        if (!scanner.hasNextLine()) {
            return "0";
        }
        return scanner.nextLine().trim();
    }

    public int lerInteiro(String rotulo) {
        while (true) {
            String valor = lerTexto(rotulo);
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido. Digite um numero inteiro.");
            }
        }
    }

    public BigDecimal lerDecimal(String rotulo) {
        while (true) {
            String valor = lerTexto(rotulo).replace(",", ".");
            try {
                return new BigDecimal(valor);
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido. Exemplo: 19.90");
            }
        }
    }

    public LocalDate lerData(String rotulo) {
        while (true) {
            String valor = lerTexto(rotulo);
            if (valor.equals("0")) {
                return null;
            }
            try {
                return LocalDate.parse(valor, FORMATO_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data invalida. Use o formato dd/MM/aaaa (0 cancela).");
            }
        }
    }

    public LocalTime lerHora(String rotulo) {
        while (true) {
            String valor = lerTexto(rotulo);
            if (valor.equals("0")) {
                return null;
            }
            try {
                return LocalTime.parse(valor, FORMATO_HORA);
            } catch (DateTimeParseException e) {
                System.out.println("Hora invalida. Use o formato HH:mm (0 cancela).");
            }
        }
    }

    public void titulo(String texto) {
        System.out.println();
        System.out.println("=== " + texto + " ===");
    }

    public void sucesso(String mensagem) {
        System.out.println("OK: " + mensagem);
    }

    public void erro(String mensagem) {
        System.out.println("ERRO: " + mensagem);
    }
}
