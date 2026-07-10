package com.rutaya.controller;
import com.rutaya.dto.*;
import com.rutaya.model.Ruta;
import com.rutaya.repository.CompraRepository;
import com.rutaya.repository.RutaRepository;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {
    private final RutaRepository rutaRepository;
    private final CompraRepository compraRepository;

    public RutaController(RutaRepository rutaRepository, CompraRepository compraRepository) {
        this.rutaRepository = rutaRepository;
        this.compraRepository = compraRepository;
    }

    @GetMapping
    public List<Ruta> listar() { return rutaRepository.findAll(); }

    @GetMapping("/{id}/asientos-ocupados")
    public ResponseEntity<?> asientosOcupados(@PathVariable Long id, @RequestParam(required=false) String fecha) {
        if (!rutaRepository.existsById(id)) return ResponseEntity.notFound().build();
        LocalDate fechaConsulta;
        try { fechaConsulta = (fecha==null||fecha.isBlank()) ? LocalDate.now() : LocalDate.parse(fecha); }
        catch (DateTimeParseException e) { return ResponseEntity.badRequest().body(Map.of("ok",false,"mensaje","Fecha inválida")); }
        List<Integer> ocupados = compraRepository.findAsientosOcupados(id, fechaConsulta);
        Map<String,Object> resp = new HashMap<>();
        resp.put("rutaId",id); resp.put("fecha",fechaConsulta.toString());
        resp.put("ocupados",ocupados); resp.put("totalAsientos",40);
        resp.put("libres",40-ocupados.size());
        return ResponseEntity.ok(resp);
    }

    // ─── Admin: crear ruta ───
    @PostMapping("/admin/crear")
    public ResponseEntity<ApiResponse> crear(@Valid @RequestBody RutaRequest req) {
        Ruta r = new Ruta();
        r.setOrigen(req.getOrigen()); r.setDestino(req.getDestino());
        r.setPrecio(req.getPrecio()); r.setDuracion(req.getDuracion() != null ? req.getDuracion() : "—");
        r.setSalidas(req.getSalidas() != null ? req.getSalidas() : "Consultar horarios");
        r.setEmpresa(req.getEmpresa() != null ? req.getEmpresa() : "Sin definir");
        r.setEmoji(req.getEmoji() != null ? req.getEmoji() : "🚌");
        r.setColor(req.getColor() != null ? req.getColor() : "#E05A1E");
        rutaRepository.save(r);
        return ResponseEntity.ok(new ApiResponse(true,"Ruta creada correctamente",r));
    }

    // ─── Admin: eliminar ruta ───
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<ApiResponse> eliminar(@PathVariable Long id) {
        if (!rutaRepository.existsById(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,"Ruta no encontrada",null));
        rutaRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(true,"Ruta eliminada correctamente",null));
    }
}
