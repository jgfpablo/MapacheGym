package com.proyecto.polotic.MapacheGym.controladores;

import com.proyecto.polotic.MapacheGym.entidades.*;
import com.proyecto.polotic.MapacheGym.seguridad.Validacion;
import com.proyecto.polotic.MapacheGym.servicios.AsistenciaServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/asistencias")
public class AsistenciaControlador {

    @Autowired
    private AsistenciaServicio asistenciaServicio;

    @Autowired
    private Validacion validar;

    @GetMapping("/empleados")
    public ModelAndView traerAsistenciasEmpleados(){
        List<Asistencia> asistencias = asistenciaServicio.traerAsistenciasEmpleados();

        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Asistencias Empleados");
        maw.addObject("view", "tables/attendance_staff_table");
        maw.addObject("asistencias", asistencias);
        boolean showHeader = true;
        maw.addObject("showHeader", showHeader);
        return maw;  
    }

    @GetMapping("/clientes")
    public ModelAndView traerAsistenciasClientes(){
        List<Asistencia> asistencias = asistenciaServicio.traerAsistenciasClientes();

        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Asistencias Clientes");
        maw.addObject("view", "tables/attendance_client_table");
        maw.addObject("asistencias", asistencias);
        boolean showHeader = true;
        maw.addObject("showHeader", showHeader);
        return maw;  
    }

    @GetMapping("/nueva")
    public ModelAndView nuevaAsistencia(){
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Crear Aasistencia");
        maw.addObject("view", "formsCreate/attendance_form");
        return maw;  
    }

    @PostMapping("/crear")
    public RedirectView crearAsistencia(@RequestParam String dniAsistencia,RedirectAttributes redirectAttributes) {
    try {
        
        boolean noDniEmpleado = validar.validarDniEmpleado(dniAsistencia);
        boolean noDniCliente = validar.validarDniCliente(dniAsistencia);
        boolean clienteActivo = validar.validarClienteActivo(dniAsistencia);


        System.out.println("soy un empleado si soy false signifca que existo: "+noDniEmpleado);
        if (noDniCliente == false) {

        if (clienteActivo == false) {
            asistenciaServicio.crearAsistenciaCliente(dniAsistencia);

            redirectAttributes.addFlashAttribute("success", "Asistencia de cliente Registrada");
            redirectAttributes.addFlashAttribute("alertScript", true);
            return new RedirectView("/asistencias/nueva", true);
        }else{
            redirectAttributes.addFlashAttribute("error", "Cliente Inactivo");
            redirectAttributes.addFlashAttribute("alertScript", true);
            return new RedirectView("/asistencias/nueva", true);
            }


        } else if(noDniEmpleado == false) {
            asistenciaServicio.crearAsistenciaEmpleado(dniAsistencia);

            redirectAttributes.addFlashAttribute("success", "Asistencia de Empleado Regitrada");
            redirectAttributes.addFlashAttribute("alertScript", true);
            return new RedirectView("/asistencias/nueva", true);
            
        }else{
            redirectAttributes.addFlashAttribute("error", "No existen registros de esta persona");
            redirectAttributes.addFlashAttribute("alertScript", true);
            return new RedirectView("/asistencias/nueva", true);
        }
    } catch (Exception e) {
        System.out.println(e);
        redirectAttributes.addFlashAttribute("error", "No existen registros de esta persona");
        redirectAttributes.addFlashAttribute("alertScript", true);
        return new RedirectView("/asistencias/nueva", true);
    }
    }

    @PutMapping
    public Asistencia modificarAsistencia(@RequestBody Asistencia asistencia){
        return asistenciaServicio.modificarAsistencia(asistencia);
    }

    @DeleteMapping
    public void eliminarAsistencia(@RequestBody Asistencia asistencia){
        asistenciaServicio.eliminarAsistencia(asistencia);
    }

}













//  @PostMapping("/crear")
//     public ResponseEntity<?> crearAsistencia(@RequestParam String dniAsistencia) {
//         if (dniAsistencia.isEmpty()) {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "Complete el campo DNI"));
//         }
//         System.out.println(dniAsistencia);

//         boolean noDniEmpleado = validar.validarDniEmpleado(dniAsistencia);
//         boolean noDniCliente = validar.validarDniCliente(dniAsistencia);

//         try {
//             if (noDniEmpleado && noDniCliente) {
//                 throw new IllegalArgumentException("DNI no registrado previamente");
//             } else if (noDniCliente) {
//                 // Realiza las operaciones necesarias para crear la asistencia del empleado
//                 asistenciaServicio.crearAsistenciaEmpleado(dniAsistencia);
//                 return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", "Asistencia del empleado se registró exitosamente"));
//             } else if (noDniEmpleado) {
//                 // Realiza las operaciones necesarias para crear la asistencia del cliente
//                 asistenciaServicio.crearAsistenciaCliente(dniAsistencia);
//                 return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", "Asistencia del cliente se registró exitosamente"));
//             }
//         } catch (IllegalArgumentException e) {
//             // Captura la excepción lanzada desde el servicio y devuelve el mensaje de error correspondiente
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
//         }

//         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "DNI no válido"));
//     }