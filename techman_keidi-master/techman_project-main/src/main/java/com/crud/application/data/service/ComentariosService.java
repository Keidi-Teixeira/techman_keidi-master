package com.crud.application.data.service;

import com.crud.application.data.entity.Comentarios;
import com.crud.application.data.entity.Equipamentos;
import com.crud.application.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Service
public class ComentariosService {
    private final ComentariosRepository repository;

    public ComentariosService(ComentariosRepository repository) {
        this.repository = repository;
    }

    public Optional<Comentarios> get(Long id) {
        return repository.findById(id);
    }

    public Comentarios update(Comentarios entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Comentarios> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Comentarios> list(Pageable pageable, Specification<Comentarios> filter) {
        return repository.findAll(filter, pageable);
    }
    public List<Comentarios> listarTodos() {
        return repository.findAll();
    }

    public void salvar(Comentarios cliente) {
        repository.save(cliente);
    }

    public int count() {
        return (int) repository.count();
    }
}
