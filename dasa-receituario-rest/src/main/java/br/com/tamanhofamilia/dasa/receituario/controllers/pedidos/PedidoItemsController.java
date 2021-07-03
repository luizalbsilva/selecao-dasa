package br.com.tamanhofamilia.dasa.receituario.controllers.pedidos;

import br.com.tamanhofamilia.dasa.receituario.controllers.PageableHelper;
import br.com.tamanhofamilia.dasa.receituario.models.pedidos.Pedido;
import br.com.tamanhofamilia.dasa.receituario.models.pedidos.PedidoItem;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import br.com.tamanhofamilia.dasa.receituario.services.pedidos.IPedidoItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

@RestController
@RequestMapping(PedidoItemsController.URL_BASE)
public class PedidoItemsController {
    public static final String URL_BASE = "/api/v1/pedidos";
    private IPedidoItemsService pedidoItemsService;

    @Autowired
    public PedidoItemsController(IPedidoItemsService pedidoItemsService) {
        this.pedidoItemsService = pedidoItemsService;
    }

    @GetMapping("/{idPedido}/items")
    public Page<PedidoItem> readAll(
            @PathVariable("idPedido") int idPedido,
            @Param("pgInit") Optional<Integer> startPage, @Param("pgFim") Optional<Integer> endPage, @Param("sortBy") Optional<String> sortField) {

        Pageable pageable = PageableHelper.create(startPage, endPage, sortField);
        return this.pedidoItemsService.findAllFromPedido(idPedido, pageable);
    }

    @PostMapping("/{idPedido}/items")
    public ResponseEntity<Void> create(
            @PathVariable("idPedido") int idPedido,
            @Valid @RequestBody PedidoItem pedidoItem) {
        if (pedidoItem.getPedido() == null) pedidoItem.setPedido(new Pedido());
        pedidoItem.getPedido().setIdPedido(idPedido);

        var id = pedidoItemsService.create(pedidoItem);
        return ResponseEntity.created(URI.create(String.format("%s/%s", URL_BASE, id))).build();
    }

    @PutMapping("/{idPedido}/items/{id}")
    public ResponseEntity<Object> update(
            @PathVariable("id") long id, @Valid @RequestBody PedidoItem pedidoItem) {
        pedidoItem.setIdPedidoItem(id);
        try {
            pedidoItemsService.update(pedidoItem);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    @GetMapping("/{idPedido}/items/{id}")
    public ResponseEntity<Object> get(
            @PathVariable("id") long id) {
        final Optional<PedidoItem> pedidoItem = pedidoItemsService.getById(id);
        if (pedidoItem.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidoItem.get());
    }

    @DeleteMapping("/{idPedido}/items/{id}")
    public ResponseEntity<Object> delete(
            @PathVariable("id") long id) {
        try {
            pedidoItemsService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }
}
