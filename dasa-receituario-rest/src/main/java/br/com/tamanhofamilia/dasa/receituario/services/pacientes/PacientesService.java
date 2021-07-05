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

/**
 * Serviço de Pacientes
 */
@Service
public class PacientesService implements IPacientesService {
    /** Dao de acesso aos dados de paciente */
    private final PacienteDao dao;

    @Autowired
    public PacientesService(PacienteDao dao) {
        this.dao = dao;
    }

    /** {@inheritDoc} */
    @Override
    public Page<Paciente> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    /** {@inheritDoc} */
    @Override
    public Integer create(Paciente data) {
        final Paciente saved = dao.save(data);
        return saved.getIdPaciente();
    }

    /** {@inheritDoc} */
    @Override
    public void update(Paciente data) throws DataNotFoundException {
        if (!dao.existsById(data.getIdPaciente())) {
            throw new DataNotFoundException(String.format("Paciente não encontrado. Id: %d", data.getIdPaciente()));
        }
        dao.save(data);
    }

    /** {@inheritDoc} */
    @Override
    public Optional<Paciente> getById(@NonNull Integer id) {
        return dao.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    public void delete(@NonNull Integer id) throws DataNotFoundException {
        if (!dao.existsById(id)) {
            throw new DataNotFoundException(String.format("Paciente não encontrado. Id: %d", id));
        }
        dao.deleteById(id);
    }
}
