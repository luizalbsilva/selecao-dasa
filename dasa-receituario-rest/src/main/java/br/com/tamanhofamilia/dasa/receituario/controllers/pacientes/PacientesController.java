package br.com.tamanhofamilia.dasa.receituario.controllers.pacientes;

import br.com.tamanhofamilia.dasa.receituario.models.paciente.Paciente;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import br.com.tamanhofamilia.dasa.receituario.services.pacientes.IPacientesService;
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
@RequestMapping(PacientesController.URL_BASE)
public class PacientesController {
    public static final String URL_BASE = "/api/v1/pacientes";
    private IPacientesService pacientesService;

    @Autowired
    PacientesController(IPacientesService pacientesService) {
        this.pacientesService = pacientesService;
    }

    @GetMapping
    public Page<Paciente> readAll(@Param("pgInit") Optional<Integer> startPage, @Param("pgFim") Optional<Integer> endPage, @Param("sortBy") Optional<String> sortField) {

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
        return this.pacientesService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Paciente pacienteDto) {
        var id = pacientesService.create(pacienteDto);
        return ResponseEntity.created(URI.create(String.format("%s/%s", URL_BASE, id))).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") int id, @Valid @RequestBody Paciente pacienteDto) {
        pacienteDto.setIdPaciente(id);
        try {
            pacientesService.update(pacienteDto);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") int id) {
        final Optional<Paciente> paciente = pacientesService.getById(id);
        if (paciente.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paciente.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        try {
            pacientesService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }
}
