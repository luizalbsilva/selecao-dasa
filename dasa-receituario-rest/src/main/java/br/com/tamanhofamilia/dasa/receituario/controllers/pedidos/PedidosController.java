package br.com.tamanhofamilia.dasa.receituario.controllers.pedidos;

import br.com.tamanhofamilia.dasa.receituario.controllers.PageableHelper;
import br.com.tamanhofamilia.dasa.receituario.models.pedidos.Pedido;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import br.com.tamanhofamilia.dasa.receituario.services.pedidos.IPedidosService;
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

/** Controller de Pedidos */
@Api(
        consumes = "application/json",
        produces = "application/json",
        value = "Manipulação dos dados de Pedidos"
)
@RestController
@RequestMapping(PedidosController.URL_BASE)
public class PedidosController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PedidosController.class);
    public static final String URL_BASE = "/api/v1/pedidos";

    /** Serviço de pedidos */
    private IPedidosService pedidosService;

    @Autowired
    public PedidosController(IPedidosService pedidosService) {
        this.pedidosService = pedidosService;
    }

    /**
     * Lista todos os pedidos do sistema
     * @param startPage Página inicial
     * @param pgSize Itens por página
     * @param sortField Campo de ordenação
     * @return Lista dos pedidos, status 200
     */
    @ApiOperation("Lista os dados de pedidos disponíveis no sistema")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de pedidos"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public Page<Pedido> readAll(
            @ApiParam("Página inicial")
            @Param("pgInit") Optional<Integer> startPage,
            @ApiParam("Itens por página")
            @Param("pgSize") Optional<Integer> pgSize,
            @ApiParam("Campo de ordem")
            @Param("sortBy") Optional<String> sortField) {
        LOGGER.trace("Listando todos os pedidos");
        var pageable = PageableHelper.create(startPage, pgSize, sortField);
        return this.pedidosService.findAll(pageable);
    }

    /**
     * Cria novo pedido
     * @param pedido Dados do pedido
     * @return status 201 + location do registro
     */
    @ApiOperation("Cria novo Pedido")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Recurso criado"),
            @ApiResponse(code = 400, message = "Erro na estrutura / dados enviados"),
            @ApiResponse(code = 412, message = "Dados em tabelas relacionadas inexistente"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Pedido pedido) {
        LOGGER.trace("Criando novo pedido: {}", pedido);
        try {
            var id = pedidosService.create(pedido);
            LOGGER.trace("Criado id {} para o novo pedido: {}", id, pedido);
            return ResponseEntity.created(URI.create(String.format("%s/%s", URL_BASE, id))).build();
        } catch (DataNotFoundException e) {
            LOGGER.error("Dados relacionados com problemas. {}", pedido, e);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    /**
     * Altera os dados do registro
     * @param id Identificador do registro
     * @param pedido Dados do pedido
     * @return status 204 (sucesso), 412 (Registro não encontrado)
     */
    @ApiOperation("Altera um determinado pedido")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Dados alterados"),
            @ApiResponse(code = 400, message = "Erro na estrutura / dados enviados"),
            @ApiResponse(code = 412, message = "Não foi encontrado o registro para ser alterado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @ApiParam("Identificador do Pedido")
            @PathVariable("id") int id, @Valid @RequestBody Pedido pedido) {
        pedido.setIdPedido(id);

        LOGGER.trace("Alterando o pedido: {}", pedido);
        try {
            pedidosService.update(pedido);
            LOGGER.trace("Alterado o pedido: {}", pedido);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            LOGGER.trace("Pedido não encontrado para alteração: {}", pedido, e);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    /**
     * Recupera os dados do pedido
     * @param id Identificador do Registro
     * @return Dados do pedido
     */
    @ApiOperation("Recupera os dados de um pedido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o pedido requisitado"),
            @ApiResponse(code = 204, message = "Dado não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> get(
            @ApiParam("Identificador do Pedido")
            @PathVariable("id") int id) {
        LOGGER.trace("Listando o pedido: {}", id);

        final Optional<Pedido> pedido = pedidosService.getById(id);
        if (pedido.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedido.get());
    }

    /**
     * Apaga o registro
     * @param id Identificador do Registro
     * @return status 204 (Sucesso), 412 ( Registro não encontrado )
     */
    @ApiOperation("Apaga pedido")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Dados apagados"),
            @ApiResponse(code = 400, message = "Erro na estrutura / dados enviados"),
            @ApiResponse(code = 412, message = "Não foi encontrado o registro para ser alterado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(
            @ApiParam("Identificador do Pedido")
            @PathVariable("id") int id) {
        LOGGER.trace("Apagando o pedido: {}", id);

        try {
            pedidosService.delete(id);
            LOGGER.debug("Apagado o pedido: {}", id);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            LOGGER.debug("Pedido não encontrado para exclusão: {}", id, e);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }
}
