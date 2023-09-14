package com.proyecto.polotic.MapacheGym.entidades;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.*;


@Entity
@Table(name = "empleado")
public class Empleado extends Persona implements Serializable {
    //Administrador, Usuario, y Instructor
    
    @ManyToOne
    @NotNull
    private Rol rol;


    @Column(name = "legajo")
    private String legajo;

    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9.-]+$")
    @NotNull
    @Column(name = "email")
    private String email;

    @Column(name = "contrasenia")
    private String password;

    //RELACION BIDERECCIONAL (1-N) CON ASISTENCIA
    @OneToMany(mappedBy = "asistenciaEmpleado")
    private List<Asistencia> asistencia;

    public Empleado() {
    }

    public Empleado(int id, String dni, String nombre, String apellido, String telefono, Rol rol, String legajo, String email, String password, List<Asistencia> asistencia) {
        super(id, dni, nombre, apellido, telefono);
        this.rol = rol;
        this.legajo = legajo;
        this.email = email;
        this.password = password;
        this.asistencia = asistencia;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return password;
    }

    public void setContrasenia(String password) {
        this.password = password;
    }

    public List<Asistencia> getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(List<Asistencia> asistencia) {
        this.asistencia = asistencia;
    }

}
