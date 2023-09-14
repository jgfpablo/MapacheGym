package com.proyecto.polotic.MapacheGym.repositorios;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import com.proyecto.polotic.MapacheGym.entidades.Empleado;

@Repository
public interface EmpleadoRepositorio extends CrudRepository<Empleado, Integer> {
    Empleado findByDni(String dni);

    Empleado findEmpleadoById(Integer id);

    Empleado findByEmail(String email);

    boolean existsByEmail(String email);

    List<Empleado> findAll();
    
}

