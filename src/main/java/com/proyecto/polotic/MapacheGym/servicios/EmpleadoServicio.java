package com.proyecto.polotic.MapacheGym.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyecto.polotic.MapacheGym.entidades.*;
import com.proyecto.polotic.MapacheGym.repositorios.*;
import jakarta.transaction.Transactional;

import java.lang.reflect.Array;
import java.util.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class EmpleadoServicio implements UserDetailsService{


    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private BCryptPasswordEncoder codificador;  
    

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

    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Empleado empleado = empleadoRepositorio.findByEmail(email);
        List<GrantedAuthority> ga = buildAuthorities(empleado.getRol());
        return buildUser(empleado, ga);
    }

    public User buildUser(Empleado empleado, List<GrantedAuthority> ga) {
        return new User(empleado.getEmail(), empleado.getContrasenia(), ga);
    }

    public List<GrantedAuthority> buildAuthorities(Rol rol) {
        List<GrantedAuthority> ga = new ArrayList<>();
        ga.add( new SimpleGrantedAuthority("ROLE_" + rol.getNombre()) );
        return ga;
    }

    @Transactional
    public void registro(Empleado empleado) {
        if (empleadoRepositorio.existsByEmail(empleado.getEmail()))
            throw new IllegalArgumentException("Ya existe un usuario con este email");

        empleado.setContrasenia( codificador.encode(empleado.getContrasenia()) );
        empleado.setRol(rolRepositorio.findByNombre("Usuario").orElseThrow(() -> new IllegalArgumentException("Error al crear usuario")));
        empleadoRepositorio.save(empleado);
    }

}