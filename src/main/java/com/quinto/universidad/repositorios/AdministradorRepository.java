package com.quinto.universidad.repositorios;

import com.quinto.universidad.entidades.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {
    @Query("SELECT ad FROM Administrador ad WHERE ad.email = :email")
    public Administrador buscarPorEmail(@Param("email") String email);
}
