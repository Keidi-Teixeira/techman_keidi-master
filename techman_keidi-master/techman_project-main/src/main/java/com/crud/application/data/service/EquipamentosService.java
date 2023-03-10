package com.crud.application.data.service;

import com.crud.application.data.entity.Comentarios;
import com.crud.application.data.entity.Equipamentos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipamentosService {
    private final EquipamentosRepository repository;

    public EquipamentosService(EquipamentosRepository repository) {
        this.repository = repository;
    }

    public Optional<Equipamentos> get(Long id) {
        return repository.findById(id);
    }

    public Equipamentos update(Equipamentos entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Equipamentos> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Equipamentos> list(Pageable pageable, Specification<Equipamentos> filter) {
        return repository.findAll(filter, pageable);
    }
    public List<Equipamentos> listarTodos() {
        return repository.findAll();
    }
    public void salvar(Equipamentos cliente) {
        repository.save(cliente);
    }
    public int count() {
        return (int) repository.count();
    }
}
