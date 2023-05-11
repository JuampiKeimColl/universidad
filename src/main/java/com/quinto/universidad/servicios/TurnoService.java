package com.quinto.universidad.servicios;

import com.quinto.universidad.entidades.Curso;
import com.quinto.universidad.entidades.Profesor;
import com.quinto.universidad.exceptions.AtrapaErrores;
import com.quinto.universidad.repositorios.CursoRepository;
import com.quinto.universidad.utilidades.Estado;
import com.quinto.universidad.utilidades.Rol;
import com.quinto.universidad.utilidades.Turno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    @Autowired
    CursoRepository cursoRepository;
    public List<Turno> getTurnos() {
        return Arrays.asList(Turno.values());
    }


//    @Transactional
//    public void crearTurno(String materia) throws AtrapaErrores {
//        Curso curso = new Curso();
//        //validarCurso(materia);
//
//        curso.setMateria(materia);
//
//        cursoRepository.save(curso);
//
//    }

//    @Transactional
//    public void modificarCurso(long cursoId, String materia) throws AtrapaErrores{
//        Optional<Curso> rta = cursoRepository.findById(cursoId);
//        //validarDniProfesor(dni);
//
//        if (rta.isPresent()){
//            Curso curso = rta.get();
//            curso.setMateria(materia);
//
//
//            cursoRepository.save(curso);
//        }
//    }
}
