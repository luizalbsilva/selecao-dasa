package br.com.tamanhofamilia.dasa.receituario.controllers.medicos;

import br.com.tamanhofamilia.dasa.receituario.controllers.PageableHelper;
import br.com.tamanhofamilia.dasa.receituario.models.medico.Medico;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import br.com.tamanhofamilia.dasa.receituario.services.medicos.IMedicosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

/**
 * Controller de médicos
 */
@Api(
        consumes = "application/json",
        produces = "application/json",
        value = "Manipulação dos dados de Médicos"
)
@RestController
@RequestMapping(MedicosController.URL_BASE)
public class MedicosController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicosController.class);
    public static final String URL_BASE = "/api/v1/medicos";

    /** Serviço de médicos */
    private IMedicosService service;

    @Autowired
    public MedicosController(IMedicosService medicosService) {
        this.service = medicosService;
    }

    /**
     * Lista os médicos disponíveis no sistema
     * @param startPage Página inicial
     * @param pgSize Itens por página
     * @param sortField Campo de ordenação
     * @return Lista dos médicos, status 200
     */
    @ApiOperation("Lista os médicos disponíveis no sistema")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de médicos"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public Page<Medico> readAll(
            @ApiParam("Página inicial")
            @Param("pgInit") Optional<Integer> startPage,
            @ApiParam("Itens por página")
            @Param("pgSize") Optional<Integer> pgSize,
            @ApiParam("Campo de ordem")
            @Param("sortBy") Optional<String> sortField) {
        LOGGER.trace("Recuperando todos os médicos");

        var pageable = PageableHelper.create(startPage, pgSize, sortField);
        return this.service.findAll(pageable);
    }

    /**
     * Cria novo registro de médico
     * @param medico Dados do médico
     * @return Location do registro, status 201
     */
    @ApiOperation("Cria novo médico")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Recurso criado"),
            @ApiResponse(code = 400, message = "Erro na estrutura / dados enviados"),
            @ApiResponse(code = 412, message = "Dado faltando em tabela relacionada"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Medico medico) {
        LOGGER.trace("Criando médico: {}", medico);

        try {
            var id = service.create(medico);
            LOGGER.debug("Criado id {} para médico: {}", id, medico);
            return ResponseEntity.created(URI.create(String.format("%s/%s", URL_BASE, id))).build();
        } catch (DataNotFoundException e) {
            LOGGER.error("Identificador de Conselho não encontrado. Dados: {}", medico, e);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    /**
     * Altera dados de um médico
     * @param id Identificador do médico
     * @param medico Dados do médico
     * @return status 204 se for alterado, 412 se não for encontrado
     */
    @ApiOperation("Altera um determinado médico")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Dados alterados"),
            @ApiResponse(code = 400, message = "Erro na estrutura / dados enviados"),
            @ApiResponse(code = 412, message = "Não foi encontrado o registro para ser alterado ou em tabela relacionada"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @ApiParam("Identificador do Médico")
            @PathVariable("id") int id,
            @Valid @RequestBody Medico medico) {
        medico.setIdMedico(id);
        LOGGER.trace("Alterando médico: {}", medico);
        try {
            service.update(medico);
            LOGGER.debug("Alterado médico: {}", medico);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            LOGGER.error("Médico não encontrado para alteração: {}", medico, e);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    /**
     * Recupera dados de médico
     * @param id Identificador de medico
     * @return Dados do médico, status 200
     */
    @ApiOperation("Recupera os dados de um médico")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o médico requisitado"),
            @ApiResponse(code = 204, message = "Dado não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> get(
            @ApiParam("Identificador do Médico")
            @PathVariable("id") int id) {
        final Optional<Medico> medico = service.getById(id);
        if (medico.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(medico.get());
    }

    /**
     * Apaga os dados do médico
     * @param id Identificador do registro
     * @return status 204(Sucesso), 412 se não for encontrado
     */
    @ApiOperation("Apaga registro de um médico")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Dados apagados"),
            @ApiResponse(code = 400, message = "Erro na estrutura / dados enviados"),
            @ApiResponse(code = 412, message = "Não foi encontrado o registro para ser alterado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(
            @ApiParam("Identificador do Médico")
            @PathVariable("id") int id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }
}
