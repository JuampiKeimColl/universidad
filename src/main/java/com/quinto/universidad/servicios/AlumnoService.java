package com.quinto.universidad.servicios;

import com.quinto.universidad.entidades.Alumno;
import com.quinto.universidad.entidades.Curso;
import com.quinto.universidad.exceptions.AtrapaErrores;
import com.quinto.universidad.exceptions.CursoNotFoundException;
import com.quinto.universidad.repositorios.AlumnoRepository;
import com.quinto.universidad.repositorios.CursoRepository;
import com.quinto.universidad.utilidades.Estado;
import com.quinto.universidad.utilidades.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService implements UserDetailsService {
    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public void crearAlumno(Integer dni, String nombre, String apellido, Integer edad,
                            String direccion, String email, String password, String password2) throws AtrapaErrores {
        Alumno alumno = new Alumno();
        validarDniAlumno(dni);
        validarAlumno(nombre, apellido, email, password, password2);
        alumno.setDni(dni);
        alumno.setNombre(nombre);
        alumno.setApellido(apellido);
        alumno.setEdad(edad);
        alumno.setDireccion(direccion);
        alumno.setFechaAlta(LocalDateTime.now());
        alumno.setEmail(email);
        alumno.setPassword(password);
        alumno.setEstado(Estado.ACTIVO);
        alumno.setRol(Rol.USER);

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

    private void validarAlumno(String nombre, String apellido, String email, String password,
                               String password2) throws AtrapaErrores {
        if (nombre == null || nombre.isEmpty()){
            throw new AtrapaErrores("El nombre es obligatorio. ");

        }
        if (apellido == null || apellido.isEmpty()){
            throw new AtrapaErrores("El apellido es obligatorio. ");

        }
        if (email == null || email.isEmpty()){
            throw new AtrapaErrores("El email es obligatorio. ");

        }
        if (password == null || password.length() <=6){
            throw new AtrapaErrores("El password es obligatorio y debe ser mayor a 6 digitos. ");

        }
        if (!password.equals(password2)){
            throw new AtrapaErrores("La confirmación del password deben coincidir. ");

        }

    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Alumno alumno = alumnoRepository.buscarPorEmail(email);
        if (alumno != null){
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p =new SimpleGrantedAuthority("ROLE_" + alumno.getRol().toString());

            permisos.add(p);

            return new User(alumno.getEmail(), alumno.getPassword(), permisos);
        }else{
            return null;
        }

    }
}
