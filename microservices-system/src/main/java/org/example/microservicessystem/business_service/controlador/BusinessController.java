package org.example.microservicessystem.business_service.controlador;
import org.example.microservicessystem.business_service.dto.ProductoDTO;
import org.example.microservicessystem.business_service.dto.ProductoRequest;
import org.example.microservicessystem.business_service.dto.InventarioDTO;
import org.example.microservicessystem.business_service.servicios.InventarioBusinessService;
import org.example.microservicessystem.business_service.servicios.ProductoBusinessService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class BusinessController {

    private final ProductoBusinessService productoBusinessService;
    private final InventarioBusinessService inventarioBusinessService;

    public BusinessController(ProductoBusinessService productoBusinessService, InventarioBusinessService inventarioBusinessService) {
        this.productoBusinessService = productoBusinessService;
        this.inventarioBusinessService = inventarioBusinessService;
    }

    @GetMapping("/productos")
    public List<ProductoDTO> obtenerTodosLosProductos() {
        return productoBusinessService.obtenerTodosLosProductos();
    }

    @GetMapping("/productos/{id}")
    public ProductoDTO obtenerProductoPorId(@PathVariable Long id) {
        return productoBusinessService.obtenerProductoPorId(id);
    }

    @PostMapping("/productos")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoDTO crearProducto(@RequestBody ProductoRequest request) {
        return productoBusinessService.crearProducto(request);
    }

    @GetMapping("/productos/categoria/{nombre}")
    public List<ProductoDTO> obtenerProductosPorCategoria(@PathVariable String nombre) {
        return productoBusinessService.obtenerProductosPorCategoria(nombre);
    }

    @GetMapping("/reportes/stock-bajo")
    public List<InventarioDTO> obtenerProductosConStockBajo() {
        return inventarioBusinessService.obtenerProductosConStockBajo();
    }

    @GetMapping("/inventario")
    public BigDecimal obtenerValorTotalInventario() {
        return inventarioBusinessService.calcularValorTotalInventario();
    }

}