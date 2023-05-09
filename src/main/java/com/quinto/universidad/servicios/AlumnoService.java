package com.quinto.universidad.servicios;

import com.quinto.universidad.entidades.Alumno;
import com.quinto.universidad.entidades.Curso;
import com.quinto.universidad.exceptions.AtrapaErrores;
import com.quinto.universidad.exceptions.CursoNotFoundException;
import com.quinto.universidad.repositorios.AlumnoRepository;
import com.quinto.universidad.repositorios.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {
    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public void crearAlumno(Integer dni, String nombre, String apellido, Integer edad,
                            String direccion) throws AtrapaErrores {
        Alumno alumno = new Alumno();
        validarDniAlumno(dni);

        alumno.setDni(dni);
        alumno.setNombre(nombre);
        alumno.setApellido(apellido);
        alumno.setEdad(edad);
        alumno.setDireccion(direccion);
        alumno.setFechaAlta(LocalDateTime.now());

        alumnoRepository.save(alumno);
    }

    @Transactional
    public void asignarCursoAlumno(Alumno alumno, long cursoId) throws CursoNotFoundException {
        //todo verificar que el curso exista por el id
        //todo con el repository lo traigo y lo valido


        Curso curso = cursoRepository.findById(cursoId).orElseThrow(CursoNotFoundException::new);

        alumno.getCursos().add(curso);

        alumnoRepository.save(alumno);
    }

    @Transactional
    public void modificarAlumno(long alumnoId, Integer dni, String nombre, String apellido, String direccion,
                                Integer edad) throws AtrapaErrores{
        Optional<Alumno> rta = alumnoRepository.findById(alumnoId);
        validarDniAlumno(dni);

        if (rta.isPresent()){
            Alumno alumno = rta.get();
            alumno.setNombre(nombre);
            alumno.setApellido(apellido);
            alumno.setDireccion(direccion);
            alumno.setEdad(edad);

            alumnoRepository.save(alumno);
        }

    }

    public List<Alumno> listarAlumnos(){

        return alumnoRepository.findAll();
    }

    public Alumno getOne(long alumnoId){
        return alumnoRepository.getOne(alumnoId);
    }

    public List<Alumno> buscarAlumnos(String letra){

        return alumnoRepository.buscarPorLetra(letra);
    }

    public List<Alumno> cursoAlumnos(long cursoId){

        return alumnoRepository.buscarPorCurso(cursoId);
    }

    private void validarDniAlumno(Integer dni) throws AtrapaErrores{
        if (dni == null || dni.toString().isEmpty()){
            throw new AtrapaErrores("El DNI del Alumno es obligatorio. ");

        }

    }


}
