package com.proyecto.polotic.MapacheGym.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.proyecto.polotic.MapacheGym.entidades.*;
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
    public DataLoader(RolRepositorio rolRepositorio){
        this.rolRepositorio = rolRepositorio;
    }

    @Override
    public void run(String... args) throws Exception {
        
        Rol usuario = new Rol();
        usuario.setNombre("Usuario");
        rolRepositorio.save(usuario);
        
        Rol administrador = new Rol();
        administrador.setNombre("Administrador");
        rolRepositorio.save(administrador);

    }
}
