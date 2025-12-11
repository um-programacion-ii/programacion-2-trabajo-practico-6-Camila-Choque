package org.example.microservicessystem.business_service.Excepciones;

public class ProductoNoEncontradoException extends RuntimeException{

    public ProductoNoEncontradoException(String message) {
        super(message);
    }
}
