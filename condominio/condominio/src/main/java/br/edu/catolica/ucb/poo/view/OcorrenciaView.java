package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.model.Ocorrencia;
import br.edu.catolica.ucb.poo.model.StatusOcorrencia;
import br.edu.catolica.ucb.poo.service.OcorrenciaService;

import java.util.List;

public class OcorrenciaView extends CrudView<Ocorrencia> {

    private final OcorrenciaService service = new OcorrenciaService();

    public OcorrenciaView(Console console) {
        super(console, "OCORRENCIAS");
    }

    @Override
    protected List<Ocorrencia> listarTodos() {
        return service.listar();
    }

    @Override
    protected String linha(Ocorrencia ocorrencia) {
        return ocorrencia.getId() + " - [" + ocorrencia.getStatus() + "] " + ocorrencia.getDescricao()
                + " | Registrada por: " + ocorrencia.getRegistradoPor()
                + " em " + ocorrencia.getDataRegistro().format(Console.FORMATO_DATA_HORA);
    }

    @Override
    protected void cadastrar() {
        String descricao = console.lerTexto("Descricao: ");
        String registradoPor = console.lerTexto("Registrado por: ");
        Ocorrencia ocorrencia = service.registrar(descricao, registradoPor);
        console.sucesso("Ocorrencia registrada com id " + ocorrencia.getId() + ".");
    }

    @Override
    protected void atualizar() {
        listar();
        int id = console.lerInteiro("Id da ocorrencia a atualizar (0 cancela): ");
        if (id == 0) {
            return;
        }
        String descricao = console.lerTexto("Nova descricao: ");
        StatusOcorrencia status = lerStatus();
        service.atualizar(id, descricao, status);
        console.sucesso("Ocorrencia atualizada.");
    }

    @Override
    protected void excluir(Integer id) {
        service.excluir(id);
    }

    private StatusOcorrencia lerStatus() {
        StatusOcorrencia[] valores = StatusOcorrencia.values();
        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + " - " + valores[i]);
        }
        int opcao = console.lerInteiro("Novo status: ");
        if (opcao < 1 || opcao > valores.length) {
            throw new IllegalArgumentException("Status invalido.");
        }
        return valores[opcao - 1];
    }
}
