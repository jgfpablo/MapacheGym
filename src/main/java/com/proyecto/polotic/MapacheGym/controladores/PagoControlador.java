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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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



    @GetMapping("/cliente")
    public ModelAndView pagos(Model model,@RequestParam Integer id)
    {
        List<Pago> pagos = pagoServicio.findAllPagosCliente(id);
        
        model.addAttribute("pagos", pagoServicio.traerPagos());
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Inicio");
        maw.addObject("view", "tables/payments_table");
        maw.addObject("pagos", pagos);
        maw.addObject("idCliente", id);
        boolean showHeader = true;
        maw.addObject("showHeader", showHeader);



        return maw;  
    }



    @GetMapping(value = {"/cliente/nuevo"})
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
        boolean showHeader = true;
        maw.addObject("showHeader", showHeader);

        return maw;  
    }


    @PostMapping("/cliente/nuevo")
    public RedirectView guardarPago(@RequestParam Integer idMembresia,@RequestParam Integer idCliente,RedirectAttributes redirectAttributes){

        if (idMembresia == null || idCliente == null) {
            redirectAttributes.addFlashAttribute("error", "No fue posible guardar el pago. Contactese con su Administrador");
            redirectAttributes.addFlashAttribute("alertScript", true);
            return new RedirectView("/empleados/nuevo", true);
        }

        Cliente cliente = clienteServicio.traerClientePorId(idCliente);

        Membresia membresia = membresiaServicio.traerMembresiaPorId(idMembresia);

        Pago pago = new Pago();

        LocalDate fechaActual = LocalDate.now();
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

        clienteServicio.crearCliente(cliente);
        pagoServicio.crearPago(pago);

        redirectAttributes.addFlashAttribute("success", "El Pago fue registrado con exito");
        redirectAttributes.addFlashAttribute("alertScript", true);
                return new RedirectView("/pagos/cliente?id="+cliente.getId(), true);

    }



    @PostMapping(value = {"/eliminar"})
    public RedirectView eliminarPago(Model model,@RequestParam Integer idPago,@RequestParam Integer idCliente,RedirectAttributes redirectAttributes)
    {

        if (idPago == null || idCliente == null) {
            redirectAttributes.addFlashAttribute("error", "No fue posible eliminar el pago. Contactese con su Administrador");
            redirectAttributes.addFlashAttribute("alertScript", true);
            return new RedirectView("/empleados/nuevo", true);
    }

        Pago pago = new Pago();
        pago = pagoServicio.traerPagoPorId(idPago);

        pagoServicio.eliminarPago(pago);

        List<Pago> todosLosPagos;
        todosLosPagos = pagoServicio.findAllPagosCliente(idCliente);

        if (todosLosPagos.size() == 0) {
            
            Cliente cliente = new Cliente();
            cliente = clienteServicio.traerClientePorId(idCliente);
            cliente.setStatus("Inactivo");
            cliente.setDiasDisponibles(0);
            
            clienteServicio.modificarCliente(cliente);
        }

        redirectAttributes.addFlashAttribute("success", "El Pago fue eliminado con exito");
        redirectAttributes.addFlashAttribute("alertScript", true);
        return new RedirectView("/pagos/cliente?id="+idCliente, true);
    }





    @GetMapping(value = {"/cliente/modificar"})
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



    @PostMapping("/clientes/update")
    public RedirectView updatePago(@RequestParam Integer idMembresia,@RequestParam Integer idCliente,@RequestParam Integer idPago,RedirectAttributes redirectAttributes){


            if (idPago == null || idCliente == null||idMembresia == null) {
            redirectAttributes.addFlashAttribute("error", "No fue posible modificar el pago. Contactese con su Administrador");
            redirectAttributes.addFlashAttribute("alertScript", true);
            return new RedirectView("/empleados/nuevo", true);
    }

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
        

        clienteServicio.modificarCliente(cliente);
        pagoServicio.modificarPago(pago);
        
        redirectAttributes.addFlashAttribute("success", "El Pago fue modificado con exito");
        redirectAttributes.addFlashAttribute("alertScript", true);
            return new RedirectView("/pagos/cliente?id="+cliente.getId(), true);
    }


}
