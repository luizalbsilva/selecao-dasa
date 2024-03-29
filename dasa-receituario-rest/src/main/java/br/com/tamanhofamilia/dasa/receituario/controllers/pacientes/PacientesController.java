package br.com.tamanhofamilia.dasa.receituario.controllers.pacientes;

import br.com.tamanhofamilia.dasa.receituario.controllers.PageableHelper;
import br.com.tamanhofamilia.dasa.receituario.models.paciente.Paciente;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import br.com.tamanhofamilia.dasa.receituario.services.pacientes.IPacientesService;
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
 * Controller de pacientes
 */
@Api(
        consumes = "application/json",
        produces = "application/json"
)
@RestController
@RequestMapping(PacientesController.URL_BASE)
public class PacientesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PacientesController.class);
    public static final String URL_BASE = "/api/v1/pacientes";

    /** Serviço de pacientes */
    private IPacientesService pacientesService;

    @Autowired
    PacientesController(IPacientesService pacientesService) {
        this.pacientesService = pacientesService;
    }

    /**
     * Lista todos os pacientes no sistema
     * @param startPage Página inicial
     * @param pgSize Tamanho da página
     * @param sortField Campo de ordenação
     * @return Lista dos pacientes, status 200
     */
    @ApiOperation("Lista os dados de pacientes disponíveis no sistema")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de pacientes"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public Page<Paciente> readAll(
            @ApiParam("Página inicial")
            @Param("pgInit") Optional<Integer> startPage,
            @ApiParam("Itens por página")
            @Param("pgSize") Optional<Integer> pgSize,
            @ApiParam("Campo de ordem")
            @Param("sortBy") Optional<String> sortField) {
        LOGGER.trace("Buscando todos os pacientes");
        var pageable = PageableHelper.create(startPage, pgSize, sortField);
        return this.pacientesService.findAll(pageable);
    }

    /**
     * Cria novo paciente
     * @param pacienteDto Dados do paciente
     * @return status 201 + Location com localização do registro
     */
    @ApiOperation("Cria novo paciente")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Recurso criado"),
            @ApiResponse(code = 400, message = "Erro na estrutura / dados enviados"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Paciente pacienteDto) {
        LOGGER.trace("Criando paciente: {}", pacienteDto);

        var id = pacientesService.create(pacienteDto);
        LOGGER.debug("Criado id {} para o paciente: {}", id, pacienteDto);
        return ResponseEntity.created(URI.create(String.format("%s/%s", URL_BASE, id))).build();
    }

    /**
     * Altera dados do paciente
     * @param id Identificador do registro
     * @param pacienteDto Dados do paciente
     * @return Status 204 (sucesso), 412 (registro não encontrado)
     */
    @ApiOperation("Altera um determinado paciente")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Dados alterados"),
            @ApiResponse(code = 400, message = "Erro na estrutura / dados enviados"),
            @ApiResponse(code = 412, message = "Não foi encontrado o registro para ser alterado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @ApiParam("Identificador do Paciente")
            @PathVariable("id") int id, @Valid @RequestBody Paciente pacienteDto) {
        pacienteDto.setIdPaciente(id);
        LOGGER.trace("Alterando Paciente: {}", pacienteDto);
        try {
            pacientesService.update(pacienteDto);
            LOGGER.debug("Alterado Paciente: {}", pacienteDto);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            LOGGER.error("Paciente não encontrado para alteração: {}", pacienteDto, e);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    /**
     * Recupera os dados de um paciente
     * @param id Identificador do registro
     * @return Dados do paciente, status 200
     */
    @ApiOperation("Recupera os dados de um paciente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o exame requisitado"),
            @ApiResponse(code = 204, message = "Dado não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> get(
            @ApiParam("Identificador do Paciente")
            @PathVariable("id") int id) {
        LOGGER.trace("Busca Paciente: {}", id);

        final Optional<Paciente> paciente = pacientesService.getById(id);
        if (paciente.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paciente.get());
    }

    /**
     * Apaga os dados do paciente
     * @param id Identificador do paciente
     * @return status 204 (sucesso), 412( Registro não encontrado )
     */
    @ApiOperation("Apaga dados de paciente")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Dados apagados"),
            @ApiResponse(code = 400, message = "Erro na estrutura / dados enviados"),
            @ApiResponse(code = 412, message = "Não foi encontrado o registro para ser alterado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(
            @ApiParam("Identificador do Paciente")
            @PathVariable("id") int id) {
        LOGGER.trace("Apagando Paciente: {}", id);

        try {
            pacientesService.delete(id);
            LOGGER.debug("Apagado Paciente: {}", id);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            LOGGER.error("Paciente não encontrado para exclusão: {}", id, e);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }
}
