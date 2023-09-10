package com.proyecto.polotic.MapacheGym.controladora;

import com.proyecto.polotic.MapacheGym.modelo.Cliente;
import com.proyecto.polotic.MapacheGym.modelo.Membresia;
import com.proyecto.polotic.MapacheGym.modelo.Pago;
import com.proyecto.polotic.MapacheGym.servicio.ClienteServicio;
import com.proyecto.polotic.MapacheGym.servicio.MembresiaServicio;
import com.proyecto.polotic.MapacheGym.servicio.PagoServicio;
import com.proyecto.polotic.MapacheGym.seguridad.Validacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cliente")
public class ClienteControlador {

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private MembresiaServicio membresiaServicio;

    @Autowired
    private PagoServicio pagoServicio;

    @Autowired
    private Validacion validar;

    @GetMapping
    public List<Cliente> traerClientes(){
        return clienteServicio.traerClientes();
    }

/*  ESTA ERA LA FORMA DE REGISTRAR CLIENTES, PERO NO DEVUELVE MENSAJES AL CODIGO JS
    @PostMapping
    public Cliente crearCliente(@RequestParam("dniCliente") String dni,
                                @RequestParam("nombreCliente") String nombre,
                                @RequestParam("apellidoCliente") String apellido,
                                @RequestParam("telefonoCliente") String telefono,
                                @RequestParam("idMembresia") int idMembresia){

        if (!validar.validarDniCliente(dni)) {
            throw new IllegalArgumentException("Ya existe un cliente con ese DNI");
        }

        if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || idMembresia == 0) {
            throw new IllegalArgumentException("Todos los campos son obligatorios, incluyendo idMembresia");
        }

        Membresia membresia = membresiaServicio.traerMembresiaPorId(idMembresia);

        // Se obtiene la fecha actual
        LocalDate fechaActual = LocalDate.now();
        // Calcular la fecha de validez (1 mes después de la fecha actual)
        LocalDate fechaValidez = fechaActual.plusMonths(1);

        Cliente cliente = new Cliente();
        Pago pago =new Pago();

        cliente.setDni(dni);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        cliente.setStatus("Activo");
        cliente.setFechaAlta(fechaActual);
        cliente.setDiasDisponibles(membresia.getDiasSemanales());
        cliente.setMembresia(membresia);

        pago.setMembresia(membresia);
        pago.setValorAbonado(membresia.getPrecio());
        pago.setCliente(cliente);
        pago.setFechaPago(fechaActual);
        pago.setValidez(fechaValidez);

        // Llamar a los servicios para crear el cliente y el pago
        pagoServicio.crearPago(pago);
        return clienteServicio.crearCliente(cliente);
    }
*/
    @PostMapping
    public ResponseEntity<?> crearCliente(@RequestParam("dniCliente") String dni,
                                          @RequestParam("nombreCliente") String nombre,
                                          @RequestParam("apellidoCliente") String apellido,
                                          @RequestParam("telefonoCliente") String telefono,
                                          @RequestParam("idMembresia") int idMembresia) {

        if (!validar.validarDniCliente(dni)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Ya existe un cliente registrado para el DNI: " + dni));
        }

        if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()/* || idMembresia == 0*/) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Todos los campos son obligatorios, incluyendo idMembresia"));
        }
        Membresia membresia = membresiaServicio.traerMembresiaPorId(idMembresia);

        // Se obtiene la fecha actual
        LocalDate fechaActual = LocalDate.now();
        // Calcular la fecha de validez (1 mes después de la fecha actual)
        LocalDate fechaValidez = fechaActual.plusMonths(1);

        Cliente cliente = new Cliente();
        Pago pago =new Pago();

        cliente.setDni(dni);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        cliente.setStatus("Activo");
        cliente.setFechaAlta(fechaActual);
        cliente.setDiasDisponibles(membresia.getDiasSemanales());
        cliente.setMembresia(membresia);

        pago.setMembresia(membresia);
        pago.setValorAbonado(membresia.getPrecio());
        pago.setCliente(cliente);
        pago.setFechaPago(fechaActual);
        pago.setValidez(fechaValidez);

        // Llamamos a los servicios para crear el cliente y el pago
        pagoServicio.crearPago(pago);
        clienteServicio.crearCliente(cliente);
        return ResponseEntity.ok(Collections.singletonMap("message", "Cliente se registró exitosamente"));
    }

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

    @PutMapping
    public Cliente modificarCliente(@RequestBody Cliente cliente){
        return clienteServicio.modificarCliente(cliente);
    }

    @DeleteMapping
    public void eliminarCliente(@RequestBody Cliente cliente){
        clienteServicio.eliminarCliente(cliente);
    }

}
