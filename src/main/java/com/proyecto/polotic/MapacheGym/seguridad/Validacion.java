package com.proyecto.polotic.MapacheGym.seguridad;

import com.proyecto.polotic.MapacheGym.entidades.Cliente;
import com.proyecto.polotic.MapacheGym.entidades.Empleado;
import com.proyecto.polotic.MapacheGym.repositorios.ClienteRepositorio;
import com.proyecto.polotic.MapacheGym.repositorios.EmpleadoRepositorio;
import com.proyecto.polotic.MapacheGym.servicios.ClienteServicio;
import com.proyecto.polotic.MapacheGym.servicios.EmpleadoServicio;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validacion {

    @Autowired
    private EmpleadoServicio EmpleadoServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    public boolean validarDniEmpleado(String dni) {
        Empleado empleadoExistente = EmpleadoServicio.traerEmpleadoPorDni(dni);
        return empleadoExistente == null;
    }

    public boolean validarDniCliente(String dni) {
        Cliente clienteExistente = clienteServicio.traerClientePorDni(dni);

        return clienteExistente == null   ;
    }

        public boolean validarClienteActivo(String dni) {
            Cliente clienteActivo = clienteServicio.traerClientePorDni(dni);

            boolean status = true;
            if(clienteActivo != null){
            status = clienteActivo.getStatus().equals("Inactivo");
            }

         return status;  //si es false significa que esta Activo
    }
}
