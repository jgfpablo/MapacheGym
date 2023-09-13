package com.proyecto.polotic.MapacheGym.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.polotic.MapacheGym.entidades.Membresia;

@Repository
public interface MembresiaRepositorio extends JpaRepository<Membresia, Integer> {

}
