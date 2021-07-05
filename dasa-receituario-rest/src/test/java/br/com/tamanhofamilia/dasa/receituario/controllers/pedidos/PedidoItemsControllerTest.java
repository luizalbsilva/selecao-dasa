package br.com.tamanhofamilia.dasa.receituario.controllers.pedidos;

import br.com.tamanhofamilia.dasa.receituario.models.pedidos.PedidoItem;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import br.com.tamanhofamilia.dasa.receituario.services.pedidos.IPedidoItemsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PedidoItemsControllerTest {
    private static final String PEDIDO_ITEM = "{" +
            "\"exame\": { \"idExame\": 3 } " +
            "}";

    @InjectMocks
    PedidoItemsController controller;

    @Mock
    IPedidoItemsService service;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    void readAll() throws Exception {
        mockMvc.perform(get("/api/v1/pedidos/2/items")).andExpect(status().isOk());

        verify(service).findAllFromPedido(eq(2), any(Pageable.class));
    }

    @Test
    void createWithoutData() throws Exception {
        mockMvc.perform(
                post("/api/v1/pedidos/2/items")
                    .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createEmptyData() throws Exception {
        mockMvc.perform(
                post("/api/v1/pedidos/2/items")
                    .content("{ }")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createDataProbDadosRelacionados() throws Exception {
        when(service.create(any(PedidoItem.class)))
                .thenThrow(DataNotFoundException.class);

        mockMvc.perform(
                post("/api/v1/pedidos/2/items")
                    .content(PEDIDO_ITEM)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isPreconditionFailed());

    }


    @Test
    void createData() throws Exception {
        when(service.create(any(PedidoItem.class)))
                .thenReturn(1L);
        mockMvc.perform(
                post("/api/v1/pedidos/2/items")
                    .content(PEDIDO_ITEM)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        verify(service).create(any(PedidoItem.class));
    }

    @Test
    void createDataVerificaUsoIdPedido() throws Exception {
        when(service.create(any(PedidoItem.class)))
                .thenReturn(1L);
        mockMvc.perform(
                post("/api/v1/pedidos/2/items")
                    .content(PEDIDO_ITEM)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
        );
        final ArgumentCaptor<PedidoItem> captor = ArgumentCaptor.forClass(PedidoItem.class);

        verify(service).create(captor.capture());
        final PedidoItem pedidoItem = captor.getValue();
        assertEquals(2, pedidoItem.getPedido().getIdPedido());
    }

    @Test
    void updateReturnStatus() throws Exception {
        mockMvc.perform(
                put("/api/v1/pedidos/2/items/1")
                        .content(PEDIDO_ITEM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void updateNotFound() throws Exception, DataNotFoundException {
        doThrow(new DataNotFoundException()).when(service).update(any(PedidoItem.class));
        mockMvc.perform(
                put("/api/v1/pedidos/2/items/1")
                        .content(PEDIDO_ITEM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isPreconditionFailed());

        verify(service).update(any(PedidoItem.class));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(
                put("/api/v1/pedidos/2/items/1")
                        .content(PEDIDO_ITEM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        verify(service).update(any(PedidoItem.class));
    }

    @Test
    void testGetSemDados() throws Exception {
        mockMvc.perform(
                get("/api/v1/pedidos/2/items/1")
        ).andExpect(status().isNoContent());
    }

    @Test
    void testGetComDados() throws Exception {
        when(service.getById(anyLong()))
                .thenReturn(Optional.of(new PedidoItem()));

        mockMvc.perform(
                get("/api/v1/pedidos/2/items/1")
        ).andExpect(status().isOk());
    }

    @Test
    void deleteNotFound() throws Exception {
        doThrow(DataNotFoundException.class)
                .when(service).delete(anyLong());

        mockMvc.perform(
                delete("/api/v1/pedidos/2/items/1")
        ).andExpect(status().isPreconditionFailed());
    }

    @Test
    void deleteOk() throws Exception {
        mockMvc.perform(
                delete("/api/v1/pedidos/2/items/1")
        ).andExpect(status().isNoContent());
    }
}