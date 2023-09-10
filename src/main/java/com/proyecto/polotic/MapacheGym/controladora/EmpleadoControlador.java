package com.proyecto.polotic.MapacheGym.controladora;

import com.proyecto.polotic.MapacheGym.modelo.Empleado;
import com.proyecto.polotic.MapacheGym.servicio.EmpleadoServicio;
import com.proyecto.polotic.MapacheGym.seguridad.Validacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/empleado")
public class EmpleadoControlador {

    @Autowired
    private EmpleadoServicio empleadoServicio;

    @Autowired
    private Validacion validar;

    @GetMapping
    public List<Empleado> traerEmpleados(){
        return empleadoServicio.traerEmpleados();
    }

/*  ESTA ERA LA FORMA DE REGISTRAR EMPLEADO, PERO NO DEVUELVE MENSAJES AL CODIGO JS
    @PostMapping
    public Empleado crearEmpleado(@RequestParam("dniEmpleado") String dni,
                                  @RequestParam("nombreEmpleado") String nombre,
                                  @RequestParam("apellidoEmpleado") String apellido,
                                  @RequestParam("telefonoEmpleado") String telefono,
                                  @RequestParam("emailEmpleado") String email,
                                  @RequestParam("tipoEmpleado") String tipoEmpleado) {

        if (!validar.validarDniEmpleado(dni)) {
            throw new IllegalArgumentException("Ya existe un empleado con ese DNI");
        }

        if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || email.isEmpty() || tipoEmpleado.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }

        if (!tipoEmpleado.equals("Administrador") && !tipoEmpleado.equals("Usuario") && !tipoEmpleado.equals("Instructor")) {
            throw new IllegalArgumentException("El tipo de empleado debe ser 'Administrador', 'Usuario' o 'Instructor'");
        }

        Empleado empleado = new Empleado();
        empleado.setDni(dni);
        empleado.setNombre(nombre);
        empleado.setApellido(apellido);
        empleado.setTelefono(telefono);
        empleado.setEmail(email);
        empleado.setTipoEmpleado(tipoEmpleado);
        empleado.setContrasenia(dni);

        int dniValue = Integer.parseInt(dni);
        String legajo = Integer.toHexString(dniValue);

        empleado.setLegajo(legajo);
        return empleadoServicio.crearEmpleado(empleado);
    }
*/
    @PostMapping
    public ResponseEntity<?> crearEmpleado(@RequestParam("dniEmpleado") String dni,
                                           @RequestParam("nombreEmpleado") String nombre,
                                           @RequestParam("apellidoEmpleado") String apellido,
                                           @RequestParam("telefonoEmpleado") String telefono,
                                           @RequestParam("emailEmpleado") String email,
                                           @RequestParam("tipoEmpleado") String tipoEmpleado) {

        if (!validar.validarDniEmpleado(dni)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "Ya existe un empleado con ese DNI"));
        }

        if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || email.isEmpty() || tipoEmpleado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Todos los campos son obligatorios"));
        }

        if (!tipoEmpleado.equals("Administrador") && !tipoEmpleado.equals("Usuario") && !tipoEmpleado.equals("Instructor")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "El tipo de empleado debe ser 'Administrador', 'Usuario' o 'Instructor'"));
        }

        Empleado empleado = new Empleado();
        empleado.setDni(dni);
        empleado.setNombre(nombre);
        empleado.setApellido(apellido);
        empleado.setTelefono(telefono);
        empleado.setEmail(email);
        empleado.setTipoEmpleado(tipoEmpleado);
        empleado.setContrasenia(dni);

        int dniValue = Integer.parseInt(dni);
        String legajo = Integer.toHexString(dniValue);

        empleado.setLegajo(legajo);
        empleadoServicio.crearEmpleado(empleado);

        return ResponseEntity.ok(Collections.singletonMap("message", tipoEmpleado + " se registró exitosamente"));
    }

    @PutMapping
    public Empleado modificarEmpleado(@RequestBody Empleado empleado){
        return empleadoServicio.modificarEmpleado(empleado);
    }

    @DeleteMapping
    public void eliminarEmpleado(@RequestBody Empleado empleado){
        empleadoServicio.eliminarEmpleado(empleado);
    }

}
