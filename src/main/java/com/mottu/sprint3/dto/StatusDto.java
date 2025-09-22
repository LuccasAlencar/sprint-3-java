package com.mottu.sprint3.dto;

import com.mottu.sprint3.model.Status;

public class StatusDto {
    private Long id;
    private String nome;
    private Long statusGrupoId;

    public StatusDto() {}

    public StatusDto(Status status) {
        this.id = status.getId();
        this.nome = status.getNome();
        if (status.getStatusGrupo() != null) {
            this.statusGrupoId = status.getStatusGrupo().getId();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Long getStatusGrupoId() { return statusGrupoId; }
    public void setStatusGrupoId(Long statusGrupoId) { this.statusGrupoId = statusGrupoId; }
}