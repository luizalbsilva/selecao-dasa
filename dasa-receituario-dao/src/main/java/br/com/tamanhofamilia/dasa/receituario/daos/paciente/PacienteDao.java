package br.com.tamanhofamilia.dasa.receituario.daos.paciente;

import br.com.tamanhofamilia.dasa.receituario.models.paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteDao extends JpaRepository<Paciente, Integer> {
}
