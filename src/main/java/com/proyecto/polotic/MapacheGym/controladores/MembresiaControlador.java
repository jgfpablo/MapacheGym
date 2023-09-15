package com.proyecto.polotic.MapacheGym.controladores;

import com.proyecto.polotic.MapacheGym.entidades.*;
import com.proyecto.polotic.MapacheGym.servicios.MembresiaServicio;

import org.aspectj.weaver.tools.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("membresias")
public class MembresiaControlador implements WebMvcConfigurer{

    @Autowired
    private MembresiaServicio membresiaServicio;


    @GetMapping("")
    public ModelAndView obtenerMembresias(Model model)
    {
        List<Membresia> membresia = membresiaServicio.traerMembresias();

        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Membresias");
        maw.addObject("view", "tables/membership_table");
        maw.addObject("membresias", membresia);
        boolean showHeader = true;
        maw.addObject("showHeader", showHeader);
        return maw;  
    }

    @GetMapping("/nueva")
    public ModelAndView nuevaMembresia(Membresia membresia) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Nueva Membresia");
        maw.addObject("view", "formsCreate/membership_form_create");
        maw.addObject("membresia", membresia);
        boolean showHeader = true;
        maw.addObject("showHeader", showHeader);
        return maw;   
    }

    @PostMapping("/guardar")
    public RedirectView crearMembresia(@ModelAttribute Membresia membresia,RedirectAttributes redirectAttributes){

        if (membresia.getTipoMembresia() == ""||membresia.getDescripcion() == ""||membresia.getPrecio()==""||membresia.getDiasSemanales() == null) {
            redirectAttributes.addFlashAttribute("error", "No fue posible crear la membresia asegurese de llenar todos los campos. Si el problema persiste contactese con el Administrador");
            redirectAttributes.addFlashAttribute("alertScript", true);
            return new RedirectView("/membresias/nueva", true);
    }

        membresiaServicio.crearMembresia(membresia);

        redirectAttributes.addFlashAttribute("success", "La membresia fue creada con exito");
        redirectAttributes.addFlashAttribute("alertScript", true);
        return new RedirectView("/membresias", true);
    }


    @PostMapping({"/eliminar"})
    public RedirectView eliminarMembresia(Membresia membresia,RedirectAttributes redirectAttributes){
    try {
        membresiaServicio.eliminarMembresia(membresia);

        redirectAttributes.addFlashAttribute("success", "La membresia fue eliminada con exito");
        redirectAttributes.addFlashAttribute("alertScript", true);
        return new RedirectView("/membresias", true);

    } catch (Exception e) {
    redirectAttributes.addFlashAttribute("error", "La membresia esta relacionada con un cliente. contactese con el administrador");
        redirectAttributes.addFlashAttribute("alertScript", true);
        return new RedirectView("/membresias", true);
    }

    }


    @GetMapping("/modificar-membresia")
    public ModelAndView modificarEmpleado(@RequestParam Integer id, Model model){
        Membresia membresia = membresiaServicio.traerMembresiaPorId(id);

        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Modificar Membresia");
        maw.addObject("view", "formsUpdate/membership_form");
        maw.addObject("membresia", membresia);
        boolean showHeader = true;
        maw.addObject("showHeader", showHeader);
        return maw;  
    }


    @PostMapping("/update")
    public RedirectView modificarMembresia( Membresia membresia,RedirectAttributes redirectAttributes){

        if (membresia.getTipoMembresia() == ""||membresia.getDescripcion() == ""||membresia.getPrecio() == ""||membresia.getDiasSemanales() == null) {
            redirectAttributes.addFlashAttribute("error", "No fue posible modificar la membresia, asegurese que todos los capos esten completos. Si el problema persiste contactese con su Administrador");
            redirectAttributes.addFlashAttribute("alertScript", true);
            return new RedirectView("/membresias/modificar-membresia?id="+membresia.getIdMembresia(), true);
        }
        membresiaServicio.modificarMembresia(membresia);

        redirectAttributes.addFlashAttribute("success", "La membresia fue modificada con exito");
        redirectAttributes.addFlashAttribute("alertScript", true);
        return new RedirectView("/membresias", true);
    }

    

}
