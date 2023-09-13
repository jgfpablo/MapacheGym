package com.proyecto.polotic.MapacheGym.controladores;

import com.proyecto.polotic.MapacheGym.entidades.*;
import com.proyecto.polotic.MapacheGym.seguridad.Validacion;
import com.proyecto.polotic.MapacheGym.servicios.ClienteServicio;
import com.proyecto.polotic.MapacheGym.servicios.MembresiaServicio;
import com.proyecto.polotic.MapacheGym.servicios.PagoServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/pagos")
public class PagoControlador {

    @Autowired
    private PagoServicio pagoServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private MembresiaServicio membresiaServicio;

    @Autowired
    private Validacion validar;



    @GetMapping(value = {"/pagos/cliente"})
    public ModelAndView pagos(Model model,@RequestParam Integer id)
    {
        
        List<Pago> pagos = pagoServicio.findAllPagosCliente(id);


        // Integer idCliente;

        // if (!pagos.isEmpty()) {
        // Pago primerPago = pagos.get(0);
        // idCliente = primerPago.getCliente().getId();
        // } else {
        //     idCliente = 0; // podria causar una implosion. voto porque julian lo repare
        // }
        
        model.addAttribute("pagos", pagoServicio.traerPagos());
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Inicio");
        maw.addObject("view", "tables/payments_table");
        maw.addObject("pagos", pagos);
        maw.addObject("idCliente", id);



        return maw;  
    }



    @GetMapping(value = {"/pago/cliente/nuevo"})
    public ModelAndView nuevoPago(Model model,@RequestParam Integer idCliente)
    {
        List<Membresia> membresias = membresiaServicio.traerMembresias();

        model.addAttribute("pagos", pagoServicio.traerPagos());
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Nuevo Pago Cliente");
        maw.addObject("view", "formsCreate/payments_form");
        model.addAttribute("cliente", new Cliente());
        maw.addObject("membresias", membresias);
        maw.addObject("idCliente", idCliente);

        return maw;  
    }


    @PostMapping("/pagos/clientes/nuevo")
    public String guardarPago(@RequestParam Integer idMembresia,@RequestParam Integer idCliente){

        Cliente cliente = clienteServicio.traerClientePorId(idCliente);

        Membresia membresia = membresiaServicio.traerMembresiaPorId(idMembresia);


        // Membresia membresia = membresiaServicio.traerMembresiaPorId(idMembresia);

        Pago pago = new Pago();

        LocalDate fechaActual = LocalDate.now();
         // Calcular la fecha de validez (1 mes después de la fecha actual)
        LocalDate fechaValidez = fechaActual.plusMonths(1);
        cliente.setStatus("Activo");
        cliente.setFechaAlta(fechaActual);
        
        cliente.setDiasDisponibles(membresia.getDiasSemanales());
        cliente.setMembresia(membresia);

        pago.setMembresia(membresia);
        pago.setValorAbonado(membresia.getPrecio());
        pago.setCliente(cliente);
        pago.setFechaPago(fechaActual);
        pago.setValidez(fechaValidez);

        
         //ERROR GIGANTE --------------------------------------------------------------------------------
        //  if (!validar.validarDniCliente(cliente.getDni())) {
        //      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        //              .body(Collections.singletonMap("error", "Ya existe un cliente registrado para el DNI: " + cliente.getDni()));
        //   }


        // ERROR GIGANTE ------------------------------------------------------------------------------------
        //   if (cliente.getDni().isEmpty() || cliente.getNombre().isEmpty() || cliente.getApellido().isEmpty() || cliente.getTelefono().isEmpty()/* || idMembresia == 0*/) {
        //      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        //              .body(Collections.singletonMap("error", "Todos los campos son obligatorios, incluyendo idMembresia"));
        //  }


        clienteServicio.crearCliente(cliente);
        pagoServicio.crearPago(pago);

        return "redirect:/pagos/cliente?id="+cliente.getId();
    }



    @PostMapping(value = {"/pagos/eliminar"})
    public String eliminarPago(Model model,@RequestParam Integer idPago,@RequestParam Integer idCliente)
    {
        Pago pago = new Pago();
        pago = pagoServicio.traerPagoPorId(idPago);
        pagoServicio.eliminarPago(pago);

        return "redirect:/pagos/cliente?id="+idCliente;
    }





    @GetMapping(value = {"/pago/cliente/modificar"})
    public ModelAndView modificarPago(Model model,@RequestParam Integer idCliente,@RequestParam Integer idPago)
    {
        List<Membresia> membresias = membresiaServicio.traerMembresias();

        model.addAttribute("pagos", pagoServicio.traerPagos());
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Modificar Pago Cliente");
        maw.addObject("view", "formsUpdate/payments_form");
        model.addAttribute("cliente", new Cliente());
        maw.addObject("membresias", membresias);
        maw.addObject("idCliente", idCliente);
        maw.addObject("idPago", idPago);


        return maw;  
    }



    @PostMapping("/pagos/clientes/update")
    public String updatePago(@RequestParam Integer idMembresia,@RequestParam Integer idCliente,@RequestParam Integer idPago){

        Cliente cliente = clienteServicio.traerClientePorId(idCliente);
        Membresia membresia = membresiaServicio.traerMembresiaPorId(idMembresia);

        Pago pago = new Pago();

        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaValidez = fechaActual.plusMonths(1);
        cliente.setStatus("Activo");
        cliente.setFechaAlta(fechaActual);
        cliente.setId(idCliente);
        cliente.setDiasDisponibles(membresia.getDiasSemanales());
        cliente.setMembresia(membresia);

        pago.setMembresia(membresia);
        pago.setValorAbonado(membresia.getPrecio());
        pago.setCliente(cliente);
        pago.setFechaPago(fechaActual);
        pago.setValidez(fechaValidez);
        pago.setIdPago(idPago);
        

        
         //ERROR GIGANTE --------------------------------------------------------------------------------
        //  if (!validar.validarDniCliente(cliente.getDni())) {
        //      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        //              .body(Collections.singletonMap("error", "Ya existe un cliente registrado para el DNI: " + cliente.getDni()));
        //   }


        // ERROR GIGANTE ------------------------------------------------------------------------------------
        //   if (cliente.getDni().isEmpty() || cliente.getNombre().isEmpty() || cliente.getApellido().isEmpty() || cliente.getTelefono().isEmpty()/* || idMembresia == 0*/) {
        //      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        //              .body(Collections.singletonMap("error", "Todos los campos son obligatorios, incluyendo idMembresia"));
        //  }


        clienteServicio.modificarCliente(cliente);
        pagoServicio.modificarPago(pago);

        return "redirect:/pagos/cliente?id="+cliente.getId();
    }





    // @GetMapping
    // public List<Pago> traerPagos(){
    //     return pagoServicio.traerPagos();
    // }

    // @PostMapping
    // public String crearPago(@RequestParam("dni") String dni) {
    //     //ESTO TODAVIA NO HACE NADA...
    //     return null;
    // }


    // @PutMapping
    // public Pago modificarPago(@RequestBody Pago pago){
    //     return pagoServicio.modificarPago(pago);
    // }

    // @DeleteMapping
    // public void eliminarPago(@RequestBody Pago pago){
    //     pagoServicio.eliminarPago(pago);
    // }



}
