package org.example.microservicessystem.data_service.Repository;
import org.example.microservicessystem.data_service.entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByNombre(String nombre);
}
