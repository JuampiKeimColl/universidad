package com.quinto.universidad.repositorios;

import com.quinto.universidad.entidades.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    @Query("DELETE FROM Profesor p WHERE p.cursos = :curso")
    public Profesor removerProfesorDelCurso(@Param("curso") String curso);

    @Query("SELECT p FROM Profesor p WHERE p.email = :email")
    public Profesor buscarPorEmail(@Param("email") String email);
}
