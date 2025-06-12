package com.prubas.unitarias.service;

import com.prubas.unitarias.model.Mascota;
import com.prubas.unitarias.repository.MascotasRepository;

import jakarta.persistence.EntityNotFoundException;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test 
    void eliminarMascota(){
        Long id = 99L;
        when(mascotasRepository.existsById(id)).thenReturn(true);
        mascotaService.eliminarMascota(id);   
        // Verificamos que se llamó a deleteById con el ID correcto
        verify(mascotasRepository).deleteById(id);   
    }

    @Test
    void testEliminarMascotaNoExistente() {
    Long id = 99L;
    // Simulamos que NO existe la mascota
    when(mascotasRepository.existsById(id)).thenReturn(false);
    // Verificamos que se lanza la excepción esperada
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
        mascotaService.eliminarMascota(id);
    });
    assertThat(exception.getMessage()).isEqualTo("Mascota no encontrada con id: " + id);
    // Verificamos que no se llama a deleteById
    verify(mascotasRepository,never()).deleteById(any());
    }
    @Test
    void testActualizarMascotaExistente() {
    Long id = 1L;
    Mascota mascotaExistente = new Mascota(id, "Rex", "Perro", 5);
    Mascota mascotaActualizada = new Mascota(null, "Firulais", "Perro", 6);
    Mascota mascotaGuardada = new Mascota(id, "Firulais", "Perro", 6);

    // Simular que la mascota existe
    when(mascotasRepository.findById(id)).thenReturn(Optional.of(mascotaExistente));
    // Simular que el save devuelve la mascota actualizada
    when(mascotasRepository.save(any(Mascota.class))).thenReturn(mascotaGuardada);

    Mascota resultado = mascotaService.actualizarMascota(id, mascotaActualizada);

    assertThat(resultado.getNombre()).isEqualTo("Firulais");
    assertThat(resultado.getEdad()).isEqualTo(6);
    verify(mascotasRepository).findById(id);
    verify(mascotasRepository).save(mascotaExistente);
    }    
}


