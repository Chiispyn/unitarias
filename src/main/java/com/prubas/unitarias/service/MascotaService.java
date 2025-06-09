package com.prubas.unitarias.service;

import com.prubas.unitarias.model.Mascota;
import com.prubas.unitarias.repository.MascotasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MascotaService {
    @Autowired
    private MascotasRepository mascotasRepository;

    public Mascota guardarMascota(Mascota mascota){
        return mascotasRepository.save(mascota);
    }

    public List<Mascota> listarMascotas(){
        return mascotasRepository.findAll();
    }

    public Optional<Mascota> obtenerMascotaPorId(Long id){
        return mascotasRepository.findById(id);
    }

    public Mascota actualizarMascota(Long id, Mascota mascota){
        Mascota existente = mascotasRepository.findById(id)
                    .orElseThrow(()  -> new RuntimeException("No existe la mascota"));
                existente.setNombre(mascota.getNombre());
                existente.setTipo(mascota.getTipo());
                existente.setEdad(mascota.getEdad());
                return mascotasRepository.save(existente);
    }

    public void eliminarMascota(Long id) {
        mascotasRepository.deleteById(id);
    }
}
