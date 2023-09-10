package com.proyecto.polotic.MapacheGym.servicio;

import com.proyecto.polotic.MapacheGym.modelo.Empleado;
import com.proyecto.polotic.MapacheGym.repositorio.EmpleadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoServicio {

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    public Empleado crearEmpleado(Empleado empleado){
        return empleadoRepositorio.save(empleado);
    }

    public Empleado modificarEmpleado(Empleado empleado){
        return empleadoRepositorio.save(empleado);
    }

    public List<Empleado> traerEmpleados(){
        return empleadoRepositorio.findAll();
    }

    public Empleado traerEmpleadoPorDni(String dni){
        return empleadoRepositorio.findByDni(dni);
    }

    public void eliminarEmpleado(Empleado empleado){
        empleadoRepositorio.delete(empleado);
    }

}
