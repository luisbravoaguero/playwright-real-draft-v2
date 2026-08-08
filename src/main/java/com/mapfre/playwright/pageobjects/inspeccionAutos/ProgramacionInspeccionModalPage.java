package com.mapfre.playwright.pageobjects.inspeccionAutos;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.DateUtils;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.time.LocalDate;

public class ProgramacionInspeccionModalPage extends BasePage {
    private final Locator programarInspeccionModal;
    private final Locator inspectorSelect;
    private final Locator fechaInicioInput;
    private final Locator horaInicioInput;
    private final Locator fechaFinInput;
    private final Locator horFinInput;
    private final Locator departamentoSelect;
    private final Locator provinciaSelect;
    private final Locator distritoSelect;
    private final Locator direccionInput;
    private final Locator programarButton;
    private final Locator resultadoModalMensajeError;
    private final Locator resultadoExitosoModal;
    private final Locator mensajeSolicitudExitosaModal;
    private final Locator OkSolicitudExitosaModalButton;
    private final Locator breadcrumbsTitle;
    private final Locator programacionesH1H2H3Title;
    public ProgramacionInspeccionModalPage(Page page) {
        super(page);
        this.programarInspeccionModal = page.locator("mat-dialog-container").filter(new Locator.FilterOptions().setHasText("PROGRAMAR INSPECCIÓN"));
        this.inspectorSelect = page.getByLabel("Inspector", new Page.GetByLabelOptions().setExact(true));
        this.fechaInicioInput = page.locator("oim-datepicker[formcontrolname='startDate'] input");
        this.horaInicioInput = page.locator("oim-timepicker[formcontrolname='startHour'] input");
        this.fechaFinInput = page.locator("oim-datepicker[formcontrolname='endDate'] input");
        this.horFinInput = page.locator("oim-timepicker[formcontrolname='endHour'] input");
        this.departamentoSelect = page.getByLabel("Departamento");
        this.provinciaSelect = page.getByLabel("Provincia");
        this.distritoSelect = page.getByLabel("Distrito");
        this.direccionInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Dirección"));
        this.programarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("PROGRAMAR"));
        this.resultadoModalMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.resultadoExitosoModal = page.locator("div.swal2-popup.swal2-icon-success[role='dialog']");
        this.mensajeSolicitudExitosaModal = resultadoExitosoModal.locator("#swal2-html-container");
        this.OkSolicitudExitosaModalButton = resultadoExitosoModal.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Ok"));
        this.breadcrumbsTitle = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Inspección de Autos >"));
        this.programacionesH1H2H3Title = page.locator("h1, h2, h3").filter(new Locator.FilterOptions().setHasText("Programaciones")).first();

    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(programarInspeccionModal, "El modal de PROGRAMAR INSPECCIÓN DEBE SER VISIBLE");

    }

    public void fillFormProgramacionInspeccion(int inspecctorIndex) {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        log.info("La fecha de hoy es: {}, se seleccionará la fecha de programación para mañana: {}", today, tomorrow);
        String inspector = inspectorSelect.inputValue();
        selectIndexIfEmptyAndSync(inspectorSelect, inspecctorIndex);
        log.info("Valor prellenado en el campo Inspector del formulario de programación de inspección: {}", inspector);
        String fechaInicio = fechaInicioInput.inputValue().trim();
        String horaInicio = horaInicioInput.inputValue().trim();
        String fechaFin = fechaFinInput.inputValue().trim();
        String horaFin = horFinInput.inputValue().trim();

        if(tomorrow.isEqual(DateUtils.parseStringDdMmYyyyToLocalDate(fechaInicio))) {
            log.info("La fecha de inicio ya está prellenada con la fecha de mañana: {}", fechaInicio);
        } else {
            log.warn("La fecha de inicio NO está prellenada con la fecha de mañana. Valor actual: {}", fechaInicio);
            fillReadonlyDate(fechaInicioInput,DateUtils.formatLocalDateToStringDdMmYyyy(tomorrow));
        }

        if(horaInicio.equalsIgnoreCase("10:30 AM")) {
            log.info("La hora de inicio ya está prellenada con la hora correcta: {}", horaInicio);
        } else {
            log.warn("La hora de inicio NO está prellenada con la hora correcta: {}", horaInicio);
            fillReadonlyDate(horaInicioInput,"10:30 AM");
        }

        if(tomorrow.isEqual(DateUtils.parseStringDdMmYyyyToLocalDate(fechaFin))) {
            log.info("La fecha fin ya está prellenada con la fecha de mañana: {}", fechaFin);
        } else {
            log.warn("La fecha fin NO está prellenada con la fecha de mañana. Valor actual: {}", fechaFin);
            fillReadonlyDate(fechaFinInput, DateUtils.formatLocalDateToStringDdMmYyyy(tomorrow));
        }

        if(horaFin.equalsIgnoreCase("11:00 AM")) {
            log.info("La hora fin ya está prellenada con la fecha de mañana: {}", horaFin);
        } else {
            log.warn("La hora fin NO está prellenada con la fecha de mañana. Valor actual: {}", horaFin);
            fillReadonlyDate(horFinInput, "11:00 AM");
        }

        log.info("Valores prellenados en el formulario de programación de inspección: Fecha Inicio = {}, Hora Inicio = {}, Fecha Fin = {}, Hora Fin = {}", fechaInicio, horaInicio, fechaFin, horaFin);
        selectAndSync(departamentoSelect, "LIMA");
        selectAndSync(provinciaSelect, "LIMA");
        selectAndSync(distritoSelect, "MIRAFLORES");
        fillAndSync(direccionInput, "Av. Larco 1234");
        log.info("[INSPECCION AUTOS][PROGRAMAR INSPECCION] Formulario de programación de inspección llenado con fecha de inicio: {}, hora de inicio: {}, fecha fin: {}, hora fin: {}, departamento: {}, provincia: {}, distrito: {} y dirección: {}",
                fechaInicioInput.inputValue(), horaInicioInput.inputValue(), fechaFinInput.inputValue(), horFinInput.inputValue(),
                departamentoSelect.inputValue(), provinciaSelect.inputValue(), distritoSelect.inputValue(), direccionInput.inputValue());
    }

    public void fillFormProgramacionInspeccion() {
        selectAndSync(departamentoSelect, "LIMA");
        selectAndSync(provinciaSelect, "LIMA");
        selectAndSync(distritoSelect, "MIRAFLORES");
        fillAndSync(direccionInput, "Av. Larco 1234");
        log.info("[INSPECCION AUTOS][PROGRAMAR INSPECCION] Formulario de programación de inspección llenado con departamento: {}, provincia: {}, distrito: {} y dirección: {}",
                departamentoSelect.inputValue(), provinciaSelect.inputValue(), distritoSelect.inputValue(), direccionInput.inputValue());
    }



    public void processAndAssertSuccessModalProgramacionInspeccionProgramarButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(programarButton, "El boton PROGRAMAR DEBE SER VISIBLE para procesar la programación de la inspección");
        ElementAsserts.assertEnabled(programarButton, "El boton PROGRAMAR DEBE SER HABILITADO para procesar la programación de la inspección");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                programarButton::click,               // action (coloca null si la accion click fue realizad)
                resultadoModalMensajeError,
                resultadoExitosoModal,
                null,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Programación de inspección procesada exitosamente, modal de resultado exitoso apareció con mensaje: {}", mensajeSolicitudExitosaModal.textContent());
    }

    public void processAndAssertSuccessModalSolicitudExitosaOkButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(resultadoExitosoModal, "El modal de resultado exitoso DEBE SER VISIBLE después de programar la inspección");
        ElementAsserts.assertVisible(OkSolicitudExitosaModalButton, "El botón Ok del modal de resultado exitoso DEBE SER VISIBLE para cerrar el modal después de programar la inspección");
        ElementAsserts.assertEnabled(OkSolicitudExitosaModalButton, "El botón Ok del modal de resultado exitoso DEBE SER HABILITADO para cerrar el modal después de programar la inspección");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                OkSolicitudExitosaModalButton::click,               // action (coloca null si la accion click fue realizad)
                resultadoModalMensajeError,
                programacionesH1H2H3Title,
                null,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Modal de resultado exitoso cerrado exitosamente, se visualiza el título de Programaciones: {}", programacionesH1H2H3Title.textContent());
    }
}