package org.example.microservicessystem.business_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponse {
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    //Duda de que atributos colocar
}