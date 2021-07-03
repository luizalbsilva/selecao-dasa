package br.com.tamanhofamilia.dasa.receituario.controllers.pedidos;

import br.com.tamanhofamilia.dasa.receituario.models.pedidos.Pedido;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import br.com.tamanhofamilia.dasa.receituario.services.pedidos.IPedidosService;
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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PedidosControllerTest {
    private static final String PEDIDO =
            "{" +
                    "\"dataValidade\": \"2021-07-03\"," +
                    "\"medico\": { \"idMedico\": 1 }," +
                    "\"paciente\": { \"idPaciente\": 1 }" +
            "}";

    @InjectMocks
    PedidosController controller;

    @Mock
    IPedidosService service;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    void readAll() throws Exception {
        mockMvc.perform(get("/api/v1/pedidos")).andExpect(status().isOk());

        verify(service).findAll(any(Pageable.class));
    }

    @Test
    void createWithoutData() throws Exception {
        mockMvc.perform(
                post("/api/v1/pedidos")
                    .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createEmptyData() throws Exception {
        mockMvc.perform(
                post("/api/v1/pedidos")
                    .content("{ }")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createData() throws Exception {
        when(service.create(any(Pedido.class)))
                .thenReturn(1);
        mockMvc.perform(
                post("/api/v1/pedidos")
                    .content(PEDIDO)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        verify(service).create(any(Pedido.class));
    }

    @Test
    void updateReturnStatus() throws Exception {
        mockMvc.perform(
                put("/api/v1/pedidos/1")
                        .content(PEDIDO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void updateNotFound() throws Exception, DataNotFoundException {
        doThrow(new DataNotFoundException()).when(service).update(any(Pedido.class));
        mockMvc.perform(
                put("/api/v1/pedidos/1")
                        .content(PEDIDO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isPreconditionFailed());

        verify(service).update(any(Pedido.class));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(
                put("/api/v1/pedidos/1")
                        .content(PEDIDO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        verify(service).update(any(Pedido.class));
    }

    @Test
    void testGetSemDados() throws Exception {
        mockMvc.perform(
                get("/api/v1/pedidos/1")
        ).andExpect(status().isNoContent());
    }

    @Test
    void testGetComDados() throws Exception {
        when(service.getById(anyInt()))
                .thenReturn(Optional.of(new Pedido()));

        mockMvc.perform(
                get("/api/v1/pedidos/1")
        ).andExpect(status().isOk());
    }

    @Test
    void deleteNotFound() throws Exception {
        doThrow(DataNotFoundException.class)
                .when(service).delete(anyInt());

        mockMvc.perform(
                delete("/api/v1/pedidos/1")
        ).andExpect(status().isPreconditionFailed());
    }

    @Test
    void deleteOk() throws Exception {
        mockMvc.perform(
                delete("/api/v1/pedidos/1")
        ).andExpect(status().isNoContent());
    }
}