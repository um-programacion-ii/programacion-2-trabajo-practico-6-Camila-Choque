package org.example.microservicessystem.business_service.service;

import org.example.microservicessystem.business_service.client.DataServiceClient;
import org.example.microservicessystem.business_service.dto.CategoriaDTO;
import java.util.List;

public class CategoriaBusinessService {
    private final DataServiceClient dataServiceClient;

    public CategoriaBusinessService(DataServiceClient dataServiceClient) {
        this.dataServiceClient = dataServiceClient;
    }
    public List<CategoriaDTO> obtenerTodasLasCategorias() {
        return dataServiceClient.obtenerTodasLasCategorias();
    }
}