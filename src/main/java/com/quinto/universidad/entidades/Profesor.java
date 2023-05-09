package com.quinto.universidad.entidades;

import lombok.*;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long profesorId;
    @Column(unique = true)
    private Integer dni;
    private String apellido;
    private String estado;
    private LocalDateTime fechaAlta;
    private LocalDateTime fechaBaja;


    @OneToMany(mappedBy = "profesor")
    private List<Curso> cursos;

}
