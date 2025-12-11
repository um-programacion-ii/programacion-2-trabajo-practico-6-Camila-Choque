package org.example.microservicessystem.business_service.servicios;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.example.microservicessystem.business_service.Excepciones.MicroserviceCommunicationException;
import org.example.microservicessystem.business_service.cliente.DataServiceClient;
import org.example.microservicessystem.business_service.dto.InventarioDTO;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class InventarioBusinessService {
    private final DataServiceClient dataServiceClient;

    public InventarioBusinessService(DataServiceClient dataServiceClient) {
        this.dataServiceClient = dataServiceClient;
    }

    public List<InventarioDTO> obtenerProductosConStockBajo() {
        try {
            return dataServiceClient.obtenerProductosConStockBajo();
        } catch (FeignException e) {
            log.error("Error al obtener inventario del microservicio de datos", e);
            throw new MicroserviceCommunicationException("Error de comunicación con el servicio de datos");
        }
    }

    public BigDecimal calcularValorTotalInventario() {
        try {

            List<InventarioDTO> inventarios = dataServiceClient.obtenerTodosLosInventarios();
            log.info("Calculando valor total de inventario para {} productos", inventarios.size());
            BigDecimal valorTotal = inventarios.stream()
                    .filter(inventario -> inventario.getProducto() != null) // Filtrar inventarios sin producto
                    .filter(inventario -> inventario.getCantidad() != null && inventario.getCantidad() > 0) // Solo productos con stock
                    .map(inventario -> {
                        BigDecimal precio = inventario.getProducto().getPrecio();
                        Integer cantidad = inventario.getCantidad();
                        if (precio == null) {
                            log.warn("Producto {} no tiene precio definido, se omitirá del cálculo",
                                    inventario.getProducto().getNombre());
                            return BigDecimal.ZERO;
                        }
                        return precio.multiply(BigDecimal.valueOf(cantidad));
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            log.info("Valor total del inventario calculado: ${}", valorTotal);
            return valorTotal;
        } catch (FeignException e) {
            log.error("Error al obtener inventarios del microservicio de datos", e);
            throw new MicroserviceCommunicationException("Error de comunicación con el servicio de datos");}
    }

}