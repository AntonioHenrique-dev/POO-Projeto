package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.model.AreaComum;
import br.edu.catolica.ucb.poo.model.Morador;
import br.edu.catolica.ucb.poo.model.Reserva;
import br.edu.catolica.ucb.poo.repository.Repositorio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class ReservaService {

    private final Repositorio<Reserva> repositorio = new Repositorio<>(Reserva.class);
    private final AreaComumService areaService = new AreaComumService();
    private final MoradorService moradorService = new MoradorService();

    public List<Reserva> listar() {
        return repositorio.listar();
    }

    public Reserva buscarPorId(Integer id) {
        Reserva reserva = repositorio.buscarPorId(id);
        if (reserva == null) {
            throw new IllegalArgumentException("Reserva com id " + id + " nao encontrada.");
        }
        return reserva;
    }

    public Reserva cadastrar(Integer areaId, Integer moradorId, LocalDate data, LocalTime inicio, LocalTime fim) {
        validar(data, inicio, fim, null, areaId);
        AreaComum area = areaService.buscarPorId(areaId);
        Morador morador = moradorService.buscarPorId(moradorId);
        return repositorio.salvar(new Reserva(area, morador, data, inicio, fim));
    }

    public Reserva atualizar(Integer id, Integer areaId, Integer moradorId, LocalDate data,
                             LocalTime inicio, LocalTime fim) {
        validar(data, inicio, fim, id, areaId);
        Reserva reserva = buscarPorId(id);
        reserva.setArea(areaService.buscarPorId(areaId));
        reserva.setMorador(moradorService.buscarPorId(moradorId));
        reserva.setData(data);
        reserva.setHoraInicio(inicio);
        reserva.setHoraFim(fim);
        return repositorio.atualizar(reserva);
    }

    public void excluir(Integer id) {
        buscarPorId(id);
        repositorio.excluir(id);
    }

    private void validar(LocalDate data, LocalTime inicio, LocalTime fim, Integer idIgnorado, Integer areaId) {
        if (data == null || inicio == null || fim == null) {
            throw new IllegalArgumentException("Data e horarios sao obrigatorios.");
        }
        if (data.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Nao e possivel reservar em data passada.");
        }
        if (!inicio.isBefore(fim)) {
            throw new IllegalArgumentException("O horario de inicio deve ser anterior ao horario de fim.");
        }
        boolean conflito = repositorio.listar().stream()
                .anyMatch(r -> r.getArea().getId().equals(areaId)
                        && r.getData().equals(data)
                        && !Objects.equals(r.getId(), idIgnorado)
                        && inicio.isBefore(r.getHoraFim())
                        && r.getHoraInicio().isBefore(fim));
        if (conflito) {
            throw new IllegalArgumentException("A area ja esta reservada nesse horario.");
        }
    }
}
