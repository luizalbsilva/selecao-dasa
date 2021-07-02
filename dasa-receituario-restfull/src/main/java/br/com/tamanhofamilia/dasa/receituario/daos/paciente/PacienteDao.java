package br.com.tamanhofamilia.dasa.receituario.daos.paciente;

import br.com.tamanhofamilia.dasa.receituario.models.paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "pacientes", collectionResourceRel = "pacientes")
public interface PacienteDao extends JpaRepository<Paciente, Long> {
}
