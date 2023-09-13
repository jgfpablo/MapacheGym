package com.proyecto.polotic.MapacheGym.entidades;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente extends Persona implements Serializable {

    @Column(name = "dias_disponibles")
    private int diasDisponibles;

    @Column(name = "status")
    private String status;

    @Column(name = "fecha_alta")
    @Temporal(TemporalType.DATE)
    private LocalDate fechaAlta;

    //RELACION  (N-1) CON MEMBRESIA
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_membresia")
    private Membresia membresia;

    //RELACION  (1-N) CON ASISTENCIA
    @OneToMany(mappedBy = "asistenciaCliente")
    private List<Asistencia> asistencia;

    //RELACION  (1-N) CON PAGOS
    @OneToMany(mappedBy = "cliente")
    private List<Pago> pago;

    public Cliente() {
    }

    public Cliente(Integer id, String dni, String nombre, String apellido, String telefono, int diasDisponibles, String status, LocalDate fechaAlta, Membresia membresia, List<Asistencia> asistencia, List<Pago> pago) {
        super(id, dni, nombre, apellido, telefono);
        this.diasDisponibles = diasDisponibles;
        this.status = status;
        this.fechaAlta = fechaAlta;
        this.membresia = membresia;
        this.asistencia = asistencia;
        this.pago = pago;
    }

    public int getDiasDisponibles() {
        return diasDisponibles;
    }

    public void setDiasDisponibles(int diasDisponibles) {
        this.diasDisponibles = diasDisponibles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }

    public List<Asistencia> getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(List<Asistencia> asistencia) {
        this.asistencia = asistencia;
    }

    public List<Pago> getPago() {
        return pago;
    }

    public void setPago(List<Pago> pago) {
        this.pago = pago;
    }


}