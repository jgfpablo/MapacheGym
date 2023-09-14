package com.proyecto.polotic.MapacheGym.seguridad;

import com.proyecto.polotic.MapacheGym.dto.*;
import com.proyecto.polotic.MapacheGym.repositorios.*;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailUnicoValidator implements ConstraintValidator<EmailUnico, Object> {

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @Override
    public void initialize(final EmailUnico constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object objeto, final ConstraintValidatorContext context) {
        final RegistroDto registro = (RegistroDto) objeto;
        boolean esValido = ! empleadoRepositorio.existsByEmail(registro.getEmail());

        if (! esValido) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode( "email" ).addConstraintViolation();
       }

       return esValido;
    }

}