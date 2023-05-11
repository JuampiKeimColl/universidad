package com.quinto.universidad.controladores;

import com.quinto.universidad.servicios.CursoService;
import com.quinto.universidad.utilidades.Turno;
import com.quinto.universidad.exceptions.AtrapaErrores;
import com.quinto.universidad.servicios.ProfesorService;
import com.quinto.universidad.servicios.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/profesor")
public class ProfesorController {
    private Logger log = Logger.getLogger(ProfesorController.class.getName());
    @Autowired
    ProfesorService profesorService;
    @Autowired
    TurnoService turnoService;
    @Autowired
    CursoService cursoService;

    @GetMapping("/registrarProfesorGet")
    public String registrarProfesorGet(ModelMap modelMap){
//        List<Turno> turno = turnoService.getTurnos();
//        modelMap.get(turno);
        return "profesor_form.html";
    }

    @PostMapping("/registroProfesorPost")
    public String registroProfesorPost(@RequestParam(required = false) Integer dni,
                                       @RequestParam(required = false) String apellido,
                                       @RequestParam(required = false) String email, String password,
                                       String password2, ModelMap modelMap){
        log.info("Creando Profesor: " + apellido + " DNI: " + dni);
        try {
        profesorService.crearProfesor(dni,apellido, email, password, password2);
        modelMap.put("crear", apellido + " creado con éxito. ");

        }catch (AtrapaErrores er){
            modelMap.put("error" , er.getMessage());
            modelMap.put("dni" , dni);
            modelMap.put("apellido" , apellido);
            modelMap.put("email" , email);

            log.info("Se produjo un error al registrar los datos: " + er.getMessage());
            return "profesor_form.html";
        }
        return new String("redirect:/");
    }

    @GetMapping("/registrarCursoGet")
    public String registrarCursoGet(ModelMap modelMap){

        return "curso_form.html";
    }

    @PostMapping("/registrarCursoPost")
    public String registrarCursoPost(@RequestParam(required = false) String materia,
                                     ModelMap modelMap) throws AtrapaErrores {

        try {
            cursoService.crearCurso(materia);
            modelMap.put("crear", materia + " creado con éxito. ");
        } catch (AtrapaErrores er) {
            modelMap.put("error" , er.getMessage());
            modelMap.get(materia);
            log.info("Se produjo un error al registrar los datos: " + er.getMessage());
            return "curso_form.html";
        }

        return new String("redirect:/administrador/listaCursos");
    }
}
