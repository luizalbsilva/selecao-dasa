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

@Api(
        consumes = "application/json",
        produces = "application/json",
        value = "Manipulação dos dados de conselhos profissionais"
)
@RestController
@RequestMapping(ConselhosController.URL_BASE)
public class ConselhosController {
    public static final String URL_BASE = "/api/v1/conselhos";
    private IConselhosService service;

    @Autowired
    public ConselhosController(IConselhosService conselhosService) {
        this.service = conselhosService;
    }

    @ApiOperation("Lista os exames disponíveis no sistema")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de conselhos profissionais"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public Page<Conselho> readAll(
            @ApiParam("Página inicial")
            @Param("pgInit") Optional<Integer> startPage,
            @ApiParam("Página final")
            @Param("pgFim") Optional<Integer> endPage,
            @ApiParam("Campo de ordem")
            @Param("sortBy") Optional<String> sortField) {
        var pageable = PageableHelper.create(startPage, endPage, sortField);
        return this.service.findAll(pageable);
    }

    @ApiOperation("Cria novo conselho profissional")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Recurso criado"),
            @ApiResponse(code = 400, message = "Erro na estrutura / dados enviados"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Conselho conselho) {
        var id = service.create(conselho);
        return ResponseEntity.created(URI.create(String.format("%s/%s", URL_BASE, id))).build();
    }

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
        try {
            service.update(conselho);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    @ApiOperation("Recupera os dados de um conselho profissional")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o exame requisitado"),
            @ApiResponse(code = 204, message = "Dado não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> get(
            @ApiParam("Identificador do Conselho Profissional")
            @PathVariable("id") int id) {
        final Optional<Conselho> conselho = service.getById(id);
        if (conselho.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(conselho.get());
    }

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
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }
}
