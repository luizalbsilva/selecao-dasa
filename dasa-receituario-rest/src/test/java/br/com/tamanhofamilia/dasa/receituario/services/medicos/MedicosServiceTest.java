package br.com.tamanhofamilia.dasa.receituario.services.medicos;

import br.com.tamanhofamilia.dasa.receituario.daos.medico.MedicoDao;
import br.com.tamanhofamilia.dasa.receituario.models.medico.Medico;
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
class MedicosServiceTest {
    @InjectMocks
    MedicosService service;

    @Mock
    MedicoDao dao;

    @Mock
    Pageable pageable;

    @Test
    void findAll() {
        service.findAll(pageable);

        verify(dao).findAll(pageable);
    }

    @Test
    void create() {
        Medico medico = new Medico();
        when(dao.save(medico)).thenAnswer(e -> {
            Medico exam = e.getArgument(0);
            exam.setIdMedico(1);
            return exam;
        });

        assertSame(1, service.create(medico));

        verify(dao).save(medico);

    }


    @Test
    void update() throws DataNotFoundException {
        final int id = 1;
        when(dao.existsById(id)).thenReturn(true);
        Medico medico = Medico.builder().idMedico(id).build();

        service.update(medico);

        verify(dao).save(medico);
    }

    @Test
    void updateNotFound() {
        Medico medico = Medico.builder().idMedico(1).build();

        assertThrows(DataNotFoundException.class, () -> {
            service.update(medico);
        });
    }

    @Test
    void getById() {
        final int id = 1;
        final Optional<Medico> daoReturn = Optional.empty();
        when(dao.findById(id)).thenReturn(daoReturn);

        final Optional<Medico> toCheck = service.getById(id);

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