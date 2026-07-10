package com.rutaya.repository;
import com.rutaya.model.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RutaRepository extends JpaRepository<Ruta, Long> {
    List<Ruta> findByOrigenIgnoreCaseAndDestinoIgnoreCase(String origen, String destino);
}
