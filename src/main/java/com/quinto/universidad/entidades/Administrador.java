package com.quinto.universidad.entidades;

import com.quinto.universidad.utilidades.Estado;
import com.quinto.universidad.utilidades.Rol;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminId;
    private String usuario;
    private String email;
    private String password;
    private Estado estado;

    @Enumerated(EnumType.STRING)
    private Rol rol;
}
