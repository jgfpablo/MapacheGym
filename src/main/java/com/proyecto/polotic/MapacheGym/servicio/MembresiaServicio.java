package com.proyecto.polotic.MapacheGym.servicio;

import com.proyecto.polotic.MapacheGym.modelo.Membresia;
import com.proyecto.polotic.MapacheGym.repositorio.MembresiaRepositorio;
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
