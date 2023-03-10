package com.crud.application.data.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "comentarios")
public class Comentarios extends AbstractEntity{
    @Column(name = "comentario")
    private String comentario;
    @ManyToOne
    @JoinColumn(name = "id_equipamento")
    private Equipamentos equipamento;

    @ManyToOne
    @JoinColumn(name = "id_perfil_usuario")
    private User usuario;

    @Column(name = "data_comentario")
    private LocalDateTime dataComentario;

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Equipamentos getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamentos equipamento) {
        this.equipamento = equipamento;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDataComentario() {
        return dataComentario;
    }

    public void setDataComentario(LocalDateTime dataComentario) {
        this.dataComentario = dataComentario;
    }
}
