package br.com.tamanhofamilia.dasa.receituario.controllers.medicos;

import br.com.tamanhofamilia.dasa.receituario.controllers.PageableHelper;
import br.com.tamanhofamilia.dasa.receituario.models.medico.Conselho;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import br.com.tamanhofamilia.dasa.receituario.services.medicos.IConselhosService;
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
 * Controller de conselhos
 */
@Api(
        consumes = "application/json",
        produces = "application/json",
        value = "Manipulação dos dados de conselhos profissionais"
)
@RestController
@RequestMapping(ConselhosController.URL_BASE)
public class ConselhosController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConselhosController.class);
    public static final String URL_BASE = "/api/v1/conselhos";
    /** Serviço de conselho */
    private IConselhosService service;

    @Autowired
    public ConselhosController(IConselhosService conselhosService) {
        this.service = conselhosService;
    }

    /**
     * Lista conselhos disponíveis no sistema
     * @param startPage Página inicial
     * @param pgSize Itens por página
     * @param sortField Campo de Ordenação
     * @return Lista de conselhos, status 200
     */
    @ApiOperation("Lista os Conselhos disponíveis no sistema")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de conselhos profissionais"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public Page<Conselho> readAll(
            @ApiParam("Página inicial")
            @Param("pgInit") Optional<Integer> startPage,
            @ApiParam("Itens por página")
            @Param("pgSize") Optional<Integer> pgSize,
            @ApiParam("Campo de ordem")
            @Param("sortBy") Optional<String> sortField) {
        LOGGER.trace("Busca por todos os conselhos profissionais");
        var pageable = PageableHelper.create(startPage, pgSize, sortField);
        return this.service.findAll(pageable);
    }

    /**
     * Cria novo conselho
     * @param conselho Dados do conselho profissional
     * @return Localicacao do registro, status 201
     */
    @ApiOperation("Cria novo conselho profissional")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Recurso criado"),
            @ApiResponse(code = 400, message = "Erro na estrutura / dados enviados"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Conselho conselho) {
        LOGGER.trace("Criando conselho: {}", conselho);
        var id = service.create(conselho);
        LOGGER.debug("Criado Id {} conselho: {}", id, conselho);
        return ResponseEntity.created(URI.create(String.format("%s/%s", URL_BASE, id))).build();
    }

    /**
     * Altera dados do conselho profissional
     * @param id Identifiador do registrp
     * @param conselho Dados do conselho
     * @return status 204
     */
    @ApiOperation("Altera um determinado conselho profissional")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Dados alterados"),
            @ApiResponse(code = 400, message = "Erro na estrutura / dados enviados"),
            @ApiResponse(code = 412, message = "Não foi encontrado o registro para ser alterado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @ApiParam("Identificador do Conselho Profissional")
            @PathVariable("id") int id,
            @Valid @RequestBody Conselho conselho) {
        conselho.setIdConselho(id);
        LOGGER.trace("Alterando conselho: {}", conselho);
        try {
            service.update(conselho);
            LOGGER.debug("Conselho trabalhista alterado: {}", conselho);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            LOGGER.error("Conselho trabalhista não encontrado: {}", conselho);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    /**
     * Recupera dados do conselho profissional
     * @param id Identificador do registro
     * @return Status 200 + Dados. 204 se não for encontrado.
     */
    @ApiOperation("Recupera os dados de um conselho profissional")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o Conselho Profissional requisitado"),
            @ApiResponse(code = 204, message = "Dado não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> get(
            @ApiParam("Identificador do Conselho Profissional")
            @PathVariable("id") int id) {
        LOGGER.trace("Buscando conselho: {}", id);

        final Optional<Conselho> conselho = service.getById(id);
        if (conselho.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(conselho.get());
    }

    /**
     * Apaga os dados do conselho profissional
     * @param id Identificador do registro
     * @return 204 se forem excluídos, 412 se não forem encontrados.
     */
    @ApiOperation("Apaga o Conselho Profissional")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Dados apagados"),
            @ApiResponse(code = 400, message = "Erro na estrutura / dados enviados"),
            @ApiResponse(code = 412, message = "Não foi encontrado o registro para ser alterado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(
            @ApiParam("Identificador do Conselho Profissional")
            @PathVariable("id") int id) {
        LOGGER.trace("Apagando conselho: {}", id);
        try {
            service.delete(id);
            LOGGER.debug("Gravando conselho: {}", id);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            LOGGER.error("Conselho não encontrado para exclusao: {}", id);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }
}
