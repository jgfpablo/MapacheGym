package com.proyecto.polotic.MapacheGym.repositorios;

import com.proyecto.polotic.MapacheGym.entidades.*;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepositorio extends CrudRepository<Rol, Long> {
    
    
    Optional<Rol> findByNombre(String string);

}
