package com.proyecto.polotic.MapacheGym.controladora;

import com.proyecto.polotic.MapacheGym.modelo.Pago;
import com.proyecto.polotic.MapacheGym.servicio.ClienteServicio;
import com.proyecto.polotic.MapacheGym.servicio.PagoServicio;
import com.proyecto.polotic.MapacheGym.seguridad.Validacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pago")
public class PagoControlador {

    @Autowired
    private PagoServicio pagoServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private Validacion validar;

    @GetMapping
    public List<Pago> traerPagos(){
        return pagoServicio.traerPagos();
    }

    @PostMapping
    public String crearPago(@RequestParam("dni") String dni) {
        //ESTO TODAVIA NO HACE NADA...
        return null;
    }


    @PutMapping
    public Pago modificarPago(@RequestBody Pago pago){
        return pagoServicio.modificarPago(pago);
    }

    @DeleteMapping
    public void eliminarPago(@RequestBody Pago pago){
        pagoServicio.eliminarPago(pago);
    }



}
