package com.proyecto.polotic.MapacheGym.controladores;

import com.proyecto.polotic.MapacheGym.entidades.*;
import com.proyecto.polotic.MapacheGym.servicios.MembresiaServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

//  @RestController
//  @RequestMapping("/membresias")  --------------- Preguntar Chatgpt
@RestController
@RequestMapping("/membresias")
public class MembresiaControlador {

    @Autowired
    private MembresiaServicio membresiaServicio;

    @GetMapping
    @ResponseBody
    public List<Membresia> traerMembresias(){
        return membresiaServicio.traerMembresias();
    }

    @GetMapping("/membresias")
    public ModelAndView obtenerMembresias(Model model)
    {
        List<Membresia> membresia = membresiaServicio.traerMembresias();

        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Membresias");
        maw.addObject("view", "tables/membership_table");
        maw.addObject("membresias", membresia);
        return maw;  
    }

      // PABLO FRANK ---------------------------- NO ENTENDI EXACTAMENTE PARA QUE EL BORRADO DE CACHE, DE MOMENTO LO HAGO FUNCIONAR NOMAS
    //Este metodo carga las membresias para el codigo js
    // @GetMapping("/membresias")
    // public ResponseEntity<?> obtenerMembresias() {
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setCacheControl("no-store");
    //     headers.setPragma("no-cache");
    //     headers.setExpires(0);

    //     try {
    //         List<Membresia> membresias = membresiaServicio.traerMembresias();
    //         return ResponseEntity.ok().headers(headers).body(membresias);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //                 .body(Collections.singletonMap("error", "Error interno del servidor"));
    //     }
    // }


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

    @GetMapping(value = {"/nueva"})
    public ModelAndView nuevaMembresia(Model model) {
        Membresia membresia = new Membresia();
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Nueva Membresia");
        maw.addObject("view", "formsCreate/membership_form");
        maw.addObject("membresia", membresia);
        return maw;   
    }

    @PostMapping(value = {"/guardar"})
    public ResponseEntity<?> crearMembresia(@ModelAttribute Membresia membresia){
    
        membresiaServicio.crearMembresia(membresia);
        if (membresia.getDescripcion().isEmpty() || membresia.getTipoMembresia().isEmpty() || membresia.getDiasSemanales() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Todos los campos son obligatorios"));
        }

        return ResponseEntity.ok(Collections.singletonMap("message", "Membresia se registró exitosamente"));
    }


    @PostMapping({"/eliminar"})
    public String eliminarMembresia(Membresia membresia){
        membresiaServicio.eliminarMembresia(membresia);

        return "redirect:/membresias";
    }


    @GetMapping("/modificar-membresia")
    public ModelAndView modificarEmpleado(@RequestParam Integer id, Model model){
        Membresia membresia = membresiaServicio.traerMembresiaPorId(id);

        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Modificar Membresia");
        maw.addObject("view", "formsUpdate/membership_form");
        maw.addObject("membresia", membresia);
        return maw;  
    }


    // @PostMapping
    // public ResponseEntity<?> crearMembresia(@RequestParam("descripcion") String descripcion,
    //                                         @RequestParam("tipoMembresia") String tipoMembresia,
    //                                         @RequestParam("diasSemanales") String diasSemanales,
    //                                         @RequestParam("precio") double precio) {

    //     if (descripcion.isEmpty() || tipoMembresia.isEmpty() || diasSemanales.isEmpty()) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //                 .body(Collections.singletonMap("error", "Todos los campos son obligatorios"));
    //     }

    //     Membresia membresia = new Membresia();
    //     membresia.setDescripcion(descripcion);
    //     membresia.setDiasSemanales(Integer.parseInt(diasSemanales));
    //     membresia.setTipoMembresia(tipoMembresia);
    //     membresia.setPrecio(precio);
    //     membresiaServicio.crearMembresia(membresia);

    //     return ResponseEntity.ok(Collections.singletonMap("message", "Membresia se registró exitosamente"));
    // }

    @PostMapping("/update")
    public String modificarMembresia( Membresia membresia){
        membresiaServicio.modificarMembresia(membresia);
        return "redirect:/membresias";
    }

    

}
