package com.proyecto.polotic.MapacheGym.entidades;

import jakarta.persistence.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "membresia")
public class Membresia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_membresia")
    private int idMembresia;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tipo_membresia")
    private String tipoMembresia;

    @Column(name = "precio")
    private String precio;

    @Column(name = "dias_semanales")
    @Min(value = 1, message = "El valor mínimo permitido es 1")
    @Max(value = 7, message = "El valor máximo permitido es 7")
    private Integer diasSemanales;

    //RELACION (1-N) CON CLIENTE
    @OneToMany(mappedBy = "membresia")
    private List<Cliente> cliente;

    //RELACION (1-n) CON PAGOS
    @OneToMany(mappedBy = "membresia")
    private List<Pago> pago;

    public Membresia() {
    }

    public Membresia(int idMembresia, String descripcion, String tipoMembresia, String precio, Integer diasSemanales, List<Cliente> cliente, List<Pago> pago) {
        this.idMembresia = idMembresia;
        this.descripcion = descripcion;
        this.tipoMembresia = tipoMembresia;
        this.precio = precio;
        this.diasSemanales = diasSemanales;
        this.cliente = cliente;
        this.pago = pago;
    }

    public int getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(int idMembresia) {
        this.idMembresia = idMembresia;
    }

    public String getTipoMembresia() {
        return tipoMembresia;
    }

    public void setTipoMembresia(String tipoMembresia) {
        this.tipoMembresia = tipoMembresia;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public List<Cliente> getCliente() {
        return cliente;
    }

    public void setCliente(List<Cliente> cliente) {
        this.cliente = cliente;
    }

    public List<Pago> getPago() {
        return pago;
    }

    public void setPago(List<Pago> pago) {
        this.pago = pago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDiasSemanales() {
        return diasSemanales;
    }

    public void setDiasSemanales(Integer diasSemanales) {
        this.diasSemanales = diasSemanales;
    }
}
