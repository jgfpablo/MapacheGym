package com.proyecto.polotic.MapacheGym.servicios;

import com.proyecto.polotic.MapacheGym.entidades.Empleado;
import com.proyecto.polotic.MapacheGym.repositorios.EmpleadoRepositorio;
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


    public Empleado traerEmpleadoPorId(Integer id){
        return empleadoRepositorio.findEmpleadoById(id);
    }

    public void eliminarEmpleado(Empleado empleado){
        empleadoRepositorio.delete(empleado);
    }

}
