package org.vaadin.example;

import java.time.LocalDate;

public class Resultado {

    private Long idResultado;
    private LocalDate fechaPrueba;
    private String empleado;
    private String departamento;
    private String resultado;
    private String comentarios;

    // Getters y setters

    public Long getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(Long idResultado) {
        this.idResultado = idResultado;
    }

    public LocalDate getFechaPrueba() {
        return fechaPrueba;
    }

    public void setFechaPrueba(LocalDate fechaPrueba) {
        this.fechaPrueba = fechaPrueba;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
