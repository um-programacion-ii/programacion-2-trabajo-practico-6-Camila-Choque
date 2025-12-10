package org.example.microservicessystem.business_service.service;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.example.microservicessystem.business_service.Excepciones.MicroserviceCommunicationException;
import org.example.microservicessystem.business_service.client.DataServiceClient;
import org.example.microservicessystem.business_service.dto.InventarioDTO;
import org.springframework.stereotype.Service;
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
            log.error("Error al obtener productos del microservicio de datos", e);
            throw new MicroserviceCommunicationException("Error de comunicación con el servicio de datos");
        }
    }

}