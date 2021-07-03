package br.com.tamanhofamilia.dasa.receituario.services.pacientes;

import br.com.tamanhofamilia.dasa.receituario.daos.paciente.PacienteDao;
import br.com.tamanhofamilia.dasa.receituario.models.paciente.Paciente;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PacientesService implements IPacientesService {
    private final PacienteDao dao;

    @Autowired
    public PacientesService(PacienteDao dao) {
        this.dao = dao;
    }

    @Override
    public Page<Paciente> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    public Integer create(Paciente paciente) {
        final Paciente saved = dao.save(paciente);
        return saved.getIdPaciente();
    }

    @Override
    public void update(Paciente paciente) throws DataNotFoundException {
        if ( !dao.existsById(paciente.getIdPaciente()) ) {
            throw new DataNotFoundException(String.format("Paciente não encontrado. Id: %d", paciente.getIdPaciente()) );
        }
        dao.save(paciente);
    }

    @Override
    public Optional<Paciente> getById(@NonNull Integer id) {
        return dao.findById(id);
    }

    @Override
    public void delete(@NonNull Integer id) throws DataNotFoundException {
        if (!dao.existsById(id)) {
            throw new DataNotFoundException(String.format("Paciente não encontrado. Id: %d", id) );
        }
        dao.deleteById(id);
    }
}
