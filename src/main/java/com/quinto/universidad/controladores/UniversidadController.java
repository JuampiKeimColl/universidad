package com.quinto.universidad.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UniversidadController {
    @GetMapping("/")
    public String index(){

        return "index.html";
    }

    @GetMapping("/login")
    public String login(){

        return "login.html";
    }

    @GetMapping("/registroUsuario")
    public String registroUsuario(){

        return "registro_usuario.html";
    }

    @GetMapping("/alumno")
    public String loginAlumno(){

        return "alumno_form.html";
    }

    @GetMapping("/profesor")
    public String loginProfesor(){

        return "profesor_form.html";
    }

    @GetMapping("/administrador")
    public String loginAdministrador(){

        return "administrador_form.html";
    }
}
