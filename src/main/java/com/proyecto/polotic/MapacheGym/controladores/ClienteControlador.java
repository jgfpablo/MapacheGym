package com.proyecto.polotic.MapacheGym.controladores;

import com.proyecto.polotic.MapacheGym.entidades.*;

import com.proyecto.polotic.MapacheGym.seguridad.Validacion;
import com.proyecto.polotic.MapacheGym.servicios.ClienteServicio;
import com.proyecto.polotic.MapacheGym.servicios.MembresiaServicio;
import com.proyecto.polotic.MapacheGym.servicios.PagoServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteControlador {

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private MembresiaServicio membresiaServicio;

    @Autowired
    private PagoServicio pagoServicio;

    @Autowired
    private Validacion validar;

    @GetMapping({""})
    public ModelAndView traerClientes(){
        List<Cliente> cliente = clienteServicio.traerClientes();
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Lista Clientes");
        maw.addObject("view", "tables/client_table");
        maw.addObject("clientes", cliente);
        boolean showHeader = true;
        maw.addObject("showHeader", showHeader);
        return maw;   
    }



    @GetMapping(value = {"/nuevo"})
    public ModelAndView nuevoEmpleado(Model model) {

        List<Membresia> membresias = membresiaServicio.traerMembresias();
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Nuevo Cliente");
        maw.addObject("view", "formsCreate/client_form");
        maw.addObject("cliente", new Cliente());
        maw.addObject("membresias", membresias);
        boolean showHeader = true;
        maw.addObject("showHeader", showHeader);
        return maw;   
    }

    
    @GetMapping("/modificar_cliente")
    public ModelAndView modificarCliente(@RequestParam Integer id,Model model){

        Cliente cliente = clienteServicio.traerClientePorId(id);
        
        List<Membresia> membresias = membresiaServicio.traerMembresias();
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Modificar Cliente");
        maw.addObject("view", "formsUpdate/client_form");
        maw.addObject("cliente", cliente);
        maw.addObject("membresias", membresias);
        boolean showHeader = true;
        maw.addObject("showHeader", showHeader);

        return maw;  
    }


    @PostMapping("/eliminar")
    public RedirectView eliminarCliente(@RequestParam Integer clienteId,RedirectAttributes redirectAttributes){
        Cliente cliente = clienteServicio.traerClientePorId(clienteId);
        cliente.setStatus("Inactivo");
        cliente.setId(clienteId);
        cliente.setDiasDisponibles(0);
        clienteServicio.crearCliente(cliente);
        redirectAttributes.addFlashAttribute("success", "El cliente fue eliminado con exito.");
        redirectAttributes.addFlashAttribute("alertScript", true);
        return new RedirectView("/clientes", true);
    }




@PostMapping("/guardar")
    public RedirectView guardarCliente(@ModelAttribute Cliente cliente,@RequestParam Integer idMembresia,RedirectAttributes redirectAttributes){

       try {
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

        if (cliente.getNombre()==""||cliente.getApellido()==""||cliente.getDni()==""||cliente.getTelefono()=="") {
            redirectAttributes.addFlashAttribute("error", "Todos los campos deben estar completos. Si el problema persiste contactese con su Administrador");
            redirectAttributes.addFlashAttribute("alertScript", true);
            return new RedirectView("/clientes/nuevo", true);
        }

        clienteServicio.crearCliente(cliente);
        pagoServicio.crearPago(pago);
        redirectAttributes.addFlashAttribute("success", "El cliente fue creado con exito.");
        redirectAttributes.addFlashAttribute("alertScript", true);
        return new RedirectView("/clientes", true);
        } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "El dni de este cliente ya existe. De no ser asi contactese con su Administrador");
        redirectAttributes.addFlashAttribute("alertScript", true);
        return new RedirectView("/clientes/nuevo", true);
        }
    }

