package com.PFG_LCG.PFG;

import org.springframework.data.jpa.repository.JpaRepository;
import com.PFG_LCG.PFG.Resultado.ResultadoTipo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResultadoRepository extends JpaRepository<Resultado, Long> {
    int countByCampana_IdCampana(Long idCampana);

    int countByCampana_IdCampanaAndResultado(Long idCampana, Resultado.ResultadoTipo resultado);
    // Contar resultados por campaña
    @Query("SELECT COUNT(r) FROM Resultado r WHERE r.campana.idCampana = :idCampana")
    int countByIdCampana(@Param("idCampana") Long idCampana);

    // Contar clics por campaña
    @Query("SELECT COUNT(r) FROM Resultado r WHERE r.campana.idCampana = :idCampana AND r.resultado = :resultado")
    int countByIdCampanaAndResultado(@Param("idCampana") Long idCampana, @Param("resultado") ResultadoTipo resultado);
}
