package com.crud.application.data.entity;

import com.crud.application.data.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "equipamentos")
public class Equipamentos extends AbstractEntity {
    @Column(name = "nome")
    private String nome;
    @Column(name = "ativo")
    private Boolean ativo;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "data_equipamento")
    private LocalDateTime data;
    @Lob
    @Column(length = 1000000, name = "imagem")
    private byte[] imagem;
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
}


