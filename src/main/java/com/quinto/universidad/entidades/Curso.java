package com.quinto.universidad.entidades;

import com.quinto.universidad.utilidades.Turno;
import lombok.*;


import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cursoId;
    private String materia;

    @Enumerated(EnumType.STRING)
    private Turno turno;

    @ManyToMany(mappedBy = "cursos")
    private Set<Alumno> Alumnos;

    @ManyToOne
    @JoinColumn(name="profesorId")
    private Profesor profesor;

    public Curso setMateria(String materia) {
        this.materia = materia;
        return null;
    }
}
