package com.proyecto.polotic.MapacheGym.servicios;

import com.proyecto.polotic.MapacheGym.entidades.Membresia;
import com.proyecto.polotic.MapacheGym.repositorios.MembresiaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembresiaServicio {

    @Autowired
    private MembresiaRepositorio membresiaRepositorio;

    public Membresia crearMembresia(Membresia membresia){
        return membresiaRepositorio.save(membresia);
    }

    public Membresia modificarMembresia(Membresia membresia){
        return membresiaRepositorio.save(membresia);
    }

    public List<Membresia> traerMembresias(){
        return membresiaRepositorio.findAll();
    }

    public Membresia traerMembresiaPorId(Integer idMembresia) {
        return membresiaRepositorio.getById(idMembresia);
    }

    public void eliminarMembresia(Membresia membresia){
        membresiaRepositorio.delete(membresia);
    }
    

}
