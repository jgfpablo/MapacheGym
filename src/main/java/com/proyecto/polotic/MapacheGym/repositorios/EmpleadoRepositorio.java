package com.proyecto.polotic.MapacheGym.repositorios;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.proyecto.polotic.MapacheGym.entidades.Empleado;

@Repository
public interface EmpleadoRepositorio extends CrudRepository<Empleado, Integer> {
    Empleado findByDni(String dni);

    Empleado findEmpleadoById(Integer id);

    Empleado findByUsuario(String usuario);

    boolean existsByUsuario(String usuario);

    // List<Empleado> findAll();

    //todos los empleados menos admin
    @Query("SELECT e FROM Empleado e WHERE e.rol.id <> 2")
    List<Empleado> findAll();

    
    
    @Query("SELECT e.password FROM Empleado e WHERE e.id = :id")
    String findPasswordById(@Param("id") Integer id);
}

