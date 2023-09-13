package com.proyecto.polotic.MapacheGym.dto;

import com.proyecto.polotic.MapacheGym.seguridad.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Confirmar
@EmailUnico
public class RegistroDto {

    @NotNull
    @NotEmpty(message = "Ingrese una dirección de correo electrónico")
    @Email(message = "Ingrese una dirección de correo electrónico válida")
    private String email;

    @NotNull
    @NotEmpty(message = "Ingrese una contraseña")
    private String password;

    private String confirmar;
    
    private String recaptcha;
}