@PostMapping("/update")
    public RedirectView updateEmpleado(Cliente cliente,@RequestParam Integer idMembresia,RedirectAttributes redirectAttributes){

            if (cliente.getNombre().isEmpty()||cliente.getApellido().isEmpty()||cliente.getDni().isEmpty()||cliente.getTelefono().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Todos los campos deben estar completos. Si el problema persiste contactese con su Administrador");
            redirectAttributes.addFlashAttribute("alertScript", true);
            return new RedirectView("/clientes/modificar_cliente?id=" + cliente.getId(), true);
    }
        Membresia membresia = membresiaServicio.traerMembresiaPorId(idMembresia);
        cliente.setMembresia(membresia);
        cliente.setDiasDisponibles(membresia.getDiasSemanales());
        clienteServicio.modificarCliente(cliente);
        redirectAttributes.addFlashAttribute("success", "El cliente fue modificado con exito.");
        redirectAttributes.addFlashAttribute("alertScript", true);
    return new RedirectView("/clientes", true);
    }












    //  @PostMapping({"/clientes/nuevo"})
    //  public ResponseEntity<?> crearCliente(@RequestParam("dniCliente") String dni,
    //                                        @RequestParam("nombreCliente") String nombre,
    //                                        @RequestParam("apellidoCliente") String apellido,
    //                                        @RequestParam("telefonoCliente") String telefono,
    //                                        @RequestParam("idMembresia") int idMembresia) {

    //      if (!validar.validarDniCliente(dni)) {
    //          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //                  .body(Collections.singletonMap("error", "Ya existe un cliente registrado para el DNI: " + dni));
    //      }

    //      if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()/* || idMembresia == 0*/) {
    //          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //                  .body(Collections.singletonMap("error", "Todos los campos son obligatorios, incluyendo idMembresia"));
    //      }
    //      Membresia membresia = membresiaServicio.traerMembresiaPorId(idMembresia);

    //      // Se obtiene la fecha actual
    //      LocalDate fechaActual = LocalDate.now();
    //      // Calcular la fecha de validez (1 mes después de la fecha actual)
    //      LocalDate fechaValidez = fechaActual.plusMonths(1);

    //      Cliente cliente = new Cliente();
    //      Pago pago =new Pago();

    //      cliente.setDni(dni);
    //      cliente.setNombre(nombre);
    //      cliente.setApellido(apellido);
    //      cliente.setTelefono(telefono);
    //      cliente.setStatus("Activo");
    //      cliente.setFechaAlta(fechaActual);
    //      cliente.setDiasDisponibles(membresia.getDiasSemanales());
    //      cliente.setMembresia(membresia);

    //      pago.setMembresia(membresia);
    //      pago.setValorAbonado(membresia.getPrecio());
    //      pago.setCliente(cliente);
    //      pago.setFechaPago(fechaActual);
    //      pago.setValidez(fechaValidez);

    // //     // Llamamos a los servicios para crear el cliente y el pago
    //      pagoServicio.crearPago(pago);
    //      clienteServicio.crearCliente(cliente);
    //      return ResponseEntity.ok(Collections.singletonMap("message", "Cliente se registró exitosamente"));
    //  }

// Este es el metodo que efectua la busqueda de clientes para el formulario de pagos
    @GetMapping("/datos")
    public ResponseEntity<?> buscarClientePorDni(@RequestParam("dni-busqueda") String dni) {
        try {
            Cliente cliente = clienteServicio.traerClientePorDni(dni);
            if (cliente != null) {
                // Si se encuentra el cliente, crea un objeto JSON con estos datos y los manda al js
                Map<String, Object> response = new HashMap<>();
                response.put("nombre", cliente.getNombre());
                response.put("apellido", cliente.getApellido());
                response.put("membresia", cliente.getMembresia().getTipoMembresia());
                response.put("valor", cliente.getMembresia().getPrecio());
                return ResponseEntity.ok(response);
            } else {
                // Si no se encuentra el cliente, devuelve una respuesta
                System.out.println("Cliente no encontrado para DNI: " + dni);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Cliente no encontrado"));
            }
        } catch (Exception e) {
            // Manejo de errores en caso de problemas con la búsqueda del cliente, por las dudas....
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Error interno del servidor"));
        }
    }

    // @PutMapping
    // public Cliente modificarCliente(@RequestBody Cliente cliente){
    //     return clienteServicio.modificarCliente(cliente);
    // }

    // @DeleteMapping
    // public void eliminarCliente(@RequestBody Cliente cliente){
    //     clienteServicio.eliminarCliente(cliente);
    // }

}
