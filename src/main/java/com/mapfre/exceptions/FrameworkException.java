package com.mapfre.exceptions;

public class FrameworkException extends RuntimeException {
    /**
     * Excepción base para errores técnicos del framework (Drivers, Configuración, Timeouts).
     * Si la excepción es FrameworkException -> El reporte marca "BROKEN/ERROR" (Error del script o infraestructura).
     */
    public FrameworkException(String message) {
        super(message);
    }
    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}