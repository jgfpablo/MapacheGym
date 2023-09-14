package com.proyecto.polotic.MapacheGym.entidades;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "pago")
public class Pago implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_pago")
    private int idPago;

    @Column(name = "fecha_pago")
    @Temporal(TemporalType.DATE)
    private LocalDate fechaPago;

    @Column(name = "validez")
    @Temporal(TemporalType.DATE)
    private LocalDate validez;

    @Column(name = "valor_abonado")
    private String valorAbonado;

    //RELACION (N-1) CON MEMBRESIA
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "membresia")
    private Membresia membresia;

    //RELACION (N-1) CON CLIENTE
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cliente")
    private Cliente cliente;

    public Pago() {
    }

    public Pago(int idPago, LocalDate fechaPago, LocalDate validez, String valorAbonado, Membresia membresia, Cliente cliente) {
        this.idPago = idPago;
        this.fechaPago = fechaPago;
        this.validez = validez;
        this.valorAbonado = valorAbonado;
        this.membresia = membresia;
        this.cliente = cliente;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public LocalDate getValidez() {
        return validez;
    }

    public void setValidez(LocalDate validez) {
        this.validez = validez;
    }

    public String getValorAbonado() {
        return valorAbonado;
    }

    public void setValorAbonado(String valorAbonado) {
        this.valorAbonado = valorAbonado;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}