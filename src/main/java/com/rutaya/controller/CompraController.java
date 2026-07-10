package com.rutaya.controller;
import com.rutaya.dto.*;
import com.rutaya.model.*;
import com.rutaya.repository.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;

@RestController
@RequestMapping("/api/compras")
public class CompraController {
    private final CompraRepository compraRepository;
    private final RutaRepository rutaRepository;

    public CompraController(CompraRepository compraRepository, RutaRepository rutaRepository) {
        this.compraRepository = compraRepository; this.rutaRepository = rutaRepository;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> comprar(@Valid @RequestBody CompraRequest req) {
        Optional<Ruta> rutaOpt = rutaRepository.findById(req.getRutaId());
        if (rutaOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,"Ruta no encontrada",null));

        LocalDate fechaViaje;
        try { fechaViaje = LocalDate.parse(req.getFechaViaje()); }
        catch (DateTimeParseException|NullPointerException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false,"Fecha de viaje inválida",null)); }

        if (fechaViaje.isBefore(LocalDate.now()))
            return ResponseEntity.badRequest().body(new ApiResponse(false,"No puedes comprar pasajes para una fecha pasada",null));

        if (compraRepository.existsByRutaIdAndNumeroAsientoAndFechaViajeAndEstado(
                req.getRutaId(), req.getNumeroAsiento(), fechaViaje, "CONFIRMADO"))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(false,"Ese asiento ya fue vendido para esa fecha. Elige otro.",null));

        // Validar datos de pasajero invitado
        if ((req.getNombrePasajero()==null||req.getNombrePasajero().isBlank()) &&
            (req.getEmailPasajero()==null||req.getEmailPasajero().isBlank()))
            return ResponseEntity.badRequest().body(new ApiResponse(false,"Debes ingresar nombre y correo del pasajero",null));

        Ruta ruta = rutaOpt.get();
        String codigo = "RUT-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();

        Compra c = new Compra();
        c.setRuta(ruta); c.setNumeroAsiento(req.getNumeroAsiento());
        c.setNombrePasajero(req.getNombrePasajero()); c.setApellidoPasajero(req.getApellidoPasajero());
        c.setEmailPasajero(req.getEmailPasajero()); c.setTelefonoPasajero(req.getTelefonoPasajero());
        c.setDniPasajero(req.getDniPasajero()); c.setMetodoPago(req.getMetodoPago());
        c.setCodigoTicket(codigo); c.setFechaViaje(fechaViaje);
        c.setTotal(ruta.getPrecio() + 2.0);
        compraRepository.save(c);

        Map<String,Object> data = new HashMap<>();
        data.put("codigoTicket",codigo); data.put("asiento",req.getNumeroAsiento());
        data.put("total",c.getTotal()); data.put("origen",ruta.getOrigen());
        data.put("destino",ruta.getDestino()); data.put("empresa",ruta.getEmpresa());
        data.put("precio",ruta.getPrecio()); data.put("fechaViaje",fechaViaje.toString());
        return ResponseEntity.ok(new ApiResponse(true,"¡Compra exitosa!",data));
    }

    @GetMapping("/mis-compras")
    public ResponseEntity<?> misCompras(@RequestParam String email) {
        if (email==null||email.isBlank()) return ResponseEntity.badRequest().body(new ApiResponse(false,"Email requerido",null));
        return ResponseEntity.ok(compraRepository.findByEmailPasajeroOrderByFechaCompraDesc(email));
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<ApiResponse> cancelar(@PathVariable Long id, @RequestParam String email) {
        Optional<Compra> opt = compraRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,"Compra no encontrada",null));
        Compra c = opt.get();
        if (c.getEmailPasajero()==null||!c.getEmailPasajero().equalsIgnoreCase(email))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false,"No tienes permiso para cancelar este pasaje",null));
        if ("CANCELADO".equals(c.getEstado()))
            return ResponseEntity.badRequest().body(new ApiResponse(false,"Este pasaje ya estaba cancelado",null));
        if (c.getFechaViaje()!=null && c.getFechaViaje().isBefore(LocalDate.now().plusDays(1)))
            return ResponseEntity.badRequest().body(new ApiResponse(false,"Ya no se puede cancelar: faltan menos de 24 horas para el viaje",null));
        c.setEstado("CANCELADO"); compraRepository.save(c);
        return ResponseEntity.ok(new ApiResponse(true,"Pasaje cancelado. El asiento quedó libre.",null));
    }
}
