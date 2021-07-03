package br.com.tamanhofamilia.dasa.receituario.controllers.medicos;

import br.com.tamanhofamilia.dasa.receituario.controllers.PageableHelper;
import br.com.tamanhofamilia.dasa.receituario.models.medico.Conselho;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import br.com.tamanhofamilia.dasa.receituario.services.medicos.IConselhosService;
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
@RequestMapping(ConselhosController.URL_BASE)
public class ConselhosController {
    public static final String URL_BASE = "/api/v1/conselhos";
    private IConselhosService service;

    @Autowired
    public ConselhosController(IConselhosService conselhosService) {
        this.service = conselhosService;
    }

    @GetMapping
    public Page<Conselho> readAll(@Param("pgInit") Optional<Integer> startPage, @Param("pgFim") Optional<Integer> endPage, @Param("sortBy") Optional<String> sortField) {
        Pageable pageable = PageableHelper.create(startPage, endPage, sortField);
        return this.service.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Conselho conselho) {
        var id = service.create(conselho);
        return ResponseEntity.created(URI.create(String.format("%s/%s", URL_BASE, id))).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") int id, @Valid @RequestBody Conselho conselho) {
        conselho.setIdConselho(id);
        try {
            service.update(conselho);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") int id) {
        final Optional<Conselho> conselho = service.getById(id);
        if (conselho.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(conselho.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }
}
