package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.model.AreaComum;
import br.edu.catolica.ucb.poo.model.Encomenda;
import br.edu.catolica.ucb.poo.model.Funcionario;
import br.edu.catolica.ucb.poo.model.Morador;
import br.edu.catolica.ucb.poo.model.Ocorrencia;
import br.edu.catolica.ucb.poo.model.Reserva;
import br.edu.catolica.ucb.poo.model.StatusOcorrencia;
import br.edu.catolica.ucb.poo.model.Unidade;
import br.edu.catolica.ucb.poo.model.Veiculo;
import br.edu.catolica.ucb.poo.model.Visitante;
import br.edu.catolica.ucb.poo.repository.Repositorio;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RelatorioService {

    public Map<String, Long> resumoGeral() {
        Map<String, Long> resumo = new LinkedHashMap<>();
        resumo.put("Unidades cadastradas", contarTodos(Unidade.class));
        resumo.put("Moradores cadastrados", contarTodos(Morador.class));
        resumo.put("Funcionarios cadastrados", contarTodos(Funcionario.class));
        resumo.put("Veiculos cadastrados", contarTodos(Veiculo.class));
        resumo.put("Areas comuns cadastradas", contarTodos(AreaComum.class));
        resumo.put("Reservas realizadas", contarTodos(Reserva.class));
        resumo.put("Visitantes autorizados", contarPor("SELECT COUNT(v) FROM Visitante v WHERE v.autorizado = :valor",
                Map.of("valor", true)));
        resumo.put("Encomendas pendentes de retirada", contarPor(
                "SELECT COUNT(e) FROM Encomenda e WHERE e.retirada = :valor", Map.of("valor", false)));
        return resumo;
    }

    public Map<StatusOcorrencia, Long> ocorrenciasPorStatus() {
        Map<StatusOcorrencia, Long> contagem = new EnumMap<>(StatusOcorrencia.class);
        for (StatusOcorrencia status : StatusOcorrencia.values()) {
            contagem.put(status, contarPor("SELECT COUNT(o) FROM Ocorrencia o WHERE o.status = :status",
                    Map.of("status", status)));
        }
        return contagem;
    }

    private long contarTodos(Class<?> tipo) {
        return contarPor("SELECT COUNT(e) FROM " + tipo.getSimpleName() + " e", Map.of());
    }

    private long contarPor(String jpql, Map<String, Object> parametros) {
        return new Repositorio<>(Object.class).contar(jpql, parametros);
    }
}
