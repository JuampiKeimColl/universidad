package com.quinto.universidad.controladores;

import com.quinto.universidad.exceptions.AtrapaErrores;
import com.quinto.universidad.servicios.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("/alumno")
public class AlumnoController {
    private Logger log = Logger.getLogger(AlumnoController.class.getName());
    @Autowired
    AlumnoService alumnoService;

    @GetMapping("/registrarAlumnoGet")
    public String registrarAlumnoGet(){
        return "alumno_form.html";
    }

    @PostMapping("/registroAlumnoPost")
    public String registroAlumnoPost(@RequestParam(required = false) Integer dni, String nombre,
                                     @RequestParam String apellido, @RequestParam(required = false) Integer edad,
                                     String direccion, ModelMap modelMap){
        log.info("Creando Alumno: " + apellido + " DNI: " + dni);
        try {
            alumnoService.crearAlumno(dni, nombre, apellido, edad, direccion);

            modelMap.put("crear", "Alumno " + apellido + " creado con éxito. ");

        }catch (AtrapaErrores er){
            modelMap.put("error" , er.getMessage());
            log.info("Se produjo un error al registrar los datos: " + er.getMessage());
            return "alumno_form.html";
        }
        return "index.html";
    }
}
