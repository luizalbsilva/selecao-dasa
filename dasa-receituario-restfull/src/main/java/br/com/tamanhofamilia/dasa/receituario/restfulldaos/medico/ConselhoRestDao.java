package br.com.tamanhofamilia.dasa.receituario.restfulldaos.medico;

import br.com.tamanhofamilia.dasa.receituario.daos.medico.ConselhoDao;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "conselhos", collectionResourceRel = "conselhos")
public interface ConselhoRestDao extends ConselhoDao {
}
