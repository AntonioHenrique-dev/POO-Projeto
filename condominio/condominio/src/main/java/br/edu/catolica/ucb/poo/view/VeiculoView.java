package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.model.Veiculo;
import br.edu.catolica.ucb.poo.service.MoradorService;
import br.edu.catolica.ucb.poo.service.VeiculoService;

import java.util.List;

public class VeiculoView extends CrudView<Veiculo> {

    private final VeiculoService service = new VeiculoService();
    private final MoradorService moradorService = new MoradorService();

    public VeiculoView(Console console) {
        super(console, "VEICULOS E VAGAS");
    }

    @Override
    protected List<Veiculo> listarTodos() {
        return service.listar();
    }

    @Override
    protected String linha(Veiculo veiculo) {
        String cor = veiculo.getCor() == null ? "-" : veiculo.getCor();
        return veiculo.getId() + " - Placa: " + veiculo.getPlaca() + " | Modelo: " + veiculo.getModelo()
                + " | Cor: " + cor + " | Vaga: " + veiculo.getVaga()
                + " | Morador: " + veiculo.getMorador().getNome();
    }

    @Override
    protected void cadastrar() {
        String placa = console.lerTexto("Placa: ");
        String modelo = console.lerTexto("Modelo: ");
        String cor = console.lerTexto("Cor (opcional): ");
        String vaga = console.lerTexto("Vaga: ");
        listarMoradores();
        int moradorId = console.lerInteiro("Id do morador: ");
        Veiculo veiculo = service.cadastrar(placa, modelo, cor, vaga, moradorId);
        console.sucesso("Veiculo cadastrado com id " + veiculo.getId() + ".");
    }

    @Override
    protected void atualizar() {
        listar();
        int id = console.lerInteiro("Id do veiculo a atualizar (0 cancela): ");
        if (id == 0) {
            return;
        }
        String placa = console.lerTexto("Nova placa: ");
        String modelo = console.lerTexto("Novo modelo: ");
        String cor = console.lerTexto("Nova cor (opcional): ");
        String vaga = console.lerTexto("Nova vaga: ");
        listarMoradores();
        int moradorId = console.lerInteiro("Id do morador: ");
        service.atualizar(id, placa, modelo, cor, vaga, moradorId);
        console.sucesso("Veiculo atualizado.");
    }

    @Override
    protected void excluir(Integer id) {
        service.excluir(id);
    }

    private void listarMoradores() {
        listarItens(moradorService.listar(), m -> m.getId() + " - " + m.getNome());
    }
}
