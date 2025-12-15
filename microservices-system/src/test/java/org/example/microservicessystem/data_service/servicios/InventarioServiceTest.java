package org.example.microservicessystem.data_service.servicios;
import org.example.microservicessystem.data_service.Repository.InventarioRepository;
import org.example.microservicessystem.data_service.entidades.Inventario;
import org.example.microservicessystem.data_service.entidades.Producto;
import org.example.microservicessystem.data_service.excepciones.RecursoNoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;

    private Inventario inventario;
    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto Test");

        inventario = new Inventario();
        inventario.setId(1L);
        inventario.setProducto(producto);
        inventario.setCantidad(10);
        inventario.setStockMinimo(5);
        inventario.setFechaActualizacion(LocalDateTime.now());
    }

    @Test
    void obtenerTodos_retornaListaInventario() {
        when(inventarioRepository.findAll())
                .thenReturn(List.of(inventario));

        List<Inventario> resultado = inventarioService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(inventarioRepository).findAll();
    }

    @Test
    void buscarPorId_inventarioExiste_retornaInventario() {
        when(inventarioRepository.findById(1L))
                .thenReturn(Optional.of(inventario));

        Inventario resultado = inventarioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(10, resultado.getCantidad());
        verify(inventarioRepository).findById(1L);
    }

    @Test
    void buscarPorId_inventarioNoExiste_lanzaExcepcion() {
        when(inventarioRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RecursoNoEncontradoException.class,
                () -> inventarioService.buscarPorId(1L)
        );

        verify(inventarioRepository).findById(1L);
    }

    @Test
    void guardar_inventarioValido_seGuardaCorrectamente() {
        when(inventarioRepository.save(any(Inventario.class)))
                .thenReturn(inventario);

        Inventario resultado = inventarioService.guardar(inventario);

        assertNotNull(resultado);
        assertEquals(producto, resultado.getProducto());
        verify(inventarioRepository).save(inventario);
    }

    @Test
    void actualizar_inventarioExiste_actualizaCampos() {
        Inventario actualizado = new Inventario();
        actualizado.setProducto(producto);
        actualizado.setCantidad(3);
        actualizado.setStockMinimo(5);

        when(inventarioRepository.findById(1L))
                .thenReturn(Optional.of(inventario));
        when(inventarioRepository.save(any(Inventario.class)))
                .thenReturn(inventario);

        Inventario resultado = inventarioService.actualizar(1L, actualizado);

        assertNotNull(resultado);
        assertEquals(3, resultado.getCantidad());
        assertEquals(5, resultado.getStockMinimo());
        assertNotNull(resultado.getFechaActualizacion());

        verify(inventarioRepository).save(inventario);
    }

    @Test
    void eliminar_inventarioExiste_seEliminaCorrectamente() {
        when(inventarioRepository.existsById(1L))
                .thenReturn(true);
        doNothing().when(inventarioRepository).deleteById(1L);

        inventarioService.eliminar(1L);

        verify(inventarioRepository).deleteById(1L);
    }

    @Test
    void eliminar_inventarioNoExiste_lanzaExcepcion() {
        when(inventarioRepository.existsById(1L))
                .thenReturn(false);

        assertThrows(
                RecursoNoEncontradoException.class,
                () -> inventarioService.eliminar(1L)
        );

        verify(inventarioRepository, never()).deleteById(anyLong());
    }

    @Test
    void obtenerProductosConStockBajo_retornaInventariosConCantidadBaja() {
        Inventario bajoStock = new Inventario();
        bajoStock.setCantidad(3);

        when(inventarioRepository.findByCantidadLessThanEqual(5))
                .thenReturn(List.of(bajoStock));

        List<Inventario> resultado =
                inventarioService.obtenerProductosConStockBajo();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getCantidad() <= 5);

        verify(inventarioRepository).findByCantidadLessThanEqual(5);
    }
}