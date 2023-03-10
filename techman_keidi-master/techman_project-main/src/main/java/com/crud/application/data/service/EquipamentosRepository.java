package com.crud.application.data.service;

import com.crud.application.data.entity.Equipamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EquipamentosRepository extends JpaRepository<Equipamentos, Long>, JpaSpecificationExecutor<Equipamentos> {

}
