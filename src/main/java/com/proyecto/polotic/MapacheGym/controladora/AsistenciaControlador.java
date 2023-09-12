package com.proyecto.polotic.MapacheGym.controladora;

import com.proyecto.polotic.MapacheGym.modelo.Asistencia;
import com.proyecto.polotic.MapacheGym.servicio.AsistenciaServicio;
import com.proyecto.polotic.MapacheGym.seguridad.Validacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/asistencias")
public class AsistenciaControlador {

    @Autowired
    private AsistenciaServicio asistenciaServicio;

    @Autowired
    private Validacion validar;

    @GetMapping
    public List<Asistencia> traerAsistencias(){
        return asistenciaServicio.traerAsistencias();
    }

    @PostMapping
    public ResponseEntity<?> crearAsistencia(@RequestParam("dniAsistencia") String dni) {
        if (dni.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "Complete el campo DNI"));
        }

        boolean noDniEmpleado = validar.validarDniEmpleado(dni);
        boolean noDniCliente = validar.validarDniCliente(dni);

        try {
            if (noDniEmpleado && noDniCliente) {
                throw new IllegalArgumentException("DNI no registrado previamente");
            } else if (noDniCliente) {
                // Realiza las operaciones necesarias para crear la asistencia del empleado
                asistenciaServicio.crearAsistenciaEmpleado(dni);
                return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", "Asistencia del empleado se registró exitosamente"));
            } else if (noDniEmpleado) {
                // Realiza las operaciones necesarias para crear la asistencia del cliente
                asistenciaServicio.crearAsistenciaCliente(dni);
                return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", "Asistencia del cliente se registró exitosamente"));
            }
        } catch (IllegalArgumentException e) {
            // Captura la excepción lanzada desde el servicio y devuelve el mensaje de error correspondiente
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "DNI no válido"));
    }

/*  ESTA ERA LA FORMA DE REGISTRAR ASISTENCIA, PERO NO DEVUELVE MENSAJES AL CODIGO JS
    @PostMapping
    public Asistencia crearAsistencia(@RequestParam("dni-asistencia") String dni) {
        if (dni.isEmpty()) {
            throw new IllegalArgumentException("Complete el campo DNI");
        }

        boolean noDniEmpleado = validar.validarDniEmpleado(dni);
        boolean noDniCliente = validar.validarDniCliente(dni);

        if (noDniEmpleado && noDniCliente) {
            throw new IllegalArgumentException("DNI no registrado previamente");
        } else if (noDniCliente) {
            return asistenciaServicio.crearAsistenciaEmpleado(dni);
        } else if (noDniEmpleado) {
            return asistenciaServicio.crearAsistenciaCliente(dni);
        }

        throw new IllegalArgumentException("DNI no válido");
    }
*/
    @PutMapping
    public Asistencia modificarAsistencia(@RequestBody Asistencia asistencia){
        return asistenciaServicio.modificarAsistencia(asistencia);
    }

    @DeleteMapping
    public void eliminarAsistencia(@RequestBody Asistencia asistencia){
        asistenciaServicio.eliminarAsistencia(asistencia);
    }

}
