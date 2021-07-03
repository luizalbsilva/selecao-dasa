package br.com.tamanhofamilia.dasa.receituario.controllers.pedidos;

import br.com.tamanhofamilia.dasa.receituario.models.pedidos.Pedido;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import br.com.tamanhofamilia.dasa.receituario.services.pedidos.IPedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping(PedidosController.URL_BASE)
public class PedidosController {
    public static final String URL_BASE = "/api/v1/pedidos";
    private IPedidosService pedidosService;

    @Autowired
    public PedidosController(IPedidosService pedidosService) {
        this.pedidosService = pedidosService;
    }

    @GetMapping
    public Page<Pedido> readAll(@Param("pgInit") Optional<Integer> startPage, @Param("pgFim") Optional<Integer> endPage, @Param("sortBy") Optional<String> sortField) {

        Pageable pageable = null;
        if (startPage.isEmpty() && endPage.isEmpty() && sortField.isEmpty()) {
            pageable = Pageable.unpaged();
        } else {
            int startpg = startPage.orElse(0);
            int endpg = endPage.orElse(1);
            if (endpg < startpg) {
                endpg = startpg;
            }
            if (sortField.isEmpty()) {
                pageable = PageRequest.of(startpg, endpg);
            } else {
                pageable = PageRequest.of(startpg, endpg, Sort.by(sortField.get()));
            }
        }
        return this.pedidosService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Pedido pedido) {
        var id = pedidosService.create(pedido);
        return ResponseEntity.created(URI.create(String.format("%s/%s", URL_BASE, id))).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") int id, @Valid @RequestBody Pedido pedido) {
        pedido.setIdPedido(id);
        try {
            pedidosService.update(pedido);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") int id) {
        final Optional<Pedido> pedido = pedidosService.getById(id);
        if (pedido.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedido.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        try {
            pedidosService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }
}