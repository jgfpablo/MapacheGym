package com.proyecto.polotic.MapacheGym.seguridad;

import com.proyecto.polotic.MapacheGym.entidades.Cliente;
import com.proyecto.polotic.MapacheGym.entidades.Empleado;
import com.proyecto.polotic.MapacheGym.repositorios.ClienteRepositorio;
import com.proyecto.polotic.MapacheGym.repositorios.EmpleadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validacion {

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public boolean validarDniEmpleado(String dni) {
        /*if (empleadoRepositorio == null) {
            System.out.println("Empleado es nulo");
        }*/ //Esto podriamos eliminar
        Empleado empleadoExistente = empleadoRepositorio.findByDni(dni);
        return empleadoExistente == null;
    }


    public boolean validarDniCliente(String dni) {
        /*if (clienteRepositorio == null) {
            System.out.println("Cliente es nulo");
        }*/
        Cliente clienteExistente = clienteRepositorio.findByDni(dni);
        return clienteExistente == null;
    }
}
