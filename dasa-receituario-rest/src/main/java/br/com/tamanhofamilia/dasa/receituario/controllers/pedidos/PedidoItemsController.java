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
        value = "Manipulação dos dados de Itens de Pedido"
)
@RestController
@RequestMapping(PedidoItemsController.URL_BASE)
public class PedidoItemsController {
    public static final String URL_BASE = "/api/v1/pedidos";
    private IPedidoItemsService pedidoItemsService;

    @Autowired
    public PedidoItemsController(IPedidoItemsService pedidoItemsService) {
        this.pedidoItemsService = pedidoItemsService;
    }

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
            @ApiParam("Página final")
            @Param("pgFim") Optional<Integer> endPage,
            @ApiParam("Campo de ordem")
            @Param("sortBy") Optional<String> sortField) {

        var pageable = PageableHelper.create(startPage, endPage, sortField);
        return this.pedidoItemsService.findAllFromPedido(idPedido, pageable);
    }

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
        if (pedidoItem.getPedido() == null) pedidoItem.setPedido(new Pedido());
        pedidoItem.getPedido().setIdPedido(idPedido);

        var id = pedidoItemsService.create(pedidoItem);
        return ResponseEntity.created(URI.create(String.format("%s/%s", URL_BASE, id))).build();
    }

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
        pedidoItem.setIdPedidoItem(id);
        try {
            pedidoItemsService.update(pedidoItem);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    @ApiOperation("Recupera os dados de um item de pedido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o exame requisitado"),
            @ApiResponse(code = 204, message = "Dado não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{idPedido}/items/{id}")
    public ResponseEntity<Object> get(
            @ApiParam("Identificador do Item do pedido")
            @PathVariable("id") long id) {
        final Optional<PedidoItem> pedidoItem = pedidoItemsService.getById(id);
        if (pedidoItem.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidoItem.get());
    }

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
        try {
            pedidoItemsService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }
}
