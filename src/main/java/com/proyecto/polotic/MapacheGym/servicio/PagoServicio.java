package com.proyecto.polotic.MapacheGym.servicio;

import com.proyecto.polotic.MapacheGym.modelo.Pago;
import com.proyecto.polotic.MapacheGym.repositorio.PagoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoServicio {

    @Autowired
    private PagoRepositorio pagoRepositorio;

    public Pago crearPago(Pago pago){
        return pagoRepositorio.save(pago);
    }

    public Pago modificarPago(Pago pago){
        return pagoRepositorio.save(pago);
    }

    public List<Pago> traerPagos(){
        return pagoRepositorio.findAll();
    }

    public void eliminarPago(Pago pago){
        pagoRepositorio.delete(pago);
    }

}
