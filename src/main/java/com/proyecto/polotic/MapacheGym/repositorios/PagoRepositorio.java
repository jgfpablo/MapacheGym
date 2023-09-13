package com.proyecto.polotic.MapacheGym.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.proyecto.polotic.MapacheGym.entidades.*;
import com.proyecto.polotic.MapacheGym.entidades.Pago;

import java.util.List;

@Repository
public interface PagoRepositorio extends JpaRepository<Pago, Integer> {
    //List<Pago> findByClienteIdOrderByFechaPagoDesc(Integer idCliente);

    //probando sql nativo con nativeQuery
    @Query(value = "SELECT * FROM Pago WHERE cliente = :cliente ORDER BY fecha_pago DESC LIMIT 1", nativeQuery = true)
    Pago findUltimoPagoCliente(@Param("cliente") Integer idCliente);

    @Query(value = "SELECT * FROM Pago WHERE cliente = :cliente ORDER BY fecha_pago DESC", nativeQuery = true)
    List<Pago> findAllPagosCliente(@Param("cliente") Integer idCliente);

    

   

}
