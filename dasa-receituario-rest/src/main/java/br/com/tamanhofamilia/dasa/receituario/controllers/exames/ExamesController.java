package br.com.tamanhofamilia.dasa.receituario.controllers.exames;

import br.com.tamanhofamilia.dasa.receituario.controllers.PageableHelper;
import br.com.tamanhofamilia.dasa.receituario.models.exame.Exame;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import br.com.tamanhofamilia.dasa.receituario.services.exames.IExamesService;
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
 * Controller de exames
 */
@Api(
        consumes = "application/json",
        produces = "application/json",
        value = "Manipulação dos dados de Exames"
)
@RestController
@RequestMapping(ExamesController.URL_BASE)
public class ExamesController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExamesController.class);
    public static final String URL_BASE = "/api/v1/exames";

    /** Serviço de exames */
    private IExamesService examesService;

    @Autowired
    public ExamesController(IExamesService examesService) {
        this.examesService = examesService;
    }

    /**
     * lista exames do sistema
     * @param startPage Página inicial
     * @param pgSize Itens por página
     * @param sortField Campo de ordenação
     * @return Lista dos exames no sistema
     */
    @ApiOperation("Lista os exames disponíveis no sistema")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de exames"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public Page<Exame> readAll(
            @ApiParam("Página inicial")
            @Param("pgInit") Optional<Integer> startPage,
            @ApiParam("Página final")
            @Param("pgSize") Optional<Integer> pgSize,
            @ApiParam("Campo de ordem")
            @Param("sortBy") Optional<String> sortField) {
        LOGGER.trace("Pesquisa de Exames");
        var pageable = PageableHelper.create(startPage, pgSize, sortField);
        return this.examesService.findAll(pageable);
    }

    /**
     * Cria novo exame
     * @param exame Exame a ser criado
     * @return ID através de Location
     */
    @ApiOperation("Cria novo exame")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Recurso criado"),
            @ApiResponse(code = 400, message = "Erro na estrutura / dados enviados"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Exame exame) {
        LOGGER.trace("Criando Exame: {}", exame);
        var id = examesService.create(exame);
        LOGGER.debug("Criado ID {} para o Exame: {}", id, exame);
        return ResponseEntity.created(URI.create(String.format("%s/%s", URL_BASE, id))).build();
    }

    /**
     * Altera os dados do exame
     * @param id Identificador do exame
     * @param exame Novos dados
     * @return Status relativos ao sucesso (204)
     */
    @ApiOperation("Altera um determinado exame")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Dados alterados"),
            @ApiResponse(code = 400, message = "Erro na estrutura / dados enviados"),
            @ApiResponse(code = 412, message = "Não foi encontrado o registro para ser alterado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @ApiParam("Identificador do Exame")
            @PathVariable("id") int id,
            @Valid @RequestBody Exame exame) {
        exame.setIdExame(id);
        LOGGER.debug("Alterando Exame : {}", exame);
        try {
            examesService.update(exame);
            LOGGER.debug("Exame alterado com sucesso: {}", exame);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            LOGGER.error("Registro não encontrado para alteração. {}", exame);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    /**
     * Recupera os dados do exame
     * @param id Identificador do Exame
     * @return Dados do exame
     */
    @ApiOperation("Recupera os dados de um exame")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o exame requisitado"),
            @ApiResponse(code = 204, message = "Dado não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> get(
            @ApiParam("Identificador do Exame")
            @PathVariable("id") int id) {
        LOGGER.trace("Requisitando exame #{}", id);
        final Optional<Exame> exame = examesService.getById(id);
        if (exame.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(exame.get());
    }

    /**
     * Apaga os dados do exame
     * @param id Identificador do exame
     * @return status 204 (sucesso)
     */
    @ApiOperation("Apaga exame")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Dados apagados"),
            @ApiResponse(code = 400, message = "Erro na estrutura / dados enviados"),
            @ApiResponse(code = 412, message = "Não foi encontrado o registro para ser alterado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(
            @ApiParam("Identificador do exame")
            @PathVariable("id") int id) {
        try {
            examesService.delete(id);
            LOGGER.debug("Exame excluido com sucesso: {}", id);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            LOGGER.error("Exame não existe: {}", id);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }
}
