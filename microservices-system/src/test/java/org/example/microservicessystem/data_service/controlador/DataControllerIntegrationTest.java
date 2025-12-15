package org.example.microservicessystem.data_service.controlador;
import org.example.microservicessystem.data_service.controller.DataController;
import org.example.microservicessystem.data_service.entidades.Inventario;
import org.example.microservicessystem.data_service.entidades.Producto;
import org.example.microservicessystem.data_service.servicios.InventarioService;
import org.example.microservicessystem.data_service.servicios.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataControllerIntegrationTest {

    @Mock
    private ProductoService productoService;

    @Mock
    private InventarioService inventarioService;

    @InjectMocks
    private DataController dataController;

    private Producto producto;
    private Inventario inventario;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto Test");

        inventario = new Inventario();
        inventario.setId(1L);
        inventario.setCantidad(3);
        inventario.setStockMinimo(5);
    }

    @Test
    void obtenerTodosLosProductos_retornaLista() {
        when(productoService.obtenerTodos())
                .thenReturn(List.of(producto));

        List<Producto> resultado =
                dataController.obtenerTodosLosProductos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(productoService).obtenerTodos();
    }

    @Test
    void obtenerProductoPorId_retornaProducto() {
        when(productoService.buscarPorId(1L))
                .thenReturn(producto);

        Producto resultado =
                dataController.obtenerProductoPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(productoService).buscarPorId(1L);
    }

    @Test
    void crearProducto_guardaYRetornaProducto() {
        when(productoService.guardar(any(Producto.class)))
                .thenReturn(producto);

        Producto resultado =
                dataController.crearProducto(producto);

        assertNotNull(resultado);
        assertEquals("Producto Test", resultado.getNombre());
        verify(productoService).guardar(producto);
    }

    @Test
    void actualizarProducto_actualizaYRetornaProducto() {
        when(productoService.actualizar(eq(1L), any(Producto.class)))
                .thenReturn(producto);

        Producto resultado =
                dataController.actualizarProducto(1L, producto);

        assertNotNull(resultado);
        verify(productoService).actualizar(1L, producto);
    }

    @Test
    void eliminarProducto_llamaAlServicio() {
        doNothing().when(productoService).eliminar(1L);

        dataController.eliminarProducto(1L);

        verify(productoService).eliminar(1L);
    }

    @Test
    void obtenerProductosPorCategoria_retornaLista() {
        when(productoService.buscarPorCategoria("Electronica"))
                .thenReturn(List.of(producto));

        List<Producto> resultado =
                dataController.obtenerProductosPorCategoria("Electronica");

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        verify(productoService).buscarPorCategoria("Electronica");
    }

    @Test
    void obtenerProductosConStockBajo_retornaInventario() {
        when(inventarioService.obtenerProductosConStockBajo())
                .thenReturn(List.of(inventario));

        List<Inventario> resultado =
                dataController.obtenerProductosConStockBajo();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getCantidad() <= 5);
        verify(inventarioService).obtenerProductosConStockBajo();
    }
}