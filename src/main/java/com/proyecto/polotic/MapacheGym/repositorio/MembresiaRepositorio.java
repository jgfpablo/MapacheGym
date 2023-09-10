package com.proyecto.polotic.MapacheGym.repositorio;

import com.proyecto.polotic.MapacheGym.modelo.Membresia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembresiaRepositorio extends JpaRepository<Membresia, Integer> {

}
