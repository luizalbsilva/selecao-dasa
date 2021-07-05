package br.com.tamanhofamilia.dasa.receituario.controllers.pacientes;

import br.com.tamanhofamilia.dasa.receituario.models.paciente.Paciente;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import br.com.tamanhofamilia.dasa.receituario.services.pacientes.IPacientesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PacientesControllerTest {
    private static final String PACIENTE =
            "{ \"nome\": \"Joe Montana\", " +
            "\"nascimento\": \"1985-07-15\"," +
            "\"sexo\": \"FEMININO\"," +
            "\"nomeMae\": \"Matinha da Vila\"," +
            "\"endereco\": { " +
                "\"rua\": \"Rua do Sobe e Desce, 43\"," +
                "\"bairro\": \"centro\"," +
                "\"cidade\": \"São Paulo\"," +
                "\"uf\": \"SP\"" +
            "}," +
            "\"mobile\": \"11988520976\"," +
            "\"telefone\": \"11951459470\"," +
            "\"email\": \"martinha@davila.com.br\"," +
            "\"documentos\": {" +
                "\"cpf\": \"17306920014\"," +
                "\"rg\": \"958017521\"" +
            "} }";


    @InjectMocks
    PacientesController controller;

    @Mock
    IPacientesService service;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    void readAll() throws Exception {
        mockMvc.perform(get("/api/v1/pacientes")).andExpect(status().isOk());

        verify(service).findAll(any(Pageable.class));
    }

    @Test
    void createWithoutData() throws Exception {
        mockMvc.perform(
                post("/api/v1/pacientes")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createEmptyData() throws Exception {
        mockMvc.perform(
                post("/api/v1/pacientes")
                        .content("{ }")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createData() throws Exception {
        when(service.create(any(Paciente.class)))
                .thenReturn(1);
        mockMvc.perform(
                post("/api/v1/pacientes")
                        .content(PACIENTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        verify(service).create(any(Paciente.class));
    }

    @Test
    void updateReturnStatus() throws Exception {
        mockMvc.perform(
                put("/api/v1/pacientes/1")
                        .content(PACIENTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void updateNotFound() throws Exception, DataNotFoundException {
        doThrow(new DataNotFoundException()).when(service).update(any(Paciente.class));
        mockMvc.perform(
                put("/api/v1/pacientes/1")
                        .content(PACIENTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isPreconditionFailed());

        verify(service).update(any(Paciente.class));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(
                put("/api/v1/pacientes/1")
                        .content(PACIENTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        verify(service).update(any(Paciente.class));
    }

    @Test
    void testGetSemDados() throws Exception {
        mockMvc.perform(
                get("/api/v1/pacientes/1")
        ).andExpect(status().isNoContent());
    }

    @Test
    void testGetComDados() throws Exception {
        when(service.getById(anyInt()))
                .thenReturn(Optional.of(new Paciente()));

        mockMvc.perform(
                get("/api/v1/pacientes/1")
        ).andExpect(status().isOk());
    }

    @Test
    void deleteNotFound() throws Exception {
        doThrow(DataNotFoundException.class)
                .when(service).delete(anyInt());

        mockMvc.perform(
                delete("/api/v1/pacientes/1")
        ).andExpect(status().isPreconditionFailed());
    }

    @Test
    void deleteOk() throws Exception {
        mockMvc.perform(
                delete("/api/v1/pacientes/1")
        ).andExpect(status().isNoContent());
    }
}