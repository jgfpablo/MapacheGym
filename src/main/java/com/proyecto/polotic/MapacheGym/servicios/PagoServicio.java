package com.proyecto.polotic.MapacheGym.servicios;

import com.proyecto.polotic.MapacheGym.entidades.Membresia;
import com.proyecto.polotic.MapacheGym.entidades.Pago;
import com.proyecto.polotic.MapacheGym.repositorios.PagoRepositorio;
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

    public List<Pago> findAllPagosCliente(Integer idCliente) {
        return pagoRepositorio.findAllPagosCliente(idCliente);
    }

    public Pago traerPagoPorId(Integer idPago) {
        return pagoRepositorio.getById(idPago);
    }

}
