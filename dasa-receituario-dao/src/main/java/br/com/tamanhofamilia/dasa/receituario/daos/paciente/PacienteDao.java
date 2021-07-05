package br.com.tamanhofamilia.dasa.receituario.daos.paciente;

import br.com.tamanhofamilia.dasa.receituario.models.paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface que define acesso aos dados de Paciente
 * @see Paciente
 */
@Repository
public interface PacienteDao extends JpaRepository<Paciente, Integer> {
}
