package com.crud.application.data.service;

import com.crud.application.data.entity.Comentarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ComentariosRepository extends JpaRepository<Comentarios, Long>, JpaSpecificationExecutor<Comentarios> {

}
