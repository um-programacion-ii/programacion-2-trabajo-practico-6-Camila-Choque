package org.example.microservicessystem.data_service.servicios;
import org.example.microservicessystem.data_service.Repository.ProductoRepository;
import org.example.microservicessystem.data_service.entidades.Categoria;
import org.example.microservicessystem.data_service.entidades.Producto;
import org.example.microservicessystem.data_service.excepciones.RecursoNoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Electrónica");

        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Notebook");
        producto.setPrecio(BigDecimal.valueOf(1500));
        producto.setCategoria(categoria);
    }

    @Test
    void obtenerTodos_retornaListaDeProductos() {
        when(productoRepository.findAll())
                .thenReturn(List.of(producto));

        List<Producto> resultado = productoService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(productoRepository).findAll();
    }

    @Test
    void buscarPorId_productoExiste_retornaProducto() {
        when(productoRepository.findById(1L))
                .thenReturn(Optional.of(producto));

        Producto resultado = productoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Notebook", resultado.getNombre());
        verify(productoRepository).findById(1L);
    }

    @Test
    void buscarPorId_productoNoExiste_lanzaExcepcion() {
        when(productoRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RecursoNoEncontradoException.class,
                () -> productoService.buscarPorId(1L)
        );

        verify(productoRepository).findById(1L);
    }

    @Test
    void guardar_productoValido_seGuardaCorrectamente() {
        when(productoRepository.save(any(Producto.class)))
                .thenReturn(producto);

        Producto resultado = productoService.guardar(producto);

        assertNotNull(resultado);
        assertEquals("Notebook", resultado.getNombre());
        verify(productoRepository).save(producto);
    }

    @Test
    void actualizar_productoExiste_actualizaCampos() {
        Producto actualizado = new Producto();
        actualizado.setNombre("Notebook Gamer");
        actualizado.setPrecio(BigDecimal.valueOf(2500));
        actualizado.setCategoria(categoria);

        when(productoRepository.findById(1L))
                .thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class)))
                .thenReturn(producto);

        Producto resultado = productoService.actualizar(1L, actualizado);

        assertNotNull(resultado);
        assertEquals("Notebook Gamer", resultado.getNombre());
        assertEquals(BigDecimal.valueOf(2500), resultado.getPrecio());
        assertEquals(categoria, resultado.getCategoria());

        verify(productoRepository).save(producto);
    }

    @Test
    void eliminar_productoExiste_seEliminaCorrectamente() {
        when(productoRepository.existsById(1L))
                .thenReturn(true);
        doNothing().when(productoRepository).deleteById(1L);

        productoService.eliminar(1L);

        verify(productoRepository).deleteById(1L);
    }

    @Test
    void eliminar_productoNoExiste_lanzaExcepcion() {
        when(productoRepository.existsById(1L))
                .thenReturn(false);

        assertThrows(
                RecursoNoEncontradoException.class,
                () -> productoService.eliminar(1L)
        );

        verify(productoRepository, never()).deleteById(anyLong());
    }

    @Test
    void buscarPorCategoria_retornaProductosDeLaCategoria() {
        when(productoRepository.findByCategoriaNombre("Electrónica"))
                .thenReturn(List.of(producto));

        List<Producto> resultado =
                productoService.buscarPorCategoria("Electrónica");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Electrónica", resultado.get(0).getCategoria().getNombre());

        verify(productoRepository).findByCategoriaNombre("Electrónica");
    }
}