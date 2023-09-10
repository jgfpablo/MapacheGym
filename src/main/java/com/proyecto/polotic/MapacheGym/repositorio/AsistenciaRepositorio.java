package com.proyecto.polotic.MapacheGym.repositorio;

import com.proyecto.polotic.MapacheGym.modelo.Asistencia;
import com.proyecto.polotic.MapacheGym.modelo.Cliente;
import com.proyecto.polotic.MapacheGym.modelo.Empleado;
import com.proyecto.polotic.MapacheGym.modelo.Pago;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AsistenciaRepositorio extends JpaRepository<Asistencia, Integer> {
    //Asistencia findByAsistenciaEmpleadoAndFecha(Empleado empleado, LocalDate fecha);

    List<Asistencia> findAllByAsistenciaEmpleadoAndFecha(Empleado empleado, LocalDate fecha);

//Este es el quefunciona para encontrar las ultimas asistencias del cliente
    @Query("SELECT a FROM Asistencia a WHERE a.asistenciaCliente = :cliente " + "ORDER BY a.fecha DESC, a.horaInicio DESC")
    List<Asistencia> findUltimasAsistenciasCliente(@Param("cliente") Cliente cliente, Pageable pageable);

//Estos no...
    //@Query("SELECT a FROM Asistencia a WHERE a.asistenciaCliente = :cliente ORDER BY a.fecha DESC")
    //Asistencia findUltimaAsistenciaCliente(@Param("cliente") Cliente cliente);

    //@Query(value = "SELECT * FROM Asistencia WHERE asistenciaCliente_id = :clienteId ORDER BY fecha DESC LIMIT 1", nativeQuery = true)
    //Asistencia findUltimaAsistenciaCliente(@Param("clienteId") Long clienteId);


}

