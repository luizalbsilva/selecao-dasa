package br.com.tamanhofamilia.dasa.receituario.services.pacientes;

import br.com.tamanhofamilia.dasa.receituario.daos.paciente.PacienteDao;
import br.com.tamanhofamilia.dasa.receituario.models.paciente.Paciente;
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
class PacientesServiceTest {
    @InjectMocks
    PacientesService service;

    @Mock
    PacienteDao dao;

    @Mock
    Pageable pageable;

    @Test
    void findAll() {
        service.findAll(pageable);

        verify(dao).findAll(pageable);
    }

    @Test
    void create() {
        Paciente paciente = new Paciente();
        when(dao.save(paciente)).thenAnswer(e -> {
            Paciente pac = e.getArgument(0);
            pac.setIdPaciente(1);
            return pac;
        });

        assertSame(1, service.create(paciente));

        verify(dao).save(paciente);
    }


    @Test
    void update() throws DataNotFoundException {
        final int id = 1;
        when(dao.existsById(id)).thenReturn(true);
        Paciente paciente = Paciente.builder().idPaciente(id).build();

        service.update(paciente);

        verify(dao).save(paciente);
    }

    @Test
    void updateNotFound() {
        Paciente paciente = Paciente.builder().idPaciente(1).build();

        assertThrows(DataNotFoundException.class, () -> {
            service.update(paciente);
        });
    }

    @Test
    void getById() {
        final int id = 1;
        final Optional<Paciente> daoReturn = Optional.empty();
        when(dao.findById(id)).thenReturn(daoReturn);

        final Optional<Paciente> toCheck = service.getById(id);

        assertSame(daoReturn, toCheck);
        verify(dao).findById(id);
    }

    @Test
    void delete() throws DataNotFoundException {
        final int id = 1;
        when(dao.existsById(id))
                .thenReturn(true);

        service.delete(id);

        verify(dao).deleteById(id);
    }

    @Test
    void deleteNotFound() {
        assertThrows(DataNotFoundException.class, () -> {
            final int id = 1;
            when(dao.existsById(id))
                    .thenReturn(false);
            service.delete(id);
        });
    }
}