package com.mapfre.models;

/**
 * Modelo inmutable de un documento SCTR leído desde la UI.
 *
 * @param numero        Número único del documento SCTR (ej. "3922977").
 * @param linea         Línea completa tal como aparece en UI (ej. "Nro. Doc. SCTR: 3922977").
 * @param fechaRegistro Texto de fecha tal como aparece (ej. "20/01/2026" o "20/01/2026 13:45:12").
 *
 * Nota: Este modelo NO depende de Playwright ni de clases de UI.
 */
public record SctrDocumento(String numero, String linea, String fechaRegistro) {

    /** Fila CSV básica (útil si quieres serializar rápido en un servicio de export). */
    public String[] toCsvRow() {
        return new String[] {
                numero == null ? "" : numero,
                linea == null ? "" : linea,
                fechaRegistro == null ? "" : fechaRegistro
        };
    }

    /** Header estándar para CSV. */
    public static String[] csvHeader() {
        return new String[] { "numero", "linea", "fecha_registro" };
    }
}
