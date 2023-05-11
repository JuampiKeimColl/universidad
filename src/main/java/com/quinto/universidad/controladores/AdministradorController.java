package com.quinto.universidad.controladores;

import com.quinto.universidad.entidades.Alumno;
import com.quinto.universidad.entidades.Curso;
import com.quinto.universidad.entidades.Profesor;
import com.quinto.universidad.exceptions.AtrapaErrores;
import com.quinto.universidad.servicios.AdministradorService;
import com.quinto.universidad.servicios.AlumnoService;
import com.quinto.universidad.servicios.CursoService;
import com.quinto.universidad.servicios.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/administrador")
public class AdministradorController {
    private Logger log = Logger.getLogger(AlumnoController.class.getName());

    @Autowired
    AdministradorService administradorService;
    @Autowired
    CursoService cursoService;
    @Autowired
    AlumnoService alumnoService;
    @Autowired
    ProfesorService profesorService;

    @GetMapping("/registrarAdministradorGet")
    public String registrarAdministradorGet(){
        return "administrador_form.html";
    }


    @PostMapping("/registroAdministradorPost")
    public String registroAdministradorPost(@RequestParam(required = false) String usuario,
                                            @RequestParam(required = false) String email, String password,
                                            String password2, ModelMap modelMap){
        log.info("Creando Admin: " + usuario);
        try {
            administradorService.crearAdmin(usuario, email, password, password2);
            modelMap.put("crear", "Usuario creado con éxito. ");
        }catch (AtrapaErrores er){
            modelMap.put("error" , er.getMessage());
            modelMap.put("usuario" , usuario);
            modelMap.put("email" , email);
            log.info("Se produjo un error al registrar los datos: " + er);
            return "administrador_form.html";
        }
        return new String("redirect:/");
    }

    @GetMapping("/administradorBusquedas")
    public String administradorBusquedas(ModelMap modelMap){
        List<Curso> cursos = cursoService.listarCursos();
        modelMap.addAttribute("materia", cursos);

        return "administrador_busquedas.html";
    }

    @GetMapping("/listaAlumnos")
    public String listaDeAlumnos(ModelMap modelMap){
        List<Alumno> alumno = alumnoService.listarAlumnos();
        modelMap.addAttribute("alumnos", alumno);

        return "alumno_lista.html";
    }

    @GetMapping("/nombreAlumnos")
    public String nombreAlumnos(){

        return "alumno_busqueda.html";
    }

    @PostMapping("/busquedaAlumnos")
    public String busquedaAlumnos(String nombre, ModelMap modelMap){
        List<Alumno> alumno = alumnoService.buscarAlumnos(nombre);
        modelMap.addAttribute("alumnos", alumno);

        return "alumno_busqueda_resultado.html";
    }

    @GetMapping("/listaProfesores")
    public String listaProfesores(ModelMap modelMap){
        List<Profesor> profesor = profesorService.listarProfesores();
        modelMap.addAttribute("profesores", profesor);

        return "profesor_lista.html";
    }

    @GetMapping("/listaCursos")
    public String listaDeCursos(ModelMap modelMap){
        List<Curso> curso = cursoService.listarCursos();
        modelMap.addAttribute("cursos", curso);

        return "curso_lista.html";
    }

    @GetMapping("/modificar/{alumnoId}")
    public String modificarAlumno(@PathVariable long alumnoId, ModelMap modelMap){
        modelMap.put("alumno",alumnoService.getOne(alumnoId));

        return "alumno_modificar.html";
    }

    @PostMapping("/modificar/{alumnoId}")
    public String modificarAlumno(@PathVariable long alumnoId, Integer dni, String nombre, String apellido, Integer edad,
                                  String direccion, ModelMap modelMap){
        try {

            alumnoService.modificarAlumno(alumnoId, dni, nombre, apellido, direccion, edad);
            return "redirect:../listaAlumnos";
        }catch (AtrapaErrores er){
            log.info("Se produjo un error al registrar los datos: " + er.getMessage());
            modelMap.put("error", er.getMessage());
            return "alumno_modificar.html";
        }

    }

    @GetMapping("/modificarProfesor/{profesorId}")
    public String modificarProfesor(@PathVariable long profesorId, ModelMap modelMap){
        modelMap.put("profesor",profesorService.getOne(profesorId));

        return "profesor_modificar.html";
    }

    @PostMapping("/modificarProfesor/{profesorId}")
    public String modificarProfesor(@PathVariable long profesorId, Integer dni, String apellido, ModelMap modelMap){
        try {

            profesorService.modificarProfesor(profesorId, dni, apellido);
            return "redirect:../listaProfesores";
        }catch (AtrapaErrores er){
            log.info("Se produjo un error al registrar los datos: " + er.getMessage());
            modelMap.put("error", er.getMessage());
            return "profesor_modificar.html";
        }

    }

    @GetMapping("/modificarCurso/{cursoId}")
    public String modificarCurso(@PathVariable long cursoId, ModelMap modelMap){
        modelMap.put("curso",cursoService.getOne(cursoId));

        return "curso_modificar.html";
    }

    @PostMapping("/modificarCurso/{cursoId}")
    public String modificarCurso(@PathVariable long cursoId, String materia, ModelMap modelMap){
        try {

            cursoService.modificarCurso(cursoId, materia);
            return "redirect:../listaCursos";
        }catch (AtrapaErrores er){
            log.info("Se produjo un error al registrar los datos: " + er.getMessage());
            modelMap.put("error", er.getMessage());
            return "curso_modificar.html";
        }

    }

    @GetMapping("/eliminarCurso/{cursoId}")
    public String eliminarCurso(@PathVariable long cursoId, ModelMap modelMap){
        //modelMap.put("curso",cursoService.getOne(cursoId));
        cursoService.eliminarCurso(cursoId);
        return "redirect:../listaCursos";
    }

    @GetMapping("/eliminarProfesor/{profesorId}")
    public String eliminarProfesor(@PathVariable long profesorId, ModelMap modelMap){

        profesorService.eliminarProfesor(profesorId);
        return "redirect:../listaProfesores";

    }

    @GetMapping("/eliminarAlumno/{alumnoId}")
    public String eliminarAlumno(@PathVariable long alumnoId, ModelMap modelMap){

        alumnoService.eliminarAlumno(alumnoId);
        return "redirect:../listaAlumnos";

    }



}
