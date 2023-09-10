package com.proyecto.polotic.MapacheGym.modelo;

import jakarta.persistence.*;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "empleado")
public class Empleado extends Persona implements Serializable {
    //Administrador, Usuario, y Instructor
    @Column(name = "tipo_empleado")
    @Pattern(regexp = "^(Administrador|Usuario|Instructor)$", message= "usuario no valido")
    private String tipoEmpleado;

    @Column(name = "legajo")
    private String legajo;

    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9.-]+$")
    @Column(name = "email")
    private String email;

    @Column(name = "contrasenia")
    private String contrasenia;

    //RELACION BIDERECCIONAL (1-N) CON ASISTENCIA
    @OneToMany(mappedBy = "asistenciaEmpleado")
    private List<Asistencia> asistencia;

    public Empleado() {
    }

    public Empleado(int id, String dni, String nombre, String apellido, String telefono, String tipoEmpleado, String legajo, String email, String contrasenia, List<Asistencia> asistencia) {
        super(id, dni, nombre, apellido, telefono);
        this.tipoEmpleado = tipoEmpleado;
        this.legajo = legajo;
        this.email = email;
        this.contrasenia = contrasenia;
        this.asistencia = asistencia;
    }

    public String getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(String tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
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
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public List<Asistencia> getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(List<Asistencia> asistencia) {
        this.asistencia = asistencia;
    }

}
