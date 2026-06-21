package com.UPIIZ.ProyectoMatAva.Entities;

public class FourierRequest {
    private String funcion;
    private Double periodoInicial;
    private Double periodoFinal;
    private Integer armonicos;

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public Double getPeriodoInicial() {
        return periodoInicial;
    }

    public void setPeriodoInicial(Double periodoInicial) {
        this.periodoInicial = periodoInicial;
    }

    public Double getPeriodoFinal() {
        return periodoFinal;
    }

    public void setPeriodoFinal(Double periodoFinal) {
        this.periodoFinal = periodoFinal;
    }

    public Integer getArmonicos() {
        return armonicos;
    }

    public void setArmonicos(Integer armonicos) {
        this.armonicos = armonicos;
    }
}