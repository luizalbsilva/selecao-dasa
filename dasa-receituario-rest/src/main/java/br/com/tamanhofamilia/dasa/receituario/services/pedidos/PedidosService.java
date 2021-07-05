package br.com.tamanhofamilia.dasa.receituario.services.pedidos;

import br.com.tamanhofamilia.dasa.receituario.daos.medico.MedicoDao;
import br.com.tamanhofamilia.dasa.receituario.daos.paciente.PacienteDao;
import br.com.tamanhofamilia.dasa.receituario.daos.pedidos.PedidoDao;
import br.com.tamanhofamilia.dasa.receituario.models.pedidos.Pedido;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** Serviço de pedidos */
@Service
public class PedidosService implements IPedidosService {
    /** Dao de acesso aos dados de Pedido */
    private final PedidoDao pedidoDao;
    /** Dao de acesso aos dados de Pacientes */
    private final PacienteDao pacienteDao;
    /** Dao de acesso aos dados de Médicos */
    private final MedicoDao medicoDao;

    @Autowired
    PedidosService(PedidoDao dao, PacienteDao pacienteDao, MedicoDao medicoDao) {
        this.pedidoDao = dao;
        this.pacienteDao = pacienteDao;
        this.medicoDao = medicoDao;
    }

    /** {@inheritDoc */
    @Override
    public Page<Pedido> findAll(Pageable pageable) {
        return pedidoDao.findAll(pageable);
    }

    /** {@inheritDoc */
    @Override
    public Integer create(Pedido data) {
        checaDadosRelacionados(data);
        final var saved = pedidoDao.save(data);
        return saved.getIdPedido();
    }

    /** {@inheritDoc */
    @Override
    public void update(Pedido data) throws DataNotFoundException {
        checaDadosRelacionados(data);
        if ( !pedidoDao.existsById(data.getIdPedido()) ) {
            throw new DataNotFoundException(String.format("Pedido não encontrado. Id: %d", data.getIdPedido()) );
        }
        pedidoDao.save(data);
    }

    private void checaDadosRelacionados(Pedido data) {
        if (! medicoDao.existsById(data.getMedico().getIdMedico())){
            throw new DataNotFoundException(
                String.format("Dado de médico não encontrado.: %d. %s", data.getMedico().getIdMedico(), data)
            );
        }
        if (! pacienteDao.existsById(data.getPaciente().getIdPaciente())){
            throw new DataNotFoundException(
                String.format("Dado de paciente não encontrado.: %d. %s", data.getPaciente().getIdPaciente(), data)
            );
        }
    }

    /** {@inheritDoc */
    @Override
    public Optional<Pedido> getById(@NonNull Integer id) {
        return pedidoDao.findById(id);
    }

    /** {@inheritDoc */
    @Override
    public void delete(@NonNull Integer id) throws DataNotFoundException {
        if (!pedidoDao.existsById(id)) {
            throw new DataNotFoundException(String.format("Pedido não encontrado. Id: %d", id) );
        }
        pedidoDao.deleteById(id);
    }
}
