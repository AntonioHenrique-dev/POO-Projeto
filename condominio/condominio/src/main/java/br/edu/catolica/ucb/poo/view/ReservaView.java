package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.model.Reserva;
import br.edu.catolica.ucb.poo.service.AreaComumService;
import br.edu.catolica.ucb.poo.service.MoradorService;
import br.edu.catolica.ucb.poo.service.ReservaService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservaView extends CrudView<Reserva> {

    private final ReservaService service = new ReservaService();
    private final AreaComumService areaService = new AreaComumService();
    private final MoradorService moradorService = new MoradorService();

    public ReservaView(Console console) {
        super(console, "RESERVAS DE AREAS COMUNS");
    }

    @Override
    protected List<Reserva> listarTodos() {
        return service.listar();
    }

    @Override
    protected String linha(Reserva reserva) {
        return reserva.getId() + " - Area: " + reserva.getArea().getNome()
                + " | Data: " + reserva.getData().format(Console.FORMATO_DATA)
                + " | Horario: " + reserva.getHoraInicio().format(Console.FORMATO_HORA)
                + " as " + reserva.getHoraFim().format(Console.FORMATO_HORA)
                + " | Morador: " + reserva.getMorador().getNome();
    }

    @Override
    protected void cadastrar() {
        listarAreas();
        int areaId = console.lerInteiro("Id da area: ");
        listarMoradores();
        int moradorId = console.lerInteiro("Id do morador: ");
        Periodo periodo = lerPeriodo();
        if (periodo == null) {
            cancelada();
            return;
        }
        Reserva reserva = service.cadastrar(areaId, moradorId, periodo.data(), periodo.inicio(), periodo.fim());
        console.sucesso("Reserva cadastrada com id " + reserva.getId() + ".");
    }

    @Override
    protected void atualizar() {
        listar();
        int id = console.lerInteiro("Id da reserva a atualizar (0 cancela): ");
        if (id == 0) {
            return;
        }
        listarAreas();
        int areaId = console.lerInteiro("Id da area: ");
        listarMoradores();
        int moradorId = console.lerInteiro("Id do morador: ");
        Periodo periodo = lerPeriodo();
        if (periodo == null) {
            cancelada();
            return;
        }
        service.atualizar(id, areaId, moradorId, periodo.data(), periodo.inicio(), periodo.fim());
        console.sucesso("Reserva atualizada.");
    }

    @Override
    protected void excluir(Integer id) {
        service.excluir(id);
    }

    private Periodo lerPeriodo() {
        LocalDate data = console.lerData("Data (dd/MM/aaaa, 0 cancela): ");
        if (data == null) {
            return null;
        }
        LocalTime inicio = console.lerHora("Horario de inicio (HH:mm, 0 cancela): ");
        if (inicio == null) {
            return null;
        }
        LocalTime fim = console.lerHora("Horario de fim (HH:mm, 0 cancela): ");
        if (fim == null) {
            return null;
        }
        return new Periodo(data, inicio, fim);
    }

    private void listarAreas() {
        listarItens(areaService.listar(), a -> a.getId() + " - " + a.getNome());
    }

    private void listarMoradores() {
        listarItens(moradorService.listar(), m -> m.getId() + " - " + m.getNome());
    }

    private void cancelada() {
        System.out.println("Operacao cancelada.");
    }

    private record Periodo(LocalDate data, LocalTime inicio, LocalTime fim) {
    }
}
