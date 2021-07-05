package br.com.tamanhofamilia.dasa.receituario.services.medicos;

import br.com.tamanhofamilia.dasa.receituario.daos.medico.ConselhoDao;
import br.com.tamanhofamilia.dasa.receituario.daos.medico.MedicoDao;
import br.com.tamanhofamilia.dasa.receituario.models.medico.Conselho;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicosServiceTest {
    @InjectMocks
    MedicosService service;

    @Mock
    MedicoDao medicoDao;

    @Mock
    ConselhoDao conselhoDao;

    @Mock
    Pageable pageable;

    @Test
    void findAll() {
        service.findAll(pageable);

        verify(medicoDao).findAll(pageable);
    }

    @Test
    void create() {
        Medico medico = new Medico();
        when(medicoDao.save(medico)).thenAnswer(e -> {
            Medico exam = e.getArgument(0);
            exam.setIdMedico(1);
            return exam;
        });

        assertSame(1, service.create(medico));

        verify(medicoDao).save(medico);

    }


    @Test
    void update() throws DataNotFoundException {
        final int id = 1;
        when(medicoDao.existsById(id)).thenReturn(true);
        Medico medico = Medico.builder().idMedico(id).build();

        service.update(medico);

        verify(medicoDao).save(medico);
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
        when(medicoDao.findById(id)).thenReturn(daoReturn);

        final Optional<Medico> toCheck = service.getById(id);

        assertSame(daoReturn, toCheck);
        verify(medicoDao).findById(id);
    }

    @Test
    void delete() throws DataNotFoundException {
        final int id = 1;
        when(medicoDao.existsById(id))
                .thenReturn(true);

        service.delete(id);

        verify(medicoDao).deleteById(id);
    }

    @Test
    void deleteNotFound() {
        assertThrows(DataNotFoundException.class, () -> {
            final int id = 1;
            when(medicoDao.existsById(id))
                    .thenReturn(false);
            service.delete(id);
        });
    }
}