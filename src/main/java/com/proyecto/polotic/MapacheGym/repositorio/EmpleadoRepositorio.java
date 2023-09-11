package com.proyecto.polotic.MapacheGym.repositorio;

import com.proyecto.polotic.MapacheGym.modelo.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepositorio extends JpaRepository<Empleado, Integer> {
    Empleado findByDni(String dni);

    Empleado findEmpleadoById(Integer id);
}
