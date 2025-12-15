package org.example.microservicessystem.business_service.servicios;
import feign.FeignException;
import java.util.Arrays;
import org.example.microservicessystem.business_service.Excepciones.MicroserviceCommunicationException;
import org.example.microservicessystem.business_service.cliente.DataServiceClient;
import org.example.microservicessystem.business_service.dto.InventarioDTO;
import org.example.microservicessystem.business_service.dto.ProductoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventarioBusinessServiceTest {

    @Mock
    private DataServiceClient dataServiceClient;

    @InjectMocks
    private InventarioBusinessService inventarioBusinessService;

    @Test
    void cuandoObtenerProductosConStockBajo_entoncesRetornaLista() {
        // Arrange
        List<InventarioDTO> inventariosEsperados = Arrays.asList(
                new InventarioDTO(1L, new ProductoDTO(1L, "Producto 1", null, BigDecimal.valueOf(100), null, null, false), 2),
                new InventarioDTO(2L, new ProductoDTO(2L, "Producto 2", null, BigDecimal.valueOf(50), null, null, false), 1)
        );

        when(dataServiceClient.obtenerProductosConStockBajo())
                .thenReturn(inventariosEsperados);

        List<InventarioDTO> resultado = inventarioBusinessService.obtenerProductosConStockBajo();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(dataServiceClient).obtenerProductosConStockBajo();
    }

    @Test
    void cuandoObtenerProductosConStockBajo_fallaFeign_entoncesLanzaExcepcion() {
        // Arrange
        when(dataServiceClient.obtenerProductosConStockBajo())
                .thenThrow(FeignException.class);

        assertThrows(MicroserviceCommunicationException.class, () -> {
            inventarioBusinessService.obtenerProductosConStockBajo();
        });

        verify(dataServiceClient).obtenerProductosConStockBajo();
    }

    @Test
    void cuandoCalcularValorTotalInventario_RetornaValorCorrecto() {
        // Arrange
        ProductoDTO producto1 = new ProductoDTO(
                1L, "Producto 1", null, BigDecimal.valueOf(100), null, null, false);
        ProductoDTO producto2 = new ProductoDTO(
                2L, "Producto 2", null, BigDecimal.valueOf(50), null, null, false);

        List<InventarioDTO> inventarios = Arrays.asList(
                new InventarioDTO(1L, producto1, 2), // 100 * 2 = 200
                new InventarioDTO(2L, producto2, 3)  // 50 * 3 = 150
        );

        when(dataServiceClient.obtenerTodosLosInventarios())
                .thenReturn(inventarios);

        BigDecimal resultado = inventarioBusinessService.calcularValorTotalInventario();

        assertNotNull(resultado);
        assertEquals(BigDecimal.valueOf(350), resultado);
        verify(dataServiceClient).obtenerTodosLosInventarios();
    }

    @Test
    void cuandoCalcularValorTotalInventario_conPrecioNull_entoncesNoSeSuma() {

        ProductoDTO productoSinPrecio = new ProductoDTO(
                1L, "Producto sin precio", null, null, null, null, false);

        List<InventarioDTO> inventarios = Arrays.asList(
                new InventarioDTO(1L, productoSinPrecio, 5)
        );

        when(dataServiceClient.obtenerTodosLosInventarios())
                .thenReturn(inventarios);


        BigDecimal resultado = inventarioBusinessService.calcularValorTotalInventario();

        assertEquals(BigDecimal.ZERO, resultado);
        verify(dataServiceClient).obtenerTodosLosInventarios();
    }

    @Test
    void cuandoCalcularValorTotalInventario_fallaFeign_entoncesLanzaExcepcion() {
        // Arrange
        when(dataServiceClient.obtenerTodosLosInventarios())
                .thenThrow(FeignException.class);

        assertThrows(MicroserviceCommunicationException.class, () -> {
            inventarioBusinessService.calcularValorTotalInventario();
        });

        verify(dataServiceClient).obtenerTodosLosInventarios();
    }
}
