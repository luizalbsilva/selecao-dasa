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

@Service
public class MedicosService implements IMedicosService {
    private final MedicoDao medicoDao;
    private final ConselhoDao conselhoDao;

    @Autowired
    public MedicosService(MedicoDao medicoDao, ConselhoDao conselhoDao) {
        this.medicoDao = medicoDao;
        this.conselhoDao = conselhoDao;
    }

    @Override
    public Page<Medico> findAll(Pageable pageable) {
        return medicoDao.findAll(pageable);
    }

    @Override
    public @NonNull Integer create(Medico medico) {
        loadData(medico);
        final Medico saved = medicoDao.save(medico);
        return saved.getIdMedico();
    }

    private void loadData(Medico medico) {
        if (medico.getConselho() != null && medico.getConselho().getIdConselho() != null) {
            conselhoDao.findById(medico.getConselho().getIdConselho())
                    .ifPresent(medico::setConselho);
        }
    }

    @Override
    public void update(Medico medico) throws DataNotFoundException {
        if ( !medicoDao.existsById(medico.getIdMedico()) ) {
            throw new DataNotFoundException(String.format("Medico não encontrado. Id: %d", medico.getIdMedico()) );
        }

        loadData(medico);
        medicoDao.save(medico);
    }

    @Override
    public Optional<Medico> getById(@NonNull Integer id) {
        return medicoDao.findById(id);
    }

    @Override
    public void delete(@NonNull Integer id) throws DataNotFoundException {
        if (!medicoDao.existsById(id)) {
            throw new DataNotFoundException(String.format("Medico não encontrado. Id: %d", id) );
        }
        medicoDao.deleteById(id);
    }
}
