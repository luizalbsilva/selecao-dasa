package br.com.tamanhofamilia.dasa.receituario.services.pedidos;

import br.com.tamanhofamilia.dasa.receituario.daos.medico.MedicoDao;
import br.com.tamanhofamilia.dasa.receituario.daos.paciente.PacienteDao;
import br.com.tamanhofamilia.dasa.receituario.daos.pedidos.PedidoDao;
import br.com.tamanhofamilia.dasa.receituario.models.medico.Medico;
import br.com.tamanhofamilia.dasa.receituario.models.paciente.Paciente;
import br.com.tamanhofamilia.dasa.receituario.models.pedidos.Pedido;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidosServiceTest {
    @InjectMocks
    PedidosService service;

    @Mock
    PedidoDao pedidoDao;

    @Mock
    PacienteDao pacienteDao;

    @Mock
    MedicoDao medicoDao;

    @Mock
    Pageable pageable;

    @Test
    void findAll() {
        service.findAll(pageable);

        verify(pedidoDao).findAll(pageable);
    }

    @Test
    void create() {
        final int idMedico = 1;
        final int idPaciente = 2;
        Pedido pedido = Pedido.builder()
                .medico(Medico.builder().idMedico(idMedico).build())
                .paciente(Paciente.builder().idPaciente(idPaciente).build())
                .build();
        when(medicoDao.existsById(idMedico)).thenReturn(true);
        when(pacienteDao.existsById(idPaciente)).thenReturn(true);
        when(pedidoDao.save(pedido)).thenAnswer(e -> {
            Pedido exam = e.getArgument(0);
            exam.setIdPedido(1);
            return exam;
        });

        assertSame(1, service.create(pedido));

        verify(pedidoDao).save(pedido);
    }


    @Test
    void update() throws DataNotFoundException {
        final int id = 3;
        final int idMedico = 4;
        final int idPaciente = 5;
        Pedido pedido = Pedido.builder().idPedido(id)
                .medico(Medico.builder().idMedico(idMedico).build())
                .paciente(Paciente.builder().idPaciente(idPaciente).build())
                .build();
        when(medicoDao.existsById(idMedico)).thenReturn(true);
        when(pacienteDao.existsById(idPaciente)).thenReturn(true);
        when(pedidoDao.existsById(id)).thenReturn(true);

        service.update(pedido);

        verify(pedidoDao).save(pedido);
    }

    @Test
    void updateNotFound() {
        final int idMedico = 6;
        final int idPaciente = 7;
        final int idPedido = 8;
        Pedido pedido = Pedido.builder().idPedido(idPedido)
                .medico(Medico.builder().idMedico(idMedico).build())
                .paciente(Paciente.builder().idPaciente(idPaciente).build())
                .build();
        when(medicoDao.existsById(idMedico)).thenReturn(true);
        when(pacienteDao.existsById(idPaciente)).thenReturn(true);

        assertThrows(DataNotFoundException.class, () -> {
            service.update(pedido);
        });
    }

    @Test
    void getById() {
        final int id = 1;
        final Optional<Pedido> daoReturn = Optional.empty();
        when(pedidoDao.findById(id)).thenReturn(daoReturn);

        final Optional<Pedido> toCheck = service.getById(id);

        assertSame(daoReturn, toCheck);
        verify(pedidoDao).findById(id);
    }

    @Test
    void delete() throws DataNotFoundException {
        final int id = 1;
        when(pedidoDao.existsById(id))
                .thenReturn(true);

        service.delete(id);

        verify(pedidoDao).deleteById(id);
    }

    @Test
    void deleteNotFound() {
        assertThrows(DataNotFoundException.class, () -> {
            final int id = 1;
            when(pedidoDao.existsById(id))
                    .thenReturn(false);
            service.delete(id);
        });
    }
}