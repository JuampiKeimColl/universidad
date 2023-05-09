package com.quinto.universidad.servicios;

import com.quinto.universidad.entidades.Curso;
import com.quinto.universidad.entidades.Profesor;
import com.quinto.universidad.entidades.Turno;
import com.quinto.universidad.exceptions.AtrapaErrores;
import com.quinto.universidad.repositorios.CursoRepository;
import com.quinto.universidad.repositorios.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProfesorService {
    @Autowired
    ProfesorRepository profesorRepository;
    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    CursoService cursoService;


    @Transactional
    public void crearProfesor(Integer dni, String apellido, String materia) throws AtrapaErrores{
        Profesor profesor = new Profesor();
        Curso curso = new Curso();
        validarDniProfesor(dni);
        profesor.setDni(dni);
        validarApellidoProfesor(apellido);
        profesor.setApellido(apellido);
        profesor.setCursos((List<Curso>) curso.setMateria(materia));
        profesor.setEstado("activo");
        profesor.setFechaAlta(LocalDateTime.now());

        profesorRepository.save(profesor);

    }

    @Transactional
    public void crearProfesor(Integer dni, String apellido) throws AtrapaErrores{
        Profesor profesor = new Profesor();
        validarDniProfesor(dni);
        profesor.setDni(dni);
        validarApellidoProfesor(apellido);
        profesor.setApellido(apellido);
        profesor.setEstado("activo");
        profesor.setFechaAlta(LocalDateTime.now());

        profesorRepository.save(profesor);

    }
    @Transactional
    public Optional<Profesor> removerProfesor(long profesorId, long cursoId) throws AtrapaErrores{
        Optional<Profesor> profe = profesorRepository.findById(profesorId);
        //validarMateriaProfesor(materia);
        //todo fecha baja, tengo q ir a buscar el curso, ver el id del profesor q sea igual, y el campo profesor se lo seteo nulo
        //todo el curso puede tener un estado que sea pentendiete, al profesor tengo que darlo de baja y cambiar el estado sinProfesor
        //profesorRepository.removerProfesorDelCurso(profe);
        return profe;
    }

//    @Transactional
//    public void modificarProfesor(Integer dni, String apellido) throws AtrapaErrores{
//        Optional<Profesor> rta = profesorRepository.findById(dni);
//        validarDniProfesor(dni);
//
//        if (rta.isPresent()){
//            Profesor profesor = rta.get();
//            profesor.setApellido(apellido);;
//
//            profesorRepository.save(profesor);
//        }
//    }

    public List<Profesor> listarProfesores(){

        return profesorRepository.findAll();
    }

//    public Optional<Profesor> listarCursosProfesores(String materia){
//
//        return profesorRepository.findById(Integer.valueOf(materia));
//    }

    private void validarDniProfesor(Integer dni) throws AtrapaErrores {
        if (dni == null || dni.toString().isEmpty()){
            throw new AtrapaErrores("El DNI del Profesor es obligatorio. ");

        }

    }

    private void validarApellidoProfesor(String apellido) throws AtrapaErrores {
        if (apellido == null || apellido.toString().isEmpty()){
            throw new AtrapaErrores("El Apellido del Profesor es obligatorio. ");

        }

    }
    private String validarMateriaProfesor(String materia) throws AtrapaErrores {
        if (materia == null || materia.isEmpty()){
            throw new AtrapaErrores("El campo Materia es obligatorio. ");

        }

        return materia;
    }
}
