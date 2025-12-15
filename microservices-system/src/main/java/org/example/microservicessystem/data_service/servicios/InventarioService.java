package org.example.microservicessystem.data_service.servicios;
import org.example.microservicessystem.data_service.Repository.InventarioRepository;
import org.example.microservicessystem.data_service.entidades.Inventario;
import org.example.microservicessystem.data_service.excepciones.RecursoNoEncontradoException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventarioService {
    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public List<Inventario> obtenerTodos() {
        return inventarioRepository.findAll();
    }

    public Inventario buscarPorId(Long id) {
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Inventario no encontrado"));
    }

    public Inventario guardar(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    public Inventario actualizar(Long id, Inventario inventarioActualizado) {
        Inventario existente = buscarPorId(id);

        existente.setProducto(inventarioActualizado.getProducto());
        existente.setCantidad(inventarioActualizado.getCantidad());
        existente.setStockMinimo(inventarioActualizado.getStockMinimo());
        existente.setFechaActualizacion(LocalDateTime.now());

        return inventarioRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!inventarioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No se puede eliminar" );
        }
        inventarioRepository.deleteById(id);
    }

    public List<Inventario> obtenerProductosConStockBajo() {

        return inventarioRepository.findByCantidadLessThanEqual(5);
    }
}
