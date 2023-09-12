package com.proyecto.polotic.MapacheGym.repositorio;

import com.proyecto.polotic.MapacheGym.modelo.Cliente;
import com.proyecto.polotic.MapacheGym.modelo.Empleado;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
// public interface ClienteRepositorio extends CrudRepository<Cliente, Integer> {
public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> { 
    
    Cliente findByDni(String dni);

    List<Cliente> findAll();

    Cliente findClienteById(Integer id);
}
