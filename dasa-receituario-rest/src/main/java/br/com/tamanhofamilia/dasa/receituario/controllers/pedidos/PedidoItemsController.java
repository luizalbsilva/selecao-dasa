package br.com.tamanhofamilia.dasa.receituario.controllers.pedidos;

import br.com.tamanhofamilia.dasa.receituario.controllers.PageableHelper;
import br.com.tamanhofamilia.dasa.receituario.models.pedidos.Pedido;
import br.com.tamanhofamilia.dasa.receituario.models.pedidos.PedidoItem;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import br.com.tamanhofamilia.dasa.receituario.services.pedidos.IPedidoItemsService;
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
 * Controller de Itens de Pedido
 */
@Api(
        consumes = "application/json",
        produces = "application/json",
        value = "Manipulação dos dados de Itens de Pedido"
)
@RestController
@RequestMapping(PedidoItemsController.URL_BASE)
public class PedidoItemsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoItemsController.class);
    public static final String URL_BASE = "/api/v1/pedidos";

    /** Serviço de Itens de Pedido */
    private IPedidoItemsService pedidoItemsService;

    @Autowired
    public PedidoItemsController(IPedidoItemsService pedidoItemsService) {
        this.pedidoItemsService = pedidoItemsService;
    }

    /**
     * Lista os Itens de Pedido
     * @param idPedido Identificador do pedido
     * @param startPage Página inicial
     * @param pgSize Itens por página
     * @param sortField Campo de ordenação
     * @return Lista dos Itens do pedido. Status 200.
     */
    @ApiOperation("Lista os itens de pedido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de Itens de um determinado pedido"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{idPedido}/items")
    public Page<PedidoItem> readAll(
            @ApiParam("Identificador do pedido")
            @PathVariable("idPedido") int idPedido,
            @ApiParam("Página inicial")
            @Param("pgInit") Optional<Integer> startPage,
            @ApiParam("Itens por página")
            @Param("pgSize") Optional<Integer> pgSize,
            @ApiParam("Campo de ordem")
            @Param("sortBy") Optional<String> sortField) {
        LOGGER.trace("Listando todos os itens de pedido");
        var pageable = PageableHelper.create(startPage, pgSize, sortField);
        return this.pedidoItemsService.findAllFromPedido(idPedido, pageable);
    }

    /**
     * Cria novo Item do Pedido
     * @param idPedido Identificador do pedido
     * @param pedidoItem Dados o item do pedido
     * @return Status 201 + location do registro
     */
    @ApiOperation("Cria novo item de pedido")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Recurso criado"),
            @ApiResponse(code = 400, message = "Erro na estrutura / dados enviados"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping("/{idPedido}/items")
    public ResponseEntity<Void> create(
            @ApiParam("Identificador do pedido")
            @PathVariable("idPedido") int idPedido,
            @Valid @RequestBody PedidoItem pedidoItem) {
        LOGGER.trace("Criando para o pedido {}, Item do Pedido {}", idPedido, pedidoItem);
        if (pedidoItem.getPedido() == null) pedidoItem.setPedido(new Pedido());
        pedidoItem.getPedido().setIdPedido(idPedido);

        try {
            var id = pedidoItemsService.create(pedidoItem);
            LOGGER.debug("Criado id {} para Item do Pedido {}", id, pedidoItem);
            return ResponseEntity.created(URI.create(String.format("%s/%s/items/%s", URL_BASE, idPedido, id))).build();
        } catch (DataNotFoundException e) {
            LOGGER.error("Faltando dados relacionados com tabela. Dados: {}", pedidoItem, e);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    /**
     * Altera os dados de um Item de pedido
     * @param id Identificador do registro
     * @param pedidoItem Dados do item do pedido
     * @return status: 204 (sucesso), 412 (registro não encontrado)
     */
    @ApiOperation("Altera um determinado item de pedido")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Dados alterados"),
            @ApiResponse(code = 400, message = "Erro na estrutura / dados enviados"),
            @ApiResponse(code = 412, message = "Não foi encontrado o registro para ser alterado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PutMapping("/{idPedido}/items/{id}")
    public ResponseEntity<Object> update(
            @ApiParam("identificador do item")
            @PathVariable("id") long id,
            @Valid @RequestBody PedidoItem pedidoItem) {
        LOGGER.trace("Alterando o Item do Pedido {}", pedidoItem);
        pedidoItem.setIdPedidoItem(id);
        try {
            pedidoItemsService.update(pedidoItem);
            LOGGER.trace("Alterado o Item do Pedido {}", pedidoItem);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            LOGGER.error("Item do Pedido não encontrado para alteração: {}", pedidoItem, e);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    /**
     * Recupera os dados do Item do Pedido
     * @param id Identificador do Registro
     * @return Dados do Item do Pedido, status 200 (204 se náo houver dado)
     */
    @ApiOperation("Recupera os dados de um item de pedido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o item de pedido requisitado"),
            @ApiResponse(code = 204, message = "Dado não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{idPedido}/items/{id}")
    public ResponseEntity<Object> get(
            @ApiParam("Identificador do Item do pedido")
            @PathVariable("id") long id) {
        LOGGER.trace("Procurando pelo Item do Pedido {}", id);
        final Optional<PedidoItem> pedidoItem = pedidoItemsService.getById(id);
        if (pedidoItem.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidoItem.get());
    }

    /**
     * Apaga os dados do item de pedido
     * @param id Identificador do registro
     * @return 204 (sucesso), 412 (registro não encontrado)
     */
    @ApiOperation("Apaga um item de pedido")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Dados apagados"),
            @ApiResponse(code = 400, message = "Erro na estrutura / dados enviados"),
            @ApiResponse(code = 412, message = "Não foi encontrado o registro para ser alterado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/{idPedido}/items/{id}")
    public ResponseEntity<Object> delete(
            @ApiParam("Identificador do Item do Pedido")
            @PathVariable("id") long id) {
        LOGGER.trace("Apagando Item do Pedido {}", id);
        try {
            pedidoItemsService.delete(id);
            LOGGER.debug("Apagando Item do Pedido {}", id);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            LOGGER.error("Item do Pedido não encontrado para exclusão: {}", id, e);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }
}
