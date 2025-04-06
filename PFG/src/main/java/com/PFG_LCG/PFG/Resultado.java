package com.PFG_LCG.PFG;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "resultados")
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResultado;

    @Column(name = "fecha_prueba", nullable = false)
    private LocalDate fechaPrueba;

    @Column(nullable = false)
    private String empleado;

    private String departamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultadoTipo resultado;

    @Column(columnDefinition = "TEXT")
    private String comentarios;

    @ManyToOne
    @JoinColumn(name = "id_campana")
    private Campana campana;

    // Enum para el campo resultado
    public enum ResultadoTipo {
        clic, ignorado
    }

    // Getters y Setters
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

    public ResultadoTipo getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoTipo resultado) {
        this.resultado = resultado;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Campana getCampana() {
        return campana;
    }

    public void setCampana(Campana campana) {
        this.campana = campana;
    }
}
