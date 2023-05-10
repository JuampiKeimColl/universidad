package com.quinto.universidad.servicios;

import com.quinto.universidad.entidades.Administrador;
import com.quinto.universidad.exceptions.AtrapaErrores;
import com.quinto.universidad.repositorios.AdministradorRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService implements UserDetailsService {
    @Autowired
    AdministradorRepository adminRepository;

    @Transactional
    public void crearAdmin(String usuario, String email, String password, String password2) throws AtrapaErrores{
        Administrador admin = new Administrador();
        validarIdAdmin(usuario, email, password, password2);
        admin.setUsuario(usuario);
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setEstado(Estado.ACTIVO);
        admin.setRol(Rol.ADMIN);

        adminRepository.save(admin);
    }

    @Transactional
    public void modificarAdmin(Integer adminId, String usuario, String email, String password,
                               String password2) throws AtrapaErrores{
        Optional<Administrador> rta = adminRepository.findById(adminId);
        validarIdAdmin(usuario, email, password, password2);

        if (rta.isPresent()){
            Administrador admin = rta.get();
            admin.setUsuario(usuario);

            adminRepository.save(admin);
        }
    }

    public List<Administrador> listarAdministradores(){

        return adminRepository.findAll();
    }

    private void validarIdAdmin(String usuario, String email, String password, String password2) throws AtrapaErrores {
        if (usuario == null || usuario.isEmpty()){
            throw new AtrapaErrores("El usuario es obligatorio. ");

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
        Administrador admin = adminRepository.buscarPorEmail(email);

        if (admin != null){
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p =new SimpleGrantedAuthority("ROLE_" + admin.getRol().toString());

            permisos.add(p);

            return new User(admin.getEmail(), admin.getPassword(), permisos);
        }else{
            return null;
        }

    }
}
