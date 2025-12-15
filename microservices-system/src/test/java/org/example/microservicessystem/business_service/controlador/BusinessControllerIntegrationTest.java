package org.example.microservicessystem.business_service.controlador;
import org.example.microservicessystem.business_service.dto.InventarioDTO;
import org.example.microservicessystem.business_service.dto.ProductoDTO;
import org.example.microservicessystem.business_service.dto.ProductoRequest;
import org.example.microservicessystem.business_service.servicios.InventarioBusinessService;
import org.example.microservicessystem.business_service.servicios.ProductoBusinessService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BusinessControllerIntegrationTest {

    @Mock
    private ProductoBusinessService productoBusinessService;

    @Mock
    private InventarioBusinessService inventarioBusinessService;

    @InjectMocks
    private BusinessController businessController;

    @Test
    void obtieneLaListaDeProductos() {

        ProductoDTO producto = new ProductoDTO();
        producto.setId(1L);
        producto.setNombre("Producto Test");
        when(productoBusinessService.obtenerTodosLosProductos())
                .thenReturn(List.of(producto));
        List<ProductoDTO> resultado = businessController.obtenerTodosLosProductos();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Producto Test", resultado.get(0).getNombre());
        verify(productoBusinessService).obtenerTodosLosProductos();
    }

    @Test
    void obtieneUnProductoPorId() {

        ProductoDTO producto = new ProductoDTO();
        producto.setId(1L);
        producto.setNombre("Producto Test");
        when(productoBusinessService.obtenerProductoPorId(1L))
                .thenReturn(producto);
        ProductoDTO resultado = businessController.obtenerProductoPorId(1L);
        assertNotNull(resultado);
        assertEquals("Producto Test", resultado.getNombre());
        verify(productoBusinessService).obtenerProductoPorId(1L);
    }

    @Test
    void creaUnProductoNuevo() {

        ProductoRequest request = new ProductoRequest();
        request.setNombre("Producto Nuevo");
        request.setPrecio(BigDecimal.valueOf(100));
        request.setStock(5);
        ProductoDTO productoCreado = new ProductoDTO();
        productoCreado.setId(1L);
        productoCreado.setNombre("Producto Nuevo");
        when(productoBusinessService.crearProducto(request))
                .thenReturn(productoCreado);
        ProductoDTO resultado = businessController.crearProducto(request);
        assertNotNull(resultado);
        assertEquals("Producto Nuevo", resultado.getNombre());
        verify(productoBusinessService).crearProducto(request);
    }

    @Test
    void obtieneProductosPorCategoria() {

        ProductoDTO producto = new ProductoDTO();
        producto.setNombre("Producto Categoria");
        when(productoBusinessService.obtenerProductosPorCategoria("Electrónica"))
                .thenReturn(List.of(producto));
        List<ProductoDTO> resultado =
                businessController.obtenerProductosPorCategoria("Electrónica");
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(productoBusinessService).obtenerProductosPorCategoria("Electrónica");
    }

    @Test
    void obtieneProductosConStockBajo() {

        InventarioDTO inventario = new InventarioDTO();
        inventario.setCantidad(2);
        when(inventarioBusinessService.obtenerProductosConStockBajo())
                .thenReturn(List.of(inventario));
        List<InventarioDTO> resultado =
                businessController.obtenerProductosConStockBajo();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(inventarioBusinessService).obtenerProductosConStockBajo();
    }

    @Test
    void obtieneElValorTotalDelInventario() {

        when(inventarioBusinessService.calcularValorTotalInventario())
                .thenReturn(BigDecimal.valueOf(500));
        BigDecimal resultado =
                businessController.obtenerValorTotalInventario();
        assertNotNull(resultado);
        assertEquals(BigDecimal.valueOf(500), resultado);
        verify(inventarioBusinessService).calcularValorTotalInventario();
    }
}