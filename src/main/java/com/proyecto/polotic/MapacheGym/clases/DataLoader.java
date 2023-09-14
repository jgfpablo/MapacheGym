package com.proyecto.polotic.MapacheGym.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.proyecto.polotic.MapacheGym.entidades.*;
import com.proyecto.polotic.MapacheGym.repositorios.MembresiaRepositorio;
import com.proyecto.polotic.MapacheGym.repositorios.RolRepositorio;

import jakarta.validation.Valid;

import com.proyecto.polotic.MapacheGym.configuraciones.Codificador;
import com.proyecto.polotic.MapacheGym.dto.RegistroDto;
import com.proyecto.polotic.MapacheGym.configuraciones.*;


@Component
public class DataLoader implements CommandLineRunner{
    
    @Autowired
    private final RolRepositorio rolRepositorio;

    @Autowired
    private final MembresiaRepositorio membresiaRepositorio;


    
    @Autowired
    public DataLoader(RolRepositorio rolRepositorio,MembresiaRepositorio membresiaRepositorio){
        this.rolRepositorio = rolRepositorio;
        this.membresiaRepositorio = membresiaRepositorio;
    }

    

    @Override
    public void run(String... args) throws Exception {
        
        Rol usuario = new Rol();
        usuario.setNombre("Usuario");
        rolRepositorio.save(usuario);
        
        Rol administrador = new Rol();
        administrador.setNombre("Administrador");
        rolRepositorio.save(administrador);

        Membresia membresia = new Membresia();

        membresia.setTipoMembresia("Basico");
        membresia.setDescripcion("¡Comienza tu viaje de fitness con el Plan Básico!");
        membresia.setPrecio("4.99");
        membresia.setDiasSemanales(2);
        membresiaRepositorio.save(membresia);

        Membresia membresia2 = new Membresia();
        membresia2.setTipoMembresia("Plan Mapache");
        membresia2.setDescripcion("¡Mejora tu nivel con el Plan Mapache!");
        membresia2.setPrecio("9.99");
        membresia2.setDiasSemanales(3);
        membresiaRepositorio.save(membresia2);

        Membresia membresia3 = new Membresia();
        membresia3.setTipoMembresia("Plan Mapache Feroz");
        membresia3.setDescripcion("¡Alcanza tus metas con el Plan Mapache Feroz!");
        membresia3.setPrecio("14.99");
        membresia3.setDiasSemanales(5);
        membresiaRepositorio.save(membresia3);


    }
}
