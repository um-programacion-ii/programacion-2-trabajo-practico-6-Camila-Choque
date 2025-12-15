package org.example.microservicessystem.business_service.servicios;
import org.example.microservicessystem.business_service.cliente.DataServiceClient;
import org.example.microservicessystem.business_service.dto.CategoriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CategoriaBusinessService {
    @Autowired
    private final DataServiceClient dataServiceClient;

    public CategoriaBusinessService(DataServiceClient dataServiceClient) {
        this.dataServiceClient = dataServiceClient;
    }
    public List<CategoriaDTO> obtenerTodasLasCategorias() {
        return dataServiceClient.obtenerTodasLasCategorias();
    }

}