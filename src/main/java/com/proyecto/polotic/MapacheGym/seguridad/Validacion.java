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

    //  @Autowired
    //  private EmpleadoRepositorio empleadoRepositorio;

    //  @Autowired
    //  private ClienteRepositorio clienteRepositorio;

    @Autowired
    private EmpleadoServicio EmpleadoServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    public boolean validarDniEmpleado(String dni) {
        Empleado empleadoExistente = EmpleadoServicio.traerEmpleadoPorDni(dni);
        return empleadoExistente == null;
    }
   


    public boolean validarDniCliente(String dni) {
        /*if (clienteRepositorio == null) {
            System.out.println("Cliente es nulo");
        }*/
        Cliente clienteExistente = clienteServicio.traerClientePorDni(dni);

        return clienteExistente == null   ;
    }

     public boolean validarClienteActivo(String dni) {
         Cliente clienteActivo = clienteServicio.traerClientePorDni(dni);

         return clienteActivo.getStatus() != "Activo";  //si es true significa que esta inactivo
     }
}
