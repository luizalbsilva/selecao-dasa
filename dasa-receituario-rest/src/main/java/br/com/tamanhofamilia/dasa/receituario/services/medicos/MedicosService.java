package br.com.tamanhofamilia.dasa.receituario.services.medicos;

import br.com.tamanhofamilia.dasa.receituario.daos.medico.ConselhoDao;
import br.com.tamanhofamilia.dasa.receituario.daos.medico.MedicoDao;
import br.com.tamanhofamilia.dasa.receituario.models.medico.Medico;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Serviços de médicos
 */
@Service
public class MedicosService implements IMedicosService {
    /** Dao de acesso aos dados de médicos */
    private final MedicoDao medicoDao;

    @Autowired
    public MedicosService(MedicoDao medicoDao) {
        this.medicoDao = medicoDao;
    }

    /** {@inheritDoc} */
    @Override
    public Page<Medico> findAll(Pageable pageable) {
        return medicoDao.findAll(pageable);
    }

    /** {@inheritDoc} */
    @Override
    public @NonNull Integer create(Medico data) {
        final Medico saved = medicoDao.save(data);
        return saved.getIdMedico();
    }

    /** {@inheritDoc} */
    @Override
    public void update(Medico data) throws DataNotFoundException {
        if (!medicoDao.existsById(data.getIdMedico())) {
            throw new DataNotFoundException(String.format("Medico não encontrado. Id: %d", data.getIdMedico()));
        }

        medicoDao.save(data);
    }

    /** {@inheritDoc} */
    @Override
    public Optional<Medico> getById(@NonNull Integer id) {
        return medicoDao.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    public void delete(@NonNull Integer id) throws DataNotFoundException {
        if (!medicoDao.existsById(id)) {
            throw new DataNotFoundException(String.format("Medico não encontrado. Id: %d", id));
        }
        medicoDao.deleteById(id);
    }
}
