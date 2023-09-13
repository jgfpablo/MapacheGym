package com.proyecto.polotic.MapacheGym.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private String password;

    @ManyToOne
    @NotNull
    private Rol rol;

}
