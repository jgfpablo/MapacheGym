package com.proyecto.polotic.MapacheGym.servicios;

import com.proyecto.polotic.MapacheGym.entidades.Asistencia;
import com.proyecto.polotic.MapacheGym.entidades.Cliente;
import com.proyecto.polotic.MapacheGym.entidades.Empleado;
import com.proyecto.polotic.MapacheGym.entidades.Pago;
import com.proyecto.polotic.MapacheGym.repositorios.AsistenciaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
public class AsistenciaServicio {

    @Autowired
    private AsistenciaRepositorio asistenciaRepositorio;

    @Autowired ClienteServicio clienteServicio;

    @Autowired EmpleadoServicio empleadoServicio;

    public Asistencia crearAsistencia(Asistencia asistencia){
        return asistenciaRepositorio.save(asistencia);
    }


    @Transactional
    public Asistencia crearAsistenciaCliente(String dni) {
        Asistencia asistencia = new Asistencia();
        LocalDate fechaActual = LocalDate.now();
        //LocalDate fechaActual = LocalDate.of(2023, 9, 30);
        LocalTime hora = LocalTime.now();
        DayOfWeek diaHoy = fechaActual.getDayOfWeek();

        String[] nombresDias = {
            "Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
        };

        String nombreDia = nombresDias[diaHoy.getValue()];



        Cliente cliente = clienteServicio.traerClientePorDni(dni);
        Pago ultimoPago = clienteServicio.obtenerUltimoPago(cliente.getId());
        if (fechaActual.isBefore(ultimoPago.getValidez())) {
            // La fecha actual es anterior a la fecha de validez, por lo que el acceso es válido.
            LocalDate inicioSemana = fechaActual.with(DayOfWeek.MONDAY);
            // Obtenemos el último registro de asistencia del cliente
            Asistencia ultimaAsistencia = ultimaAsistenciaCliente(cliente);
            // Verificamos si es una nueva semana (si no hay registro de asistencia previo en esta semana)
            if (ultimaAsistencia == null || ultimaAsistencia.getFecha().isBefore(inicioSemana)) {
                cliente.setDiasDisponibles(cliente.getMembresia().getDiasSemanales());
                clienteServicio.modificarCliente(cliente);
            }
            if (cliente.getDiasDisponibles() > 0) {
                // Si el cliente tiene días disponibles para asistir esta semana
                asistencia.setAsistenciaCliente(cliente);
                asistencia.setDia(nombreDia);
                asistencia.setFecha(fechaActual);
                asistencia.setHoraInicio(hora);
                asistencia.setHoraFin(hora.plusHours(2));
                crearAsistencia(asistencia);
                cliente.setDiasDisponibles(cliente.getDiasDisponibles() - 1);
                clienteServicio.modificarCliente(cliente);
            } else {
                throw new IllegalArgumentException("Amigo, no tenés más días disponibles para asistir esta semana.");
            }
        } else {
            throw new IllegalArgumentException("El acceso ha caducado.");
        }
        return asistencia;
    }

    @Transactional
    public Asistencia crearAsistenciaEmpleado(String dni) {
        // Es un empleado
        Asistencia asistencia = new Asistencia();
        LocalDate fechaActual = LocalDate.now();
        LocalTime hora = LocalTime.now();
        DayOfWeek diaHoy = fechaActual.getDayOfWeek();
         String[] nombresDias = {
            "Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
        };
        String nombreDia = nombresDias[diaHoy.getValue()];
        Empleado empleado = empleadoServicio.traerEmpleadoPorDni(dni);
        // Verificamos si el empleado ya ha registrado asistencia hoy...
        Asistencia asistenciaExistente = obtenerAsistenciaEmpleado(empleado, fechaActual);
        if (asistenciaExistente == null || asistenciaExistente.getHoraFin() != null) {
            // Si no hay asistencia para hoy o la asistencia previa ya se registro la salida
            asistencia.setAsistenciaEmpleado(empleado);
            asistencia.setDia(nombreDia); //ATENTI ACA
            asistencia.setFecha(fechaActual);
            asistencia.setHoraInicio(hora);
            return crearAsistencia(asistencia);
        } else {
            // Actualizar la horaFin, osea.. se marca la salida
            asistenciaExistente.setHoraFin(hora);
            modificarAsistencia(asistenciaExistente);
            return asistenciaExistente;
        }

    }

    public Asistencia modificarAsistencia(Asistencia asistencia){
        return asistenciaRepositorio.save(asistencia);
    }

    public List<Asistencia> traerAsistencias(){
        return asistenciaRepositorio.findAll();
    }


    //En teoria trae todas las asistencias relacionadas con empleados para la tabla
    public List<Asistencia> traerAsistenciasEmpleados(){
        return asistenciaRepositorio.findAsistenciasEmpleados();
    }

    public List<Asistencia> traerAsistenciasClientes(){
        return asistenciaRepositorio.findAsistenciasClientes();
    }

    public void eliminarAsistencia(Asistencia asistencia){
        asistenciaRepositorio.delete(asistencia);
    }

//Esto busca las asistencias del empleado en una fecha particular.
// Inclusive si el empleado tuvo varios ingresos y salidas el mismo dia
    public Asistencia obtenerAsistenciaEmpleado(Empleado empleado, LocalDate fecha) {
        List<Asistencia> asistencias = asistenciaRepositorio.findAllByAsistenciaEmpleadoAndFecha(empleado, fecha);
        // Ordena la lista de asistencias por horaInicio en orden descendente
        asistencias.sort(Comparator.comparing(Asistencia::getHoraInicio).reversed());
        // Verifica si hay asistencias en la lista
        if (!asistencias.isEmpty()) {
            // la última según la hora de inicio...
            return asistencias.get(0);
        } else {
            // No hay asistencias registradas en esta fecha
            return null;
        }
    }

//Trae la ultima asistencia del cliente
    public Asistencia ultimaAsistenciaCliente(Cliente cliente) {
        Pageable pageable = PageRequest.of(0, 1);
        List<Asistencia> asistencias = asistenciaRepositorio.findUltimasAsistenciasCliente(cliente, pageable);
        return asistencias.isEmpty() ? null : asistencias.get(0);
    }

}
