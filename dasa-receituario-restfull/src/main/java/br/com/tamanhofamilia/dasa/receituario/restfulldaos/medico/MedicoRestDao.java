package br.com.tamanhofamilia.dasa.receituario.restfulldaos.medico;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "medicos", collectionResourceRel = "medicos")
public interface MedicoRestDao extends br.com.tamanhofamilia.dasa.receituario.daos.medico.MedicoDao {
}
