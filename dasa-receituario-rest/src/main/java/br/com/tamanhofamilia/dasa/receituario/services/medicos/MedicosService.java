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
    /** Dao de acesso aos dados de conselho */
    private final ConselhoDao conselhoDao;

    @Autowired
    public MedicosService(MedicoDao medicoDao, ConselhoDao conselhoDao) {
        this.medicoDao = medicoDao;
        this.conselhoDao = conselhoDao;
    }

    /** {@inheritDoc} */
    @Override
    public Page<Medico> findAll(Pageable pageable) {
        return medicoDao.findAll(pageable);
    }

    /** {@inheritDoc} */
    @Override
    public @NonNull Integer create(Medico medico) {
        checaDadosRelacionados(medico);
        final Medico saved = medicoDao.save(medico);
        return saved.getIdMedico();
    }

    private void checaDadosRelacionados(Medico data) {
        if (! conselhoDao.existsById(data.getConselho().getIdConselho())) {
            throw new DataNotFoundException(String.format("Conselho não encontrado: ", data.getConselho().getIdConselho()));
        }
    }

    /** {@inheritDoc} */
    @Override
    public void update(Medico medico) throws DataNotFoundException {
        checaDadosRelacionados(medico);
        if (!medicoDao.existsById(medico.getIdMedico())) {
            throw new DataNotFoundException(String.format("Medico não encontrado. Id: %d", medico.getIdMedico()));
        }

        medicoDao.save(medico);
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
