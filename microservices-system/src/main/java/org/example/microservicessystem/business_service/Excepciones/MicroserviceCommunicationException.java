package org.example.microservicessystem.business_service.Excepciones;

public class MicroserviceCommunicationException extends RuntimeException{
    public MicroserviceCommunicationException(String message) {
        super(message);
    }
}
