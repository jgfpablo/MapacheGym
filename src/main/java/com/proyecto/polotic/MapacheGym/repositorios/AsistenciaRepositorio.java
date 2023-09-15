package com.proyecto.polotic.MapacheGym.repositorios;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.polotic.MapacheGym.entidades.Asistencia;
import com.proyecto.polotic.MapacheGym.entidades.Cliente;
import com.proyecto.polotic.MapacheGym.entidades.Empleado;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface AsistenciaRepositorio extends JpaRepository<Asistencia, Integer> {
    //Asistencia findByAsistenciaEmpleadoAndFecha(Empleado empleado, LocalDate fecha);

    List<Asistencia> findAllByAsistenciaEmpleadoAndFecha(Empleado empleado, LocalDate fecha);

//Este es el quefunciona para encontrar las ultimas asistencias del cliente
    @Query("SELECT a FROM Asistencia a WHERE a.asistenciaCliente = :cliente " + "ORDER BY a.fecha DESC, a.horaInicio DESC")
    List<Asistencia> findUltimasAsistenciasCliente(@Param("cliente") Cliente cliente, Pageable pageable);


    @Query("SELECT a FROM Asistencia a WHERE a.asistenciaEmpleado IS NOT NULL ORDER BY a.fecha DESC, a.horaInicio DESC")
    List<Asistencia> findAsistenciasEmpleados();

    @Query("SELECT a FROM Asistencia a WHERE a.asistenciaCliente IS NOT NULL ORDER BY a.fecha DESC, a.horaInicio DESC")
    List<Asistencia> findAsistenciasClientes();

//Estos no...
    //@Query("SELECT a FROM Asistencia a WHERE a.asistenciaCliente = :cliente ORDER BY a.fecha DESC")
    //Asistencia findUltimaAsistenciaCliente(@Param("cliente") Cliente cliente);

    //@Query(value = "SELECT * FROM Asistencia WHERE asistenciaCliente_id = :clienteId ORDER BY fecha DESC LIMIT 1", nativeQuery = true)
    //Asistencia findUltimaAsistenciaCliente(@Param("clienteId") Long clienteId);


}

