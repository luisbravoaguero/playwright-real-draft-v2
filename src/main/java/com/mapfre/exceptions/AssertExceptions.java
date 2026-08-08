package com.mapfre.exceptions;

public class AssertExceptions extends FrameworkException {
    /**
     * Excepción para fallos en las aserciones de negocio (Validaciones de UI).
     * AssertExceptions es una RuntimeException (Correcto usar extends).
     * AssertExceptions: Es un objeto que contiene información sobre un error (el mensaje, la causa, el momento en que ocurrió).
     * Si la excepción es AssertExceptions -> El reporte marca "FAIL" (Error de la web).
     */
    public AssertExceptions(String message) {
        super(message);
    }
    public AssertExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}