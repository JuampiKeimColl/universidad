package com.quinto.universidad.servicios;

import com.quinto.universidad.entidades.Administrador;
import com.quinto.universidad.exceptions.AtrapaErrores;
import com.quinto.universidad.repositorios.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {
    @Autowired
    AdministradorRepository adminRepository;

    @Transactional
    public void crearAdmin(Integer id, String usuario) throws AtrapaErrores{
        Administrador admin = new Administrador();
        validarIdAdmin(id);
        admin.setId(id);
        admin.setUsuario(usuario);

        adminRepository.save(admin);
    }

    @Transactional
    public void modificarAdmin(Integer id, String usuario) throws AtrapaErrores{
        Optional<Administrador> rta = adminRepository.findById(id);
        validarIdAdmin(id);

        if (rta.isPresent()){
            Administrador admin = rta.get();
            admin.setUsuario(usuario);

            adminRepository.save(admin);
        }
    }

    public List<Administrador> listarAdministradores(){

        return adminRepository.findAll();
    }

    private void validarIdAdmin(Integer id) throws AtrapaErrores {
        if (id == null || id.toString().isEmpty()){
            throw new AtrapaErrores("El Id del Administrador es obligatorio. ");

        }

    }

}
