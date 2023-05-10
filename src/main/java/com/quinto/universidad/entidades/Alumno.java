package com.quinto.universidad.entidades;

import com.quinto.universidad.utilidades.Estado;
import com.quinto.universidad.utilidades.Rol;
import lombok.*;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long alumnoId;
    @Column(unique = true)
    private Integer dni;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String direccion;
    private LocalDateTime fechaAlta;
    private LocalDateTime fechaBaja;
    private Estado estado;
    private String email;
    private String password;


    @ManyToMany
    @JoinTable(
            name = "alumno_curso",
            joinColumns = @JoinColumn(name = "alumnoId"),
            inverseJoinColumns = @JoinColumn(name = "cursoId")
    )
    Set<Curso> cursos;

    @Enumerated(EnumType.STRING)
    private Rol rol;

}
