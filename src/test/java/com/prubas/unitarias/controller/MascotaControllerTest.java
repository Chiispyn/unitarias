package com.prubas.unitarias.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prubas.unitarias.controller.MascotaController;
import com.prubas.unitarias.model.Mascota;
import com.prubas.unitarias.service.MascotaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MascotaController.class)
class MascotaControllerTest{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MascotaService mascotaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenerTodas() throws Exception{
        Mascota m1 = new Mascota(1L, "Toby", "Perro", 3);
        Mascota m2 = new Mascota(2L, "Michi", "Gato", 1);

        Mockito.when(mascotaService.listarMascotas()).thenReturn(Arrays.asList(m1,m2));

        mockMvc.perform(get("/api/mascotas"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombre").value("Tobby"))
            .andExpect(jsonPath("$[1].tipo").value("Gato"));
    }
}
