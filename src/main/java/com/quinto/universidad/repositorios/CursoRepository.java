package com.quinto.universidad.repositorios;

import com.quinto.universidad.entidades.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
