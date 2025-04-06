package com.PFG_LCG.PFG;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resultados")
@CrossOrigin(origins = "*")
public class ResultadoController {

    @Autowired
    private ResultadoRepository resultadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CampanaRepository campanaRepository;

    // Obtener todos los resultados (usuarios y admins)
    @GetMapping
    public List<Resultado> getAll(@RequestParam String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario == null) return List.of(); // vacía si no existe
        return resultadoRepository.findAll();
    }

    // Crear nuevo resultado (solo admin)
    @PostMapping
    public String create(@RequestParam String correo, @RequestBody Resultado nuevo) {
        if (!esAdmin(correo)) return "No autorizado";
        resultadoRepository.save(nuevo);
        return "Resultado guardado correctamente.";
    }

    // Editar resultado existente (solo admin)
    @PutMapping("/{id}")
    public String update(@RequestParam String correo, @PathVariable Long id, @RequestBody Resultado actualizado) {
        if (!esAdmin(correo)) return "No autorizado";

        Resultado existente = resultadoRepository.findById(id).orElse(null);
        if (existente == null) return "Resultado no encontrado.";

        existente.setFechaPrueba(actualizado.getFechaPrueba());
        existente.setEmpleado(actualizado.getEmpleado());
        existente.setDepartamento(actualizado.getDepartamento());
        existente.setResultado(actualizado.getResultado());
        existente.setComentarios(actualizado.getComentarios());

        resultadoRepository.save(existente);
        return "Resultado actualizado.";
    }

    // Eliminar resultado (solo admin)
    @DeleteMapping("/{id}")
    public String delete(@RequestParam String correo, @PathVariable Long id) {
        if (!esAdmin(correo)) return "No autorizado";
        resultadoRepository.deleteById(id);
        return "Resultado eliminado.";
    }
    @GetMapping("/estadisticas")
    public List<CampaignStatsDTO> getEstadisticasCampanas(@RequestParam String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario == null) return List.of();

        return campanaRepository.findAll().stream().map(campana -> {
            int total = resultadoRepository.countByIdCampana(campana.getIdCampana());
            int clics = resultadoRepository.countByIdCampanaAndResultado(campana.getIdCampana(), Resultado.ResultadoTipo.clic);


            return new CampaignStatsDTO(campana.getNombre(), total, clics);

        }).toList();
    }

    // Método auxiliar
    private boolean esAdmin(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        return usuario != null && usuario.getRol().equals(Usuario.Rol.admin);
    }
}
