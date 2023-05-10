package com.quinto.universidad.entidades;

import com.quinto.universidad.utilidades.Estado;
import com.quinto.universidad.utilidades.Rol;
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
    private Estado estado;
    private LocalDateTime fechaAlta;
    private LocalDateTime fechaBaja;
    private String email;
    private String password;

    @OneToMany(mappedBy = "profesor")
    private List<Curso> cursos;

    @Enumerated(EnumType.STRING)
    private Rol rol;

}
