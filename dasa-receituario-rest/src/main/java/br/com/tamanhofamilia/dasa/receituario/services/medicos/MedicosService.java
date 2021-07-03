package br.com.tamanhofamilia.dasa.receituario.services.medicos;

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
    private final MedicoDao dao;

    @Autowired
    public MedicosService(MedicoDao dao) {
        this.dao = dao;
    }

    @Override
    public Page<Medico> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    public @NonNull Integer create(Medico medico) {
        final Medico saved = dao.save(medico);
        return saved.getIdMedico();
    }

    @Override
    public void update(Medico medico) throws DataNotFoundException {
        if ( !dao.existsById(medico.getIdMedico()) ) {
            throw new DataNotFoundException(String.format("Medico não encontrado. Id: %d", medico.getIdMedico()) );
        }
        dao.save(medico);
    }

    @Override
    public Optional<Medico> getById(@NonNull Integer id) {
        return dao.findById(id);
    }

    @Override
    public void delete(@NonNull Integer id) throws DataNotFoundException {
        if (!dao.existsById(id)) {
            throw new DataNotFoundException(String.format("Medico não encontrado. Id: %d", id) );
        }
        dao.deleteById(id);
    }
}
