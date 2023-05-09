package com.quinto.universidad.exceptions;

import lombok.*;

@Getter
@Setter
public class CursoNotFoundException extends Exception{
   private final String error = "El Curso selecionado no existe. ";
}
