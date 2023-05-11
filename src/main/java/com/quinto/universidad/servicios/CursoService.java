package com.quinto.universidad.servicios;

import com.quinto.universidad.entidades.Curso;
import com.quinto.universidad.entidades.Profesor;
import com.quinto.universidad.utilidades.Turno;
import com.quinto.universidad.exceptions.AtrapaErrores;
import com.quinto.universidad.repositorios.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CursoService {
    @Autowired
    CursoRepository cursoRepository;

    public Curso getOne(long cursoId){

        return cursoRepository.getOne(cursoId);
    }
    public void eliminarCurso(long cursoId){

        cursoRepository.deleteById(cursoId);
    }

    @Transactional
    public void crearCurso(String materia) throws AtrapaErrores {
        Curso curso = new Curso();
        //validarCurso(materia);

        curso.setMateria(materia);

        cursoRepository.save(curso);

    }
    @Transactional
    public void crearCurso(String materia, Turno turno, Profesor profesor) throws AtrapaErrores{
        Curso curso = new Curso();
        //validarMateriaCurso(materia);
        curso.setMateria(materia);
        curso.setTurno(turno);
        curso.setProfesor(profesor);

        cursoRepository.save(curso);

    }

    @Transactional
    public void modificarCurso(long cursoId, String materia) throws AtrapaErrores{
        Optional<Curso> rta = cursoRepository.findById(cursoId);
        //validarDniProfesor(dni);

        if (rta.isPresent()){
            Curso curso = rta.get();
            curso.setMateria(materia);


            cursoRepository.save(curso);
        }
    }

    public List<Curso> listarCursos(){

        return cursoRepository.findAll();
    }

//    private void validarMateriaCurso(Long cursoId) throws AtrapaErrores {
//        if (cursoId. null || cursoId.isEmpty()){
//            throw new AtrapaErrores("La materia del Curso es obligatoria. ");
//
//        }
//
//    }

}
