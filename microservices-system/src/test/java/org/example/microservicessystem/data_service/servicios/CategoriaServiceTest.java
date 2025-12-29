package org.example.microservicessystem.data_service.servicios;
import org.example.microservicessystem.data_service.Repository.CategoriaRepository;
import org.example.microservicessystem.data_service.entidades.Categoria;
import org.example.microservicessystem.data_service.excepciones.RecursoNoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Electrónica");
        categoria.setDescripcion("Productos electrónicos");
    }

    @Test
    void obtenerTodas_retornaListaDeCategorias() {
        when(categoriaRepository.findAll())
                .thenReturn(List.of(categoria));

        List<Categoria> resultado = categoriaService.obtenerTodas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(categoriaRepository).findAll();
    }

    @Test
    void buscarPorId_categoriaExiste_retornaCategoria() {
        when(categoriaRepository.findById(1L))
                .thenReturn(Optional.of(categoria));

        Categoria resultado = categoriaService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Electrónica", resultado.getNombre());
        verify(categoriaRepository).findById(1L);
    }

    @Test
    void buscarPorId_categoriaNoExiste_lanzaExcepcion() {
        when(categoriaRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RecursoNoEncontradoException.class,
                () -> categoriaService.buscarPorId(1L)
        );

        verify(categoriaRepository).findById(1L);
    }

    @Test
    void guardar_categoriaValida_seGuardaCorrectamente() {
        when(categoriaRepository.save(any(Categoria.class)))
                .thenReturn(categoria);

        Categoria resultado = categoriaService.guardar(categoria);

        assertNotNull(resultado);
        assertEquals("Electrónica", resultado.getNombre());
        verify(categoriaRepository).save(categoria);
    }

    @Test
    void actualizar_categoriaExiste_actualizaDatos() {
        Categoria actualizada = new Categoria();
        actualizada.setNombre("Hogar");
        actualizada.setDescripcion("Productos para el hogar");

        when(categoriaRepository.findById(1L))
                .thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(any(Categoria.class)))
                .thenReturn(categoria);

        Categoria resultado = categoriaService.actualizar(1L, actualizada);

        assertNotNull(resultado);
        assertEquals("Hogar", resultado.getNombre());
        assertEquals("Productos para el hogar", resultado.getDescripcion());
        verify(categoriaRepository).save(categoria);
    }

    @Test
    void eliminar_categoriaExiste_seEliminaCorrectamente() {
        when(categoriaRepository.existsById(1L))
                .thenReturn(true);
        doNothing().when(categoriaRepository).deleteById(1L);

        categoriaService.eliminar(1L);

        verify(categoriaRepository).deleteById(1L);
    }

    @Test
    void eliminar_categoriaNoExiste_lanzaExcepcion() {
        when(categoriaRepository.existsById(1L))
                .thenReturn(false);

        assertThrows(
                RecursoNoEncontradoException.class,
                () -> categoriaService.eliminar(1L)
        );

        verify(categoriaRepository, never()).deleteById(anyLong());
    }

    @Test
    void buscarPorNombre_categoriaExiste_retornaCategoria() {
        when(categoriaRepository.findByNombre("Electrónica"))
                .thenReturn(Optional.of(categoria));

        Categoria resultado =
                categoriaService.buscarPorNombre("Electrónica");

        assertNotNull(resultado);
        assertEquals("Electrónica", resultado.getNombre());
        verify(categoriaRepository).findByNombre("Electrónica");
    }

    @Test
    void buscarPorNombre_categoriaNoExiste_lanzaExcepcion() {
        when(categoriaRepository.findByNombre("Electrónica"))
                .thenReturn(Optional.empty());

        assertThrows(
                RecursoNoEncontradoException.class,
                () -> categoriaService.buscarPorNombre("Electrónica")
        );
        verify(categoriaRepository).findByNombre("Electrónica");
    }
}