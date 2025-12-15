package org.example.microservicessystem.data_service.Repository;
import org.example.microservicessystem.data_service.entidades.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    List<Inventario> findByCantidadLessThanEqual(int cantidad);
}
