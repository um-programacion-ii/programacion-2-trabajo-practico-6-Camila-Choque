package org.example.microservicessystem.data_service.Repository;
import org.example.microservicessystem.data_service.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoriaNombre(String nombre);
}
