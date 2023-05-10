package com.quinto.universidad.servicios;

import com.quinto.universidad.utilidades.Turno;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TurnoService {
    public List<Turno> getTurnos() {
        return Arrays.asList(Turno.values());
    }
}
