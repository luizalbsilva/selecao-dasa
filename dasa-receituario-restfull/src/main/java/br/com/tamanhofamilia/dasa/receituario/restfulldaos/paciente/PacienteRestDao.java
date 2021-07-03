package br.com.tamanhofamilia.dasa.receituario.restfulldaos.paciente;

import br.com.tamanhofamilia.dasa.receituario.daos.paciente.PacienteDao;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "pacientes", collectionResourceRel = "pacientes")
public interface PacienteRestDao extends PacienteDao {
}
