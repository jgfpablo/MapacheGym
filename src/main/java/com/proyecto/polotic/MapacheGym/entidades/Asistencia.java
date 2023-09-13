package com.proyecto.polotic.MapacheGym.entidades;

import jakarta.persistence.*;

import javax.ejb.Local;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "asistencia")
public class Asistencia implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id_asistencia")
    private int idAsistencia;

    @Column(name = "dia")
    private String dia;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fin")
    private LocalTime horaFin;

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private LocalDate fecha;

    //RELACION (N-1) CON EMPLEADO
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_empleado")
    private Empleado asistenciaEmpleado;

    //RELACION (N-1) CON CLIENTE
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_cliente")
    private Cliente asistenciaCliente;

    public Asistencia() {
    }

    public Asistencia(int idAsistencia, String dia, LocalTime horaInicio, LocalTime horaFin, LocalDate fecha, Empleado asistenciaEmpleado, Cliente asistenciaCliente) {
        this.idAsistencia = idAsistencia;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fecha = fecha;
        this.asistenciaEmpleado = asistenciaEmpleado;
        this.asistenciaCliente = asistenciaCliente;
    }

    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Empleado getAsistenciaEmpleado() {
        return asistenciaEmpleado;
    }

    public void setAsistenciaEmpleado(Empleado asistenciaEmpleado) {
        this.asistenciaEmpleado = asistenciaEmpleado;
    }

    public Cliente getAsistenciaCliente() {
        return asistenciaCliente;
    }

    public void setAsistenciaCliente(Cliente asistenciaCliente) {
        this.asistenciaCliente = asistenciaCliente;
    }


}