package org.example.microservicessystem.business_service.Excepciones;

public class ValidacionNegocioException extends RuntimeException {

    public ValidacionNegocioException(String message) {
        super(message);
    }
}
