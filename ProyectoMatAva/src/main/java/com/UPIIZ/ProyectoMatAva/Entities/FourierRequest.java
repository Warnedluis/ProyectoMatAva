package com.UPIIZ.ProyectoMatAva.Entities;


public class FourierRequest{
    private String funcion;
    private Double periodoInicial;
    private Double periodoFinal;
    private Integer armonicos;
    
    
    public String getFuncion()
    {
        return funcion;
    }

    public Double getPeriodoInicial()
    {
        return periodoInicial;
    }

    public Double getPeriodoFinal()
    {
        return periodoFinal;
    }

    public Integer getArmonicos()
    {
        return armonicos;
    }

    public void setFuncion(String funcion)
    {
        this.funcion = funcion;
    }

    public void setPeriodoInicial(Double periodoInicial)
    {
        this.periodoInicial = periodoInicial;
    }

    public void setPeriodoFinal(Double periodoFinal)
    {
        this.periodoFinal= periodoFinal;
    }

    public void setArmonicos(Integer armonicos)
    {
        this.armonicos = armonicos;
    }





}