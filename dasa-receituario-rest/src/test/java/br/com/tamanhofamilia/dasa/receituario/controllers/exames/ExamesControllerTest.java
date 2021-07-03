package br.com.tamanhofamilia.dasa.receituario.controllers.exames;

import br.com.tamanhofamilia.dasa.receituario.models.exame.Exame;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import br.com.tamanhofamilia.dasa.receituario.services.exames.IExamesService;
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

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ExamesControllerTest {
    @InjectMocks
    ExamesController controller;

    @Mock
    IExamesService service;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    void readAll() throws Exception {
        mockMvc.perform(get("/api/v1/exames")).andExpect(status().isOk());

        verify(service).findAll(any(Pageable.class));
    }

    @Test
    void createWithoutData() throws Exception {
        mockMvc.perform(
                post("/api/v1/exames")
                    .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createEmptyData() throws Exception {
        mockMvc.perform(
                post("/api/v1/exames")
                    .content("{ }")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createData() throws Exception {
        when(service.create(any(Exame.class)))
                .thenReturn(1);
        mockMvc.perform(
                post("/api/v1/exames")
                    .content("{ \"descricao\": \"Hemograma Simples \" }")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        verify(service).create(any(Exame.class));
    }

    @Test
    void updateReturnStatus() throws Exception {
        mockMvc.perform(
                put("/api/v1/exames/1")
                        .content("{ \"descricao\": \"Hemograma Simples \" }")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void updateNotFound() throws Exception, DataNotFoundException {
        doThrow(new DataNotFoundException()).when(service).update(any(Exame.class));
        mockMvc.perform(
                put("/api/v1/exames/1")
                        .content("{ \"descricao\": \"Hemograma Simples \" }")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isPreconditionFailed());

        verify(service).update(any(Exame.class));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(
                put("/api/v1/exames/1")
                        .content("{ \"descricao\": \"Hemograma Simples \" }")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        verify(service).update(any(Exame.class));
    }

    @Test
    void testGetSemDados() throws Exception {
        mockMvc.perform(
                get("/api/v1/exames/1")
        ).andExpect(status().isNoContent());
    }

    @Test
    void testGetComDados() throws Exception {
        when(service.getById(anyInt()))
                .thenReturn(Optional.of(new Exame()));

        mockMvc.perform(
                get("/api/v1/exames/1")
        ).andExpect(status().isOk());
    }

    @Test
    void deleteNotFound() throws Exception {
        doThrow(DataNotFoundException.class)
                .when(service).delete(anyInt());

        mockMvc.perform(
                delete("/api/v1/exames/1")
        ).andExpect(status().isPreconditionFailed());
    }

    @Test
    void deleteOk() throws Exception {
        mockMvc.perform(
                delete("/api/v1/exames/1")
        ).andExpect(status().isNoContent());
    }
}