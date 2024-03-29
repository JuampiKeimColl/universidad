package com.quinto.universidad.servicios;

import com.quinto.universidad.entidades.Curso;
import com.quinto.universidad.entidades.Profesor;
import com.quinto.universidad.exceptions.AtrapaErrores;
import com.quinto.universidad.repositorios.CursoRepository;
import com.quinto.universidad.repositorios.ProfesorRepository;
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
public class ProfesorService implements UserDetailsService {
    @Autowired
    ProfesorRepository profesorRepository;
    @Autowired
    CursoRepository cursoRepository;


//    @Transactional
//    public void crearProfesor(Integer dni, String apellido, String materia) throws AtrapaErrores{
//        Profesor profesor = new Profesor();
//        validarDniProfesor(dni);
//        profesor.setDni(dni);
//        validarApellidoProfesor(apellido);
//        profesor.setApellido(apellido);
//
//        profesorRepository.save(profesor);
//
//    }

    @Transactional
    public void crearProfesor(Integer dni, String apellido, String email, String password,
                              String password2) throws AtrapaErrores{
        Profesor profesor = new Profesor();
        validarProfesor(dni, apellido, email, password, password2);

        profesor.setDni(dni);
        profesor.setApellido(apellido);
        profesor.setEmail(email);
        profesor.setPassword(password);
        profesor.setFechaAlta(LocalDateTime.now());
        profesor.setEstado(Estado.ACTIVO);
        profesor.setRol(Rol.USER);

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

    @Transactional
    public void modificarProfesor(long profesorId, Integer dni, String apellido) throws AtrapaErrores{
        Optional<Profesor> rta = profesorRepository.findById(profesorId);
        //validarDniProfesor(dni);

        if (rta.isPresent()){
            Profesor profesor = rta.get();
            profesor.setDni(dni);
            profesor.setApellido(apellido);

            profesorRepository.save(profesor);
        }
    }

    public List<Profesor> listarProfesores(){

        return profesorRepository.findAll();
    }

    public Profesor getOne(long profesorId){

        return profesorRepository.getOne(profesorId);
    }

    public void eliminarProfesor(long profesorId){

        profesorRepository.deleteById(profesorId);
    }
//    public Optional<Profesor> listarCursosProfesores(String materia){
//
//        return profesorRepository.findById(Integer.valueOf(materia));
//    }

    private void validarProfesor(Integer dni, String apellido, String email, String password,
                                 String password2) throws AtrapaErrores {
        if (dni == null || dni.toString().isEmpty()){
            throw new AtrapaErrores("El DNI del Profesor es obligatorio. ");

        }
        if (apellido == null || apellido.toString().isEmpty()){
            throw new AtrapaErrores("El Apellido del Profesor es obligatorio. ");

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

    private String validarMateriaProfesor(String materia) throws AtrapaErrores {
        if (materia == null || materia.isEmpty()){
            throw new AtrapaErrores("El campo Materia es obligatorio. ");

        }

        return materia;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Profesor profesor = profesorRepository.buscarPorEmail(email);
        if (profesor != null){
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p =new SimpleGrantedAuthority("ROLE_" + profesor.getRol().toString());

            permisos.add(p);

            return new User(profesor.getEmail(), profesor.getPassword(), permisos);
        }else{
            return null;
        }

    }
}
