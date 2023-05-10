package com.quinto.universidad.repositorios;

import com.quinto.universidad.entidades.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    @Query("SELECT a FROM Alumno a WHERE a.nombre  = :letra")
    public List<Alumno> buscarPorLetra(@Param("letra") String letra);

    @Query(value = "SELECT a FROM Alumno a INNER JOIN alumno_curso ac ON ac.alumnoId = a.alumnoId WHERE ac.cursoId = :cursoId", nativeQuery = true )
    public List<Alumno> buscarPorCurso(@Param("cursoId") long cursoId);

    @Query("SELECT a FROM Alumno a WHERE a.email = :email")
    public Alumno buscarPorEmail(@Param("email") String email);

    @Query("SELECT a FROM Alumno a WHERE a.nombre = :nombre")
    public List<Alumno> buscarPorNombre(String nombre);
}
