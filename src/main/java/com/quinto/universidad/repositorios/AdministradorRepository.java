package com.quinto.universidad.repositorios;

import com.quinto.universidad.entidades.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {
}
