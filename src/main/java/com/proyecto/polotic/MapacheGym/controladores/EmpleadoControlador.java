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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



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
        boolean showHeader = true;
        maw.addObject("showHeader", showHeader);
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
        boolean showHeader = true;
        maw.addObject("showHeader", showHeader);
        return maw;   
    }


    @PostMapping("/eliminar")
    public RedirectView eliminarEmpleado(@RequestParam Integer id,RedirectAttributes redirectAttributes){
        Empleado empleado = new Empleado();
        empleado = empleadoServicio.traerEmpleadoPorId(id);
        empleadoServicio.eliminarEmpleado(empleado);
        redirectAttributes.addFlashAttribute("success", "El empleado fue eliminado.");
        redirectAttributes.addFlashAttribute("alertScript", true);
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
        boolean showHeader = true;
        maw.addObject("showHeader", showHeader);
        return maw;  
    }
//-------------------------------------------
@PostMapping("/update")
    public RedirectView updateEmpleado(Empleado empleado,@RequestParam String password,RedirectAttributes redirectAttributes){

            if (empleado.getNombre()==""||empleado.getApellido()==""||empleado.getDni()==""||empleado.getTelefono()==""||empleado.getUsuario()=="") {
            redirectAttributes.addFlashAttribute("error", "Todos los campos deben estar completos.");
            redirectAttributes.addFlashAttribute("alertScript", true);
            return new RedirectView("/empleados/modificar-empleado?id=" + empleado.getId(), true);
    }

        if (password != null && password != "") {
    BCryptPasswordEncoder coder = new BCryptPasswordEncoder();
    String contraseniaCrypt = coder.encode(password);
    empleado.setContrasenia(contraseniaCrypt);
        }else{
        String oldPassword = empleadoServicio.traerContraseniaEmpleado(empleado.getId());
        empleado.setContrasenia(oldPassword);
        }
    empleadoServicio.modificarEmpleado(empleado);

        redirectAttributes.addFlashAttribute("success", "El empleado fue modificado con exito.");
        redirectAttributes.addFlashAttribute("alertScript", true);
    return new RedirectView("/empleados", true);

    }
//------------------------------------------

@PostMapping("/guardar")
    public RedirectView guardarEmpleado(@ModelAttribute Empleado empleado,RedirectAttributes redirectAttributes){

        if (empleado.getNombre()==""||empleado.getApellido()==""||empleado.getDni()==""||empleado.getTelefono()==""||empleado.getUsuario()=="") {
            redirectAttributes.addFlashAttribute("error", "No fue posible crear un nuevo empleado");
            redirectAttributes.addFlashAttribute("alertScript", true);
            return new RedirectView("/empleados/nuevo", true);
        }

    String contrasenia = empleado.getContrasenia();
    BCryptPasswordEncoder coder = new BCryptPasswordEncoder();
    String contraseniaCrypt = coder.encode(contrasenia);
    empleado.setContrasenia(contraseniaCrypt);


    
            
        empleadoServicio.crearEmpleado(empleado);
        redirectAttributes.addFlashAttribute("success", "El empleado fue creado con exito");
        redirectAttributes.addFlashAttribute("alertScript", true);
        return new RedirectView("/empleados", true);
    }


}


