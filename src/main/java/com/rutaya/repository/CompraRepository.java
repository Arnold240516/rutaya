package com.rutaya.repository;
import com.rutaya.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    @Query("SELECT c.numeroAsiento FROM Compra c WHERE c.ruta.id = :rutaId AND c.fechaViaje = :fecha AND c.estado = 'CONFIRMADO'")
    List<Integer> findAsientosOcupados(Long rutaId, LocalDate fecha);

    boolean existsByRutaIdAndNumeroAsientoAndFechaViajeAndEstado(Long rutaId, Integer numeroAsiento, LocalDate fechaViaje, String estado);

    List<Compra> findByEmailPasajeroOrderByFechaCompraDesc(String emailPasajero);

    Optional<Compra> findByCodigoTicket(String codigoTicket);
}
