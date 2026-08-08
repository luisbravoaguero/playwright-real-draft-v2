package com.mapfre.playwright.components.material;

import com.mapfre.asserts.ElementAsserts;
import com.microsoft.playwright.Page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MaterialDatePicker {

    private static final Logger log = LoggerFactory.getLogger(MaterialDatePicker.class);

    private final Page page;
    private final Locator root;
    private final String label;

    private static final DateTimeFormatter FECHA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final String DATE_INPUT = "input";
    private static final String CALENDAR_OPEN_BUTTON = "button[aria-label='Open calendar']";

    private static final String DATEPICKER_OVERLAY = ".cdk-overlay-pane.mat-datepicker-popup";
    private static final String PERIOD_BUTTON = "button.mat-calendar-period-button";
    private static final String PREVIOUS_BUTTON = "button.mat-calendar-previous-button";
    private static final String NEXT_BUTTON = "button.mat-calendar-next-button";

    private static final String YEAR_BUTTON_BY_ARIA = "button[aria-label='%s']";
    private static final String MONTH_BUTTON_BY_ARIA = "button[aria-label='%s %s']";
    private static final String DAY_BUTTON_BY_ARIA = "button[aria-label='%s de %s de %s']";

    public MaterialDatePicker(Page page, Locator root, String label) {
        this.page = page;
        this.root = root;
        this.label = label;
    }

    public void seleccionarFecha(String fecha) {
        LocalDate targetDate = parseFecha(fecha);
        seleccionarFecha(targetDate);
    }

    public void seleccionarFecha(LocalDate fecha) {
        log.info("Iniciando selección de fecha '{}' para el campo '{}'.", formatFecha(fecha), label);

        assertVisible(root, "No se encontró el datepicker con label '" + label + "'.");

        Locator calendarButton = root.locator(CALENDAR_OPEN_BUTTON);
        clickCalendarOption(
                calendarButton,
                "No se pudo abrir el calendario del campo '" + label + "'."
        );
        log.info("Se abrió el calendario del campo '{}'.", label);

        Locator overlay = obtenerOverlayVisible();
        log.info("El popup del calendario está visible para el campo '{}'.", label);

        abrirVistaMultiAnio(overlay);
        navegarHastaRangoQueContieneAnio(overlay, fecha.getYear());
        seleccionarAnio(overlay, fecha.getYear());
        seleccionarMes(overlay, fecha.getMonthValue(), fecha.getYear());
        seleccionarDia(overlay, fecha);
        validarFechaSeleccionada(fecha);

        log.info("Se confirmó correctamente la fecha '{}' en el campo '{}'.", formatFecha(fecha), label);
    }

    public String obtenerValor() {
        Locator input = root.locator(DATE_INPUT);
        assertVisible(input, "No se encontró el input del campo '" + label + "'.");
        return input.inputValue();
    }

    private Locator obtenerOverlayVisible() {
        Locator overlay = page.locator(DATEPICKER_OVERLAY).last();
        overlay.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return overlay;
    }

    private void abrirVistaMultiAnio(Locator overlay) {
        Locator periodButton = overlay.locator(PERIOD_BUTTON);

        clickCalendarOption(
                periodButton,
                "No se pudo abrir el selector de período del calendario para el campo '" + label + "'."
        );
        log.info("Se abrió el selector de período del calendario para el campo '{}'.", label);

        String periodText = safeText(periodButton);

        // Si después del primer click estamos en vista año (por ejemplo "2026"),
        // hacemos otro click para abrir vista multi-año (por ejemplo "2026 – 2049")
        if (periodText != null && !periodText.contains("–")) {
            clickCalendarOption(
                    periodButton,
                    "No se pudo abrir la vista multi-año del calendario para el campo '" + label + "'."
            );
            log.info("Se abrió la vista multi-año del calendario para el campo '{}'.", label);
        }
    }

    private void navegarHastaRangoQueContieneAnio(Locator overlay, int anioObjetivo) {
        int maxIntentos = 30;

        for (int i = 0; i < maxIntentos; i++) {
            Locator periodButton = overlay.locator(PERIOD_BUTTON);
            String texto = safeText(periodButton);

            if (texto == null || !texto.contains("–")) {
                throw new AssertionError(
                        "No se pudo determinar el rango de años visible para el campo '" + label + "'."
                );
            }

            int[] rango = parseRangoAnios(texto);
            int anioInicio = rango[0];
            int anioFin = rango[1];

            log.info("Rango de años visible para '{}': {} - {}", label, anioInicio, anioFin);

            if (anioObjetivo >= anioInicio && anioObjetivo <= anioFin) {
                return;
            }

            if (anioObjetivo < anioInicio) {
                Locator previousButton = overlay.locator(PREVIOUS_BUTTON);
                clickCalendarOption(
                        previousButton,
                        "No se pudo navegar al rango anterior de años para el campo '" + label + "'."
                );
            } else {
                Locator nextButton = overlay.locator(NEXT_BUTTON);
                clickCalendarOption(
                        nextButton,
                        "No se pudo navegar al rango siguiente de años para el campo '" + label + "'."
                );
            }
        }

        throw new AssertionError(
                "No se pudo encontrar un rango visible que contenga el año '" + anioObjetivo +
                        "' para el campo '" + label + "'."
        );
    }

    private void seleccionarAnio(Locator overlay, int anio) {
        Locator yearButton = overlay.locator(String.format(YEAR_BUTTON_BY_ARIA, anio));

        clickCalendarOption(
                yearButton,
                "No se puede seleccionar el año '" + anio + "' para el campo '" + label + "'."
        );

        log.info("Se seleccionó el año '{}' para el campo '{}'.", anio, label);
    }

    private void seleccionarMes(Locator overlay, int mes, int anio) {
        String nombreMes = obtenerNombreMes(mes);
        Locator monthButton = overlay.locator(String.format(MONTH_BUTTON_BY_ARIA, nombreMes, anio));

        clickCalendarOption(
                monthButton,
                "No se puede seleccionar el mes '" + nombreMes + " " + anio + "' para el campo '" + label + "'."
        );

        log.info("Se seleccionó el mes '{}' del año '{}' para el campo '{}'.", nombreMes, anio, label);
    }

    private void seleccionarDia(Locator overlay, LocalDate fecha) {
        String nombreMes = obtenerNombreMes(fecha.getMonthValue());

        Locator dayButton = overlay.locator(String.format(
                DAY_BUTTON_BY_ARIA,
                fecha.getDayOfMonth(),
                nombreMes,
                fecha.getYear()
        ));

        clickCalendarOption(
                dayButton,
                "No se puede seleccionar el día '" + fecha.getDayOfMonth() + " de " + nombreMes + " de " +
                        fecha.getYear() + "' para el campo '" + label +
                        "'. Verifique si la fecha está fuera del rango permitido o deshabilitada en el calendario."
        );

        log.info("Se seleccionó el día '{}' para el campo '{}'.", formatFecha(fecha), label);
    }

    private void validarFechaSeleccionada(LocalDate fechaEsperada) {
        Locator input = root.locator(DATE_INPUT);
        assertVisible(input, "El input del campo '" + label + "' no es visible después de seleccionar la fecha.");

        String valorFinal = input.inputValue();
        String valorEsperado = formatFecha(fechaEsperada);

        ElementAsserts.assertTrue(
                valorFinal != null && valorFinal.trim().equals(valorEsperado),
                "La fecha del campo '" + label + "' no se actualizó correctamente. " +
                        "Se esperaba: '" + valorEsperado + "', pero se encontró: '" + valorFinal + "'."
        );
    }

    private void clickCalendarOption(Locator locator, String mensajeError) {
        ElementAsserts.assertTrue(
                locator.count() > 0,
                mensajeError + " No se encontró el elemento."
        );

        ElementAsserts.assertTrue(
                locator.isVisible(),
                mensajeError + " El elemento no es visible."
        );

        String ariaDisabled = locator.getAttribute("aria-disabled");
        ElementAsserts.assertTrue(
                !"true".equalsIgnoreCase(ariaDisabled),
                mensajeError + " El elemento está deshabilitado."
        );

        locator.click();
    }

    private void assertVisible(Locator locator, String mensajeError) {
        ElementAsserts.assertTrue(locator.count() > 0, mensajeError);
        ElementAsserts.assertTrue(locator.isVisible(), mensajeError);
    }

    private LocalDate parseFecha(String fecha) {
        try {
            return LocalDate.parse(fecha, FECHA_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Formato de fecha inválido: '" + fecha + "'. Se esperaba dd/MM/yyyy.", e
            );
        }
    }

    private String formatFecha(LocalDate fecha) {
        return fecha.format(FECHA_FORMATTER);
    }

    private String safeText(Locator locator) {
        String text = locator.textContent();
        return text == null ? null : text.trim();
    }

    private int[] parseRangoAnios(String textoRango) {
        String normalizado = textoRango.replace(" ", "").replace("–", "-");
        String[] partes = normalizado.split("-");

        if (partes.length != 2) {
            throw new IllegalArgumentException("No se pudo interpretar el rango de años: '" + textoRango + "'.");
        }

        int inicio = Integer.parseInt(partes[0]);
        int fin = Integer.parseInt(partes[1]);
        return new int[]{inicio, fin};
    }

    private String obtenerNombreMes(int mes) {
        switch (mes) {
            case 1: return "enero";
            case 2: return "febrero";
            case 3: return "marzo";
            case 4: return "abril";
            case 5: return "mayo";
            case 6: return "junio";
            case 7: return "julio";
            case 8: return "agosto";
            case 9: return "septiembre";
            case 10: return "octubre";
            case 11: return "noviembre";
            case 12: return "diciembre";
            default: throw new IllegalArgumentException("Mes inválido: " + mes);
        }
    }
}