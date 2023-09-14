package com.proyecto.polotic.MapacheGym.controladores;

import com.proyecto.polotic.MapacheGym.entidades.Empleado;
import com.proyecto.polotic.MapacheGym.entidades.Rol;
import com.proyecto.polotic.MapacheGym.seguridad.Validacion;
import com.proyecto.polotic.MapacheGym.servicios.EmpleadoServicio;
import com.proyecto.polotic.MapacheGym.servicios.RolServicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.ui.Model;



@RestController
@RequestMapping("/empleados")
public class EmpleadoControlador {

    @Autowired
    private EmpleadoServicio empleadoServicio;

    @Autowired
    private Validacion validar;

     @Autowired
    private RolServicio rolServicio;
    
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

        List<Rol> roles = rolServicio.getAll();

        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Nuevo Empleado");
        maw.addObject("view", "formsCreate/staff_form");
        maw.addObject("empleado", new Empleado());
        maw.addObject("roles", roles);
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


        List<Rol> roles = rolServicio.getAll();




        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Modificar Empleado");
        maw.addObject("view", "formsUpdate/staff_form");
        maw.addObject("empleado", empleado);
        maw.addObject("roles", roles);

        return maw;  
    }

    @PostMapping("/update")
    public RedirectView updateEmpleado(Empleado empleado,@RequestParam String password){
         if (password != null && password != "") {
    BCryptPasswordEncoder coder = new BCryptPasswordEncoder();
    String contraseniaCrypt = coder.encode(password);
    empleado.setContrasenia(contraseniaCrypt);
        }else{
        String oldPassword = empleadoServicio.traerContraseniaEmpleado(empleado.getId());
        empleado.setContrasenia(oldPassword);
        }
    empleadoServicio.modificarEmpleado(empleado);
    return new RedirectView("/empleados", true);

    }


    @PostMapping("/guardar")
    public RedirectView guardarEmpleado(@ModelAttribute Empleado empleado){

    String contrasenia = empleado.getContrasenia();
    
    BCryptPasswordEncoder coder = new BCryptPasswordEncoder();

    String contraseniaCrypt = coder.encode(contrasenia);

    empleado.setContrasenia(contraseniaCrypt);


        if (empleado.getDni() == null || empleado.getDni().isEmpty()) {
    String error = "Todos los campos del formulario deben estar completos";
    
    RedirectView redirectView = new RedirectView("/empleados", true);
    redirectView.addStaticAttribute("error", error); // Usa addStaticAttribute para agregar atributos
    
    return redirectView; // Retorna el RedirectView con los atributos
    }
            
        
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


}
