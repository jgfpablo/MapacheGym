package com.proyecto.polotic.MapacheGym.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.polotic.MapacheGym.entidades.Empleado;

@Repository
public interface EmpleadoRepositorio extends JpaRepository<Empleado, Integer> {
    Empleado findByDni(String dni);

    Empleado findEmpleadoById(Integer id);
}
