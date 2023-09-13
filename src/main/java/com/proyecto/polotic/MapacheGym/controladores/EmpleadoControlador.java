package com.proyecto.polotic.MapacheGym.controladores;

import com.proyecto.polotic.MapacheGym.entidades.Empleado;
import com.proyecto.polotic.MapacheGym.seguridad.Validacion;
import com.proyecto.polotic.MapacheGym.servicios.EmpleadoServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoControlador {

    @Autowired
    private EmpleadoServicio empleadoServicio;

    @Autowired
    private Validacion validar;
    
    @GetMapping(value = {""})
    public ModelAndView empleados(Model model)
    {
        model.addAttribute("empleados", empleadoServicio.traerEmpleados());
        ModelAndView maw = new ModelAndView();
        
        maw.setViewName("fragments/base");
        maw.addObject("title", "Inicio");
        maw.addObject("view", "tables/staff_table");
        return maw;  
    }

    @GetMapping(value = {"/nuevo"})
    public ModelAndView nuevoEmpleado(Model model) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Nuevo Empleado");
        maw.addObject("view", "formsCreate/staff_form");
        maw.addObject("empleado", new Empleado());
        return maw;   
    }

    @PostMapping("/eliminar")
    public RedirectView eliminarEmpleado(@RequestParam Integer id){
        Empleado empleado = new Empleado();
        empleado = empleadoServicio.traerEmpleadoPorId(id);
        empleadoServicio.eliminarEmpleado(empleado);
        return new RedirectView("/empleados", true);
    }

    @GetMapping("/modificar-empleado")
    public ModelAndView modificarEmpleado(@RequestParam Integer id, Model model){
        Empleado empleado = empleadoServicio.traerEmpleadoPorId(id);

        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Modificar Empleado");
        maw.addObject("view", "formsUpdate/staff_form");
        maw.addObject("empleado", empleado);
        return maw;  
    }

    @PostMapping("/update")
    public RedirectView updateEmpleado(Empleado empleado){
    empleadoServicio.modificarEmpleado(empleado);
    return new RedirectView("/empleados", true);

    }


    @PostMapping("/guardar")
    public RedirectView guardarEmpleado(@ModelAttribute Empleado empleado){
        //ResponseEntity<?>
                //Falta agregar logica de santiago , porque aun no la entiendo 
        // if (!validar.validarDniEmpleado(empleado.getDni())) {
        //     return ResponseEntity.status(HttpStatus.NOT_FOUND)
        //             .body(Collections.singletonMap("error", "Ya existe un empleado con ese DNI"));
        // }

        // if (empleado.getDni().isEmpty() || empleado.getNombre().isEmpty() || empleado.getApellido().isEmpty() || empleado.getTelefono().isEmpty() || empleado.getEmail().isEmpty() || empleado.getTipoEmpleado().isEmpty()) {
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        //             .body(Collections.singletonMap("error", "Todos los campos son obligatorios"));
        // }

        // if (!empleado.getTipoEmpleado().equals("Administrador") && !empleado.getTipoEmpleado().equals("Usuario") && !empleado.getTipoEmpleado().equals("Instructor")) {
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        //             .body(Collections.singletonMap("error", "El tipo de empleado debe ser 'Administrador', 'Usuario' o 'Instructor'"));
        // }

                empleadoServicio.crearEmpleado(empleado);
        // return ResponseEntity.ok(Collections.singletonMap("message", empleado.getTipoEmpleado() + " se registró exitosamente"));
        return new RedirectView("/empleados", true);
    }



      //FUNCIONA -----------------------------Pablo Frank----------------------------------------------------------------
    // @PostMapping("/empleados/guardar")
    // public String guardarEmpleado(@ModelAttribute Empleado empleado){
    //             //Falta agregar logica de santiago , porque aun no la entiendo 
    //             empleadoServicio.crearEmpleado(empleado);
    //     return "redirect:/empleados";
    // }



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




    // @PostMapping
    // public ResponseEntity<?> crearEmpleado(@RequestParam("dniEmpleado") String dni,
    //                                        @RequestParam("nombreEmpleado") String nombre,
    //                                        @RequestParam("apellidoEmpleado") String apellido,
    //                                        @RequestParam("telefonoEmpleado") String telefono,
    //                                        @RequestParam("emailEmpleado") String email,
    //                                        @RequestParam("tipoEmpleado") String tipoEmpleado) {

    //     if (!validar.validarDniEmpleado(dni)) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND)
    //                 .body(Collections.singletonMap("error", "Ya existe un empleado con ese DNI"));
    //     }

    //     if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || email.isEmpty() || tipoEmpleado.isEmpty()) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //                 .body(Collections.singletonMap("error", "Todos los campos son obligatorios"));
    //     }

    //     if (!tipoEmpleado.equals("Administrador") && !tipoEmpleado.equals("Usuario") && !tipoEmpleado.equals("Instructor")) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //                 .body(Collections.singletonMap("error", "El tipo de empleado debe ser 'Administrador', 'Usuario' o 'Instructor'"));
    //     }

    //     Empleado empleado = new Empleado();
    //     empleado.setDni(dni);
    //     empleado.setNombre(nombre);
    //     empleado.setApellido(apellido);
    //     empleado.setTelefono(telefono);
    //     empleado.setEmail(email);
    //     empleado.setTipoEmpleado(tipoEmpleado);
    //     empleado.setContrasenia(dni);

    //     int dniValue = Integer.parseInt(dni);
    //     String legajo = Integer.toHexString(dniValue);

    //     empleado.setLegajo(legajo);
    //     empleadoServicio.crearEmpleado(empleado);

    //     return ResponseEntity.ok(Collections.singletonMap("message", tipoEmpleado + " se registró exitosamente"));
    // }

    // @PutMapping
    // public Empleado modificarEmpleado(@RequestBody Empleado empleado){
    //     return empleadoServicio.modificarEmpleado(empleado);
    // }

    // @DeleteMapping
    // public void eliminarEmpleado(@RequestBody Empleado empleado){
    //     empleadoServicio.eliminarEmpleado(empleado);
    // }

}
