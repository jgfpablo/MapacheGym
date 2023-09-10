package com.proyecto.polotic.MapacheGym.seguridad;

import com.proyecto.polotic.MapacheGym.modelo.Cliente;
import com.proyecto.polotic.MapacheGym.modelo.Empleado;
import com.proyecto.polotic.MapacheGym.repositorio.ClienteRepositorio;
import com.proyecto.polotic.MapacheGym.repositorio.EmpleadoRepositorio;
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
        }*/
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
