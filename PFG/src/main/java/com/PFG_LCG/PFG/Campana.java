package com.PFG_LCG.PFG;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "campanas")
public class Campana {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCampana;

    private String nombre;

    // Relación con resultados (uno a muchos)
    @OneToMany(mappedBy = "campana", cascade = CascadeType.ALL)
    private List<Resultado> resultados;

    // Getters y Setters
    public Long getIdCampana() {
        return idCampana;
    }

    public void setIdCampana(Long idCampana) {
        this.idCampana = idCampana;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Resultado> getResultados() {
        return resultados;
    }

    public void setResultados(List<Resultado> resultados) {
        this.resultados = resultados;
    }
}
