package org.example.microservicessystem.business_service.servicios;
import java.util.Arrays;
import org.example.microservicessystem.business_service.cliente.DataServiceClient;
import org.example.microservicessystem.business_service.dto.CategoriaDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaBusinessServiceTest {

    @Mock
    private DataServiceClient dataServiceClient;

    @InjectMocks
    private CategoriaBusinessService categoriaBusinessService;

    @Test
    void cuandoObtenerTodasLasCategorias_entoncesRetornaLista() {
        // Arrange
        List<CategoriaDTO> categoriasEsperadas = Arrays.asList(
                new CategoriaDTO(1L, "Electrónica", null),
                new CategoriaDTO(2L, "Hogar", null)
        );

        when(dataServiceClient.obtenerTodasLasCategorias())
                .thenReturn(categoriasEsperadas);

        List<CategoriaDTO> resultado = categoriaBusinessService.obtenerTodasLasCategorias();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Electrónica", resultado.get(0).getNombre());
        verify(dataServiceClient).obtenerTodasLasCategorias();
    }
}
