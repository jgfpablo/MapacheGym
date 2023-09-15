package com.proyecto.polotic.MapacheGym.seguridad;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;
import jakarta.validation.*;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = UsuarioUnicoValidator.class)
@Documented
public @interface UsuarioUnico {
    String message() default "Ya existe un usuario con este usuario";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}