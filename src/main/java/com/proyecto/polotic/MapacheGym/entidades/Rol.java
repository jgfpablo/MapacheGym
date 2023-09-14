package com.proyecto.polotic.MapacheGym.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @NotNull
    @NotBlank(message = "Campo obligatorio")
    @Column(unique = true)
    private String nombre;

    @Override
    public String toString(){
        return nombre;
    }

}
