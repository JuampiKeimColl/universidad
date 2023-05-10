package com.quinto.universidad.controladores;

import com.quinto.universidad.entidades.Alumno;
import com.quinto.universidad.entidades.Curso;
import com.quinto.universidad.exceptions.AtrapaErrores;
import com.quinto.universidad.servicios.AdministradorService;
import com.quinto.universidad.servicios.AlumnoService;
import com.quinto.universidad.servicios.CursoService;
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
        return "index.html";
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



}
