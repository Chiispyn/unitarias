package com.prubas.unitarias.service;

import com.prubas.unitarias.model.Mascota;
import com.prubas.unitarias.repository.MascotasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class MascotaServiceTest {
    @Mock
    private MascotasRepository mascotasRepository;

    @InjectMocks
    private MascotaService mascotaService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    /*para guardar mascota en la capa servicio */
    @Test
    void testGuardarMascota(){ 
    Mascota mascota = new Mascota(null, "Rex", "Perro", 5);
    Mascota mascotaGuardada = new Mascota(1L, "Rex", "Perro", 5);
    
    when(mascotasRepository.save(mascota)).thenReturn(mascotaGuardada);

    Mascota resultado = mascotaService.guardarMascota(mascota);
    
    assertThat(resultado.getId()).isEqualTo(1L);
    assertThat(resultado.getNombre()).isEqualTo("Rex");
    assertThat(resultado.getTipo()).isEqualTo("Perro");
    assertThat(resultado.getEdad()).isEqualTo(5);
    
    verify(mascotasRepository).save(mascota);
}
    @Test
    void testListarMascotas() {
        Mascota m1 = new Mascota(1L, "Rex", "Perro", 5);
        Mascota m2 = new Mascota(2L, "Michi", "Gato", 2);
        when(mascotasRepository.findAll()).thenReturn(Arrays.asList(m1, m2));

        List<Mascota> resultado = mascotaService.listarMascotas();
        assertThat(resultado).hasSize(2).contains(m1, m2);
        verify(mascotasRepository).findAll();
    }   

}
