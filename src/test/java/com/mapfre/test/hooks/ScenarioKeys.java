package com.mapfre.test.hooks;

public final class ScenarioKeys {
    private ScenarioKeys() {
        throw new IllegalStateException("Utility class");
    }

    public static final class PolizaAuto {
        public static final String NUMERO_PLACA_VEHICULO = "numeroPlaca";
        public static final String NUMERO_SERIE_VIN_VEHICULO= "numeroSerie";
        public static final String NUMERO_MOTOR_VEHICULO = "numeroMotor";
        public static final String NUMERO_COTIZACION_VEHICULO = "numeroCotizacion";
        public static final String NUMERO_NUEVA_SOLICITUD_VEHICULO = "numeroNuevaSolicitud";

        private PolizaAuto() {
            throw new IllegalStateException("Utility class");
        }
    }

    public static final class PolizaSctr {
        public static final String NUMERO_DOCUMENTO_SCTR = "numeroDocumentoSctr";

        private PolizaSctr() {
            throw new IllegalStateException("Utility class");
        }
    }

}
