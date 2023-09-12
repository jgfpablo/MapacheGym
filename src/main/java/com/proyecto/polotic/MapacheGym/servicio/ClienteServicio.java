package com.proyecto.polotic.MapacheGym.servicio;

import com.proyecto.polotic.MapacheGym.modelo.Asistencia;
import com.proyecto.polotic.MapacheGym.modelo.Cliente;
import com.proyecto.polotic.MapacheGym.modelo.Empleado;
import com.proyecto.polotic.MapacheGym.modelo.Pago;
import com.proyecto.polotic.MapacheGym.repositorio.ClienteRepositorio;
import com.proyecto.polotic.MapacheGym.repositorio.PagoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ClienteRepositorio asistenciaRepositorio;

    @Autowired
    private PagoRepositorio pagoRepositorio;

    public Cliente crearCliente(Cliente cliente){
        return clienteRepositorio.save(cliente);
    }

    public Cliente modificarCliente(Cliente cliente){
        return clienteRepositorio.save(cliente);
    }

    public List<Cliente> traerClientes(){
        return clienteRepositorio.findAll();
    }

    public Cliente traerClientePorDni(String dni){
        return clienteRepositorio.findByDni(dni);
    }

    public Cliente traerClientePorId(Integer id){
        return clienteRepositorio.findClienteById(id);
    }

    // public Empleado traerEmpleadoPorId(Integer id){
    //     return empleadoRepositorio.findEmpleadoById(id);
    // }

    public void eliminarCliente(Cliente cliente){
        clienteRepositorio.delete(cliente);
    }

//Trae el el ultimo pago que el cliente ha hecho
    public Pago obtenerUltimoPago(int clienteId) {
        return pagoRepositorio.findUltimoPagoCliente(clienteId);
    }

}
