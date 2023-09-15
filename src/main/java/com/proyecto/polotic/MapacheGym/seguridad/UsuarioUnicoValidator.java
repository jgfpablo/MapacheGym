package com.proyecto.polotic.MapacheGym.seguridad;

import com.proyecto.polotic.MapacheGym.dto.*;
import com.proyecto.polotic.MapacheGym.repositorios.*;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;

public class UsuarioUnicoValidator implements ConstraintValidator<UsuarioUnico, Object> {

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @Override
    public void initialize(final UsuarioUnico constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object objeto, final ConstraintValidatorContext context) {
        final RegistroDto registro = (RegistroDto) objeto;
        boolean esValido = ! empleadoRepositorio.existsByUsuario(registro.getUsuario());

        if (! esValido) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode( "usuario" ).addConstraintViolation();
       }

       return esValido;
    }

}