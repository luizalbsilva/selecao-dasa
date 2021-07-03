package br.com.tamanhofamilia.dasa.receituario.controllers.exames;

import br.com.tamanhofamilia.dasa.receituario.models.exame.Exame;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import br.com.tamanhofamilia.dasa.receituario.services.exames.IExamesService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(ExamesController.URL_BASE)
public class ExamesController {
    public final static String URL_BASE = "/api/v1/exames";
    private IExamesService examesService;

    @Autowired
    ExamesController(IExamesService examesService) {
        this.examesService = examesService;
    }

    @GetMapping
    public Page<Exame> readAll(@Param("pgInit") Optional<Integer> startPage, @Param("pgFim") Optional<Integer> endPage, @Param("sortBy") Optional<String> sortField) {

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
        return this.examesService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Exame exame) {
        var id = examesService.create(exame);
        return ResponseEntity.created(URI.create(String.format("%s/%s", URL_BASE, id))).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") int id, @Valid @RequestBody Exame exame) {
        exame.setIdExame(id);
        try {
            examesService.update(exame);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") int id) {
        final Optional<Exame> exame = examesService.getById(id);
        if (exame.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(exame.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
        try {
            examesService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }
}
