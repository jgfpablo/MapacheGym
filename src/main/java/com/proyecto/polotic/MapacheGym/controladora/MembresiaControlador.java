package com.proyecto.polotic.MapacheGym.controladora;

import com.proyecto.polotic.MapacheGym.modelo.Membresia;
import com.proyecto.polotic.MapacheGym.servicio.MembresiaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/membresia")
public class MembresiaControlador {

    @Autowired
    private MembresiaServicio membresiaServicio;

    @GetMapping
    @ResponseBody
    public List<Membresia> traerMembresias(){
        return membresiaServicio.traerMembresias();
    }

    //Este metodo carga las membresias para el codigo js
    @GetMapping("/membresias")
    public ResponseEntity<?> obtenerMembresias() {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-store");
        headers.setPragma("no-cache");
        headers.setExpires(0);

        try {
            List<Membresia> membresias = membresiaServicio.traerMembresias();
            return ResponseEntity.ok().headers(headers).body(membresias);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error interno del servidor"));
        }
    }

/*  ESTA ERA LA FORMA DE REGISTRAR MEMBRESIAS, PERO NO DEVUELVE MENSAJES AL CODIGO JS
    @PostMapping
    public Membresia crearMembresia(@RequestParam("descripcion") String descripcion,
                                    @RequestParam("tipoMembresia") String tipoMembresia,
                                    @RequestParam("diasSemanales") String diasSemanales,
                                    @RequestParam("precio") double precio) {

        if (descripcion.isEmpty() || tipoMembresia.isEmpty() || diasSemanales.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }

        Membresia membresia = new Membresia();
        membresia.setDescripcion(descripcion);
        membresia.setDiasSemanales(Integer.parseInt(diasSemanales));
        membresia.setTipoMembresia(tipoMembresia);
        membresia.setPrecio(precio);
        return membresiaServicio.crearMembresia(membresia);
    }
*/

    @PostMapping
    public ResponseEntity<?> crearMembresia(@RequestParam("descripcion") String descripcion,
                                            @RequestParam("tipoMembresia") String tipoMembresia,
                                            @RequestParam("diasSemanales") String diasSemanales,
                                            @RequestParam("precio") double precio) {

        if (descripcion.isEmpty() || tipoMembresia.isEmpty() || diasSemanales.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Todos los campos son obligatorios"));
        }

        Membresia membresia = new Membresia();
        membresia.setDescripcion(descripcion);
        membresia.setDiasSemanales(Integer.parseInt(diasSemanales));
        membresia.setTipoMembresia(tipoMembresia);
        membresia.setPrecio(precio);
        membresiaServicio.crearMembresia(membresia);

        return ResponseEntity.ok(Collections.singletonMap("message", "Membresia se registró exitosamente"));
    }

    @PutMapping
    public Membresia modificarMembresia(@RequestBody Membresia membresia){
        return membresiaServicio.modificarMembresia(membresia);
    }

    @DeleteMapping
    public void eliminarMembresia(@RequestBody Membresia membresia){
        membresiaServicio.eliminarMembresia(membresia);
    }

}
