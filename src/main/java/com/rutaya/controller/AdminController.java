package com.rutaya.controller;
import com.rutaya.model.*;
import com.rutaya.repository.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UsuarioRepository usuarioRepository;
    private final CompraRepository compraRepository;
    private final RutaRepository rutaRepository;

    public AdminController(UsuarioRepository u, CompraRepository c, RutaRepository r) {
        this.usuarioRepository=u; this.compraRepository=c; this.rutaRepository=r;
    }

    @GetMapping("/resumen")
    public Map<String,Object> resumen() {
        Map<String,Object> data = new HashMap<>();
        data.put("totalUsuarios", usuarioRepository.count());
        data.put("totalCompras", compraRepository.count());
        data.put("totalRutas", rutaRepository.count());
        double ingresos = compraRepository.findAll().stream()
            .filter(c->"CONFIRMADO".equals(c.getEstado())).mapToDouble(Compra::getTotal).sum();
        data.put("ingresosTotales", ingresos);
        long canceladas = compraRepository.findAll().stream()
            .filter(c->"CANCELADO".equals(c.getEstado())).count();
        data.put("totalCanceladas", canceladas);
        return data;
    }

    @GetMapping("/usuarios")
    public List<Usuario> usuarios() { return usuarioRepository.findAll(); }

    @GetMapping("/compras")
    public List<Compra> compras() { return compraRepository.findAll(); }
}
