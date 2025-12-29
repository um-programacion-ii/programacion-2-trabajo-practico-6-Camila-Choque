package org.example.microservicessystem.data_service.servicios;
import org.example.microservicessystem.business_service.dto.ProductoDTO;
import org.example.microservicessystem.business_service.dto.ProductoRequest;
import org.example.microservicessystem.data_service.Repository.CategoriaRepository;
import org.example.microservicessystem.data_service.Repository.ProductoRepository;
import org.example.microservicessystem.data_service.entidades.Categoria;
import org.example.microservicessystem.data_service.entidades.Inventario;
import org.example.microservicessystem.data_service.entidades.Producto;
import org.example.microservicessystem.data_service.excepciones.RecursoNoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    private final CategoriaService categoriaService;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, CategoriaService categoriaService) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.categoriaService = categoriaService;
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto productoActualizado) {
        Producto existente = buscarPorId(id);

        existente.setNombre(productoActualizado.getNombre());
        existente.setPrecio(productoActualizado.getPrecio());
        existente.setCategoria(productoActualizado.getCategoria());

        return productoRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No se puede eliminar");
        }
        productoRepository.deleteById(id);
    }

    public List<Producto> buscarPorCategoria(String nombreCategoria) {
        return productoRepository.findByCategoriaNombre(nombreCategoria);
    }

    @Transactional
    public ProductoDTO guardarDesdeDTO(ProductoRequest dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());

        // Buscar o crear categoría
        Categoria categoria = null;
        if (dto.getCategoriaNombre() != null && !dto.getCategoriaNombre().isBlank()) {
            categoria = categoriaRepository
                    .findByNombre(dto.getCategoriaNombre())
                    .orElseGet(() -> {
                        Categoria nueva = new Categoria();
                        nueva.setNombre(dto.getCategoriaNombre());
                        return categoriaRepository.save(nueva);
                    });
            producto.setCategoria(categoria);
        }

        // Crear inventario (usar el campo "stock")
        Inventario inventario = new Inventario();
        inventario.setStock(dto.getStock() != null ? dto.getStock() : 0);
        inventario.setProducto(producto);
        producto.setInventario(inventario);

        // Guardar todo en cascada
        Producto guardado = productoRepository.save(producto);

        // Mapear a DTO
        return new ProductoDTO(
                guardado.getId(),
                guardado.getNombre(),
                guardado.getDescripcion(),
                guardado.getPrecio(),
                guardado.getCategoria() != null ? guardado.getCategoria().getNombre() : null,
                guardado.getInventario().getStock(), // Usar "stock" en lugar de "cantidad"
                guardado.getInventario().getStock() < 5 // Calcular stockBajo
        );
    }




}
