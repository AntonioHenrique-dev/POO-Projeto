import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Programa unico demonstrando ArrayList, HashSet e HashMap
 * usando o tema do projeto: gestao de condominio.
 */
public class Colecoes {

    public static void main(String[] args) {
        arrayList();
        hashSet();
        hashMap();
    }

    // ArrayList: lista ordenada, aceita elementos repetidos, acesso por indice
    private static void arrayList() {
        System.out.println("=== ArrayList: moradores ===");
        List<Morador> moradores = new ArrayList<>();
        moradores.add(new Morador(1, "Carlos Silva", 101));
        moradores.add(new Morador(2, "Ana Souza", 202));
        moradores.add(new Morador(3, "Bruno Lima", 303));
        moradores.add(new Morador(4, "Daniela Rocha", 404));

        System.out.println("Total: " + moradores.size());
        System.out.println("Primeiro: " + moradores.get(0));

        moradores.remove(2); // remove pelo indice (Bruno Lima)
        moradores.sort(Comparator.comparing(m -> m.nome));

        System.out.println("Ordenados por nome, apos remover um:");
        for (Morador m : moradores) {
            System.out.println("  " + m);
        }
        System.out.println();
    }

    // HashSet: nao permite repetidos e nao garante ordem
    private static void hashSet() {
        System.out.println("=== HashSet: placas de veiculos ===");
        Set<String> placas = new HashSet<>();
        System.out.println("Adicionou ABC1D23? " + placas.add("ABC1D23"));
        System.out.println("Adicionou XYZ9K88? " + placas.add("XYZ9K88"));
        System.out.println("Adicionou ABC1D23 de novo? " + placas.add("ABC1D23"));

        System.out.println("Total (sem repetidas): " + placas.size());
        System.out.println("Contem XYZ9K88? " + placas.contains("XYZ9K88"));

        placas.remove("XYZ9K88");
        System.out.println("Placas apos remover: " + placas);

        // HashSet de objetos: depende de equals e hashCode da classe Morador
        Set<Morador> unicos = new HashSet<>();
        unicos.add(new Morador(1, "Carlos Silva", 101));
        unicos.add(new Morador(1, "Carlos Silva", 101)); // mesmo id: ignorado
        System.out.println("Moradores unicos: " + unicos.size());
        System.out.println();
    }

    // HashMap: pares chave/valor, chave unica, busca rapida pela chave
    private static void hashMap() {
        System.out.println("=== HashMap: unidade -> morador ===");
        Map<Integer, Morador> porUnidade = new HashMap<>();
        porUnidade.put(101, new Morador(1, "Carlos Silva", 101));
        porUnidade.put(202, new Morador(2, "Ana Souza", 202));
        porUnidade.put(404, new Morador(4, "Daniela Rocha", 404));

        System.out.println("Morador da unidade 202: " + porUnidade.get(202));
        System.out.println("Existe unidade 303? " + porUnidade.containsKey(303));
        System.out.println("Unidade 303 (padrao): "
                + porUnidade.getOrDefault(303, new Morador(0, "Vaga", 303)));

        // put com chave existente substitui o valor
        porUnidade.put(101, new Morador(5, "Eduardo Dias", 101));

        System.out.println("Todas as unidades:");
        for (Map.Entry<Integer, Morador> e : porUnidade.entrySet()) {
            System.out.println("  Unidade " + e.getKey() + " -> " + e.getValue().nome);
        }

        porUnidade.remove(404);
        System.out.println("Chaves apos remover 404: " + porUnidade.keySet());

        // Contagem de ocorrencias por tipo: uso classico de HashMap
        String[] ocorrencias = {"barulho", "vazamento", "barulho", "lixo", "barulho"};
        Map<String, Integer> contagem = new HashMap<>();
        for (String tipo : ocorrencias) {
            contagem.put(tipo, contagem.getOrDefault(tipo, 0) + 1);
        }
        System.out.println("Ocorrencias por tipo: " + contagem);
    }

    // Classe auxiliar (no mesmo arquivo, sem modificador public)
    static class Morador {
        final int id;
        final String nome;
        final int unidade;

        Morador(int id, String nome, int unidade) {
            this.id = id;
            this.nome = nome;
            this.unidade = unidade;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Morador)) return false;
            return id == ((Morador) o).id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return nome + " (unidade " + unidade + ")";
        }
    }
}
