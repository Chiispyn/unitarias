package com.prubas.unitarias.repository;

import com.prubas.unitarias.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MascotasRepository extends JpaRepository<Mascota, Long>{}
