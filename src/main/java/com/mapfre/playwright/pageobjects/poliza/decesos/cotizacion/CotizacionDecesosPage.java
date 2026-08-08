package com.mapfre.playwright.pageobjects.poliza.decesos.cotizacion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CotizacionDecesosPage extends BasePage {
    private final Locator productoSelect;
    private final Locator polizaGrupoSelect;
    private final Locator modalidadSelect;
    private final Locator medioPagoSelect;
    private final Locator tipoDocumentoSelect;
    private final Locator numeroDocumentoInput;
    private final Locator nombresInput;
    private final Locator apellidoPaternoInput;
    private final Locator apellidoMaternoInput;
    private final Locator diaNacimientoSelect;
    private final Locator mesNacimientoSelect;
    private final Locator anioNacimientoSelect;
    private final Locator nacionalidadSelect;
    private final Locator sexoSelect;
    private final Locator estadoCivilSelect;
    private final Locator profesionSelect;
    private final Locator telefonoCasaInput;
    private final Locator telefonoMovilInput;
    private final Locator correoElectronicoInput;
    private final Locator paisResidenciaSelect;
    private final Locator departamentoSelect;
    private final Locator provinciaSelect;
    private final Locator distritoSelect;
    private final Locator tipoViaSelect;
    private final Locator nombreViaInput;
    private final Locator tipoNumeroSelect;
    private final Locator enumeracionInput;
    private final Locator aseguradoEsContratanteCheckBox;
    private final Locator agregarAseguradoButton;
    private final Locator primerAseguradoAgregarButton;
    private final Locator agregarOtroAseguradoButton;
    private final Locator tipoDocumento2Select;
    private final Locator numeroDocumento2Input;
    private final Locator agregarSegundoAseguradoButton;
    private final Locator cotizarButton;
    private final Locator agregandoAgregandoAseguradoLabel;
    private final Locator modalErrorGeneral;
    private final Locator aseguradoList;
    private final Locator agregandoAseguradoTitle;
    public CotizacionDecesosPage(Page page) {
        super(page);
        this.productoSelect = page.locator("oim-select[name='nProducto'] select");
        this.polizaGrupoSelect = page.locator("oim-select[name='nPoliza'] select");
        this.modalidadSelect = page.locator("oim-select[name='nModalidad'] select");
        this.medioPagoSelect = page.locator("oim-select[name='nMedioPago'] select");
        this.tipoDocumentoSelect = page.locator("oim-select[name='documentType'] select");
        this.numeroDocumentoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Número de documento"));
        this.nombresInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nombres"));
        this.apellidoPaternoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Apellido Paterno"));
        this.apellidoMaternoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Apellido Materno"));
        this.diaNacimientoSelect = page.locator("oim-select[name='day'] select");
        this.mesNacimientoSelect = page.locator("oim-select[name='month'] select");
        this.anioNacimientoSelect = page.locator("oim-select[name='year'] select");
        this.estadoCivilSelect= page.locator("oim-select[name='civilState'] select");
        this.nacionalidadSelect = page.locator("oim-select[name='nationality'] select");
        this.sexoSelect = page.locator("oim-radio").filter(new Locator.FilterOptions().setHasText("Masculino"));
        this.profesionSelect = page.locator("oim-select[name='Profesion'] select");
        this.telefonoCasaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Teléfono Casa"));
        this.telefonoMovilInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Teléfono móvil"));
        this.correoElectronicoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Correo electrónico"));
        this.paisResidenciaSelect = page.getByLabel("País de residencia");
        this.departamentoSelect = page.locator("oim-select[label='Departamento'] select");
        this.provinciaSelect = page.locator("oim-select[label='Provincia'] select");
        this.distritoSelect = page.locator("oim-select[label='Distrito'] select");
        this.tipoViaSelect = page.locator("oim-select[name='Via'] select");
        this.nombreViaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nombre vía"));
        this.tipoNumeroSelect = page.locator("oim-select[name='NumberType'] select");
        this.enumeracionInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enumeración").setExact(true));
        this.aseguradoEsContratanteCheckBox = page.locator(("oim-checkbox[name='mVal'] mat-checkbox"));
        this.agregarAseguradoButton = page.locator("div.g-section-inner a.g-button:has-text('Agregar asegurado')");
        this.primerAseguradoAgregarButton=page.locator("a.g-button:has-text('AGREGAR ASEGURADO')");
        this.agregarOtroAseguradoButton=page.locator("a.g-button.g-button--second-design:has-text('agregar asegurado')");
        this.tipoDocumento2Select=page.locator("oim-select[name='documentType'] select").last();
        this.numeroDocumento2Input=page.locator("input[name='documentNumber']").last();
        this.agregarSegundoAseguradoButton=page.locator("a.g-button.g-button--second-design:has-text('AGREGAR ASEGURADO')").last();
        this.cotizarButton=page.locator("a.g-button.g-button--second-design:has-text('cotizar')");
        this.agregandoAgregandoAseguradoLabel = page.locator("h2", new Page.LocatorOptions().setHasText("AGREGANDO ASEGURADO"));
        this.modalErrorGeneral = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.aseguradoList = page.locator("div.g-myd-result");
        this.agregandoAseguradoTitle = page.locator("h2:has-text('AGREGANDO ASEGURADO')");

    }

    public void fillFormDatosPoliza(String producto, String polizagrupo, String modalidad, String mediopago) {
        // Implementar la lógica para llenar el formulario de Datos de la Poliza
        selectByOptionIfEnableAndSync(productoSelect,producto);
        //selectByOptionIfEnableAndSync(polizaGrupoSelect,polizagrupo);
        selectByOptionIfEnableAndSync(modalidadSelect,modalidad);
        selectOptionIfEnabledAndSync(medioPagoSelect,mediopago);
    }
    public void fillFormDatosContratante(String tipoDocumento, String numeroDocumento, String estadoCivil, String profesion, String telefonoCasa, String telefonoMovil, String correo, String departamento, String provincia, String distrito, String tipoVia, String nombreVia, String numero, String enumeracion) {
        // Implementar la lógica para llenar el formulario de Datos del Contratante
        selectAndSync(tipoDocumentoSelect, tipoDocumento);
        fillAndBlurSync(numeroDocumentoInput, numeroDocumento);
        waitAppReady();
        sync();
        waitRandomBetween(3000);
        fillInputIfEnabledAndSync(nombresInput, "LUIS");
        fillInputIfEnabledAndSync(apellidoPaternoInput, "ROJAS");
        fillInputIfEnabledAndSync(apellidoMaternoInput, "ROJAS");
        String fechaNacimiento = "15/11/1993";
        String dia = fechaNacimiento.split("/")[0];
        String mes = fechaNacimiento.split("/")[1];
        String anio = fechaNacimiento.split("/")[2];
        selectByOptionIfEnableAndSync(diaNacimientoSelect, dia);
        selectByOptionIfEnableAndSync(mesNacimientoSelect, mes);
        selectByOptionIfEnableAndSync(anioNacimientoSelect, anio);
        selectByOptionIfEnableAndSync(estadoCivilSelect, estadoCivil);
        selectByOptionIfEnableAndSync(nacionalidadSelect, "PERU");
        clickAndSync(sexoSelect);
        selectByOptionIfEnableAndSync(profesionSelect, profesion);
        /*if (ElementAsserts.isElementDisabled(estadoCivilSelect,3000)){
            String valor = estadoCivilSelect.inputValue();
            log.info("El campo estado Civil muestra lo siguiente {}", valor);
        } else {
            // Lógica para llenar el campo Estado Civil
            log.info("El campo estado civil está habilitado, se procede a llenarlo con el valor: {}", estadoCivil);
            selectAndSync(estadoCivilSelect,estadoCivil);

        }*/

        clearAndFill(telefonoCasaInput,telefonoCasa);
        clearAndFill(telefonoMovilInput,telefonoMovil);
        clearAndFill(correoElectronicoInput,correo);
        selectByOptionIfEnableAndSync(paisResidenciaSelect, "PERU");
        selectByOptionIfEnableAndSync(departamentoSelect, departamento);
        selectByOptionIfEnableAndSync(provinciaSelect, provincia);
        selectByOptionIfEnableAndSync(distritoSelect, distrito);
        selectByOptionIfEnableAndSync(tipoViaSelect, tipoVia);
        clearAndFill(nombreViaInput, nombreVia);
        selectByOptionIfEnableAndSync(tipoNumeroSelect, numero);
        clearAndFill(enumeracionInput, enumeracion);
    }

    public void clickAgregarAsegurado() {
        // Implementar la lógica para hacer clic en el botón "Agregar Asegurado"
        agregarAseguradoButton.scrollIntoViewIfNeeded();
        clickAndSync(agregarAseguradoButton);

    }

    public void processAndAssertSucessAgregandoAseguradoSeccion(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                modalErrorGeneral,
                agregandoAgregandoAseguradoLabel,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
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
    }
    public void clickAseguradoEsContratante() {
        clickAndSync(aseguradoEsContratanteCheckBox);
        waitForDOMContentLoaded();
        waitForNetworkIdle();
        waitRandomBetween(5000);
    }

    public void clickAgregarPrimerAsegurado() {
        primerAseguradoAgregarButton.scrollIntoViewIfNeeded();
        clickAndSync(primerAseguradoAgregarButton);
    }

    public void processAndAssertSucessAseguradoRegistradoSeccion(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                modalErrorGeneral,
                aseguradoList.first(),
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
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
    }

    public void clickAgregarOtroAsegurado() {
        agregarAseguradoButton.scrollIntoViewIfNeeded();
        clickAndSync(agregarOtroAseguradoButton);
    }
    public void fillFormSegundoAsegurado(String tipoDocumento2, String numeroDocumento2) {
        selectAndSync(tipoDocumento2Select, tipoDocumento2);
        fillAndBlurSync(numeroDocumento2Input, numeroDocumento2);
    }
    public void clickSegundoAseguradoAgregarButton() {
        agregarSegundoAseguradoButton.scrollIntoViewIfNeeded();
        clickAndSync(agregarSegundoAseguradoButton);
        waitRandomBetween(5000);
        sync();
        ElementAsserts.assertHidden(agregandoAseguradoTitle, "El título 'AGREGANDO ASEGURADO' sigue visible después de hacer clic en el botón 'AGREGAR ASEGURADO'. Esto indica que el asegurado no se ha agregado correctamente.");
    }
    public void clickcotizarButton() {
        cotizarButton.scrollIntoViewIfNeeded();
        clickAndSync(cotizarButton);
    }

    public void validarAseguradoRegistrado(int numeroAseguradoEsperado) {
        int numeroAseguradoActual = aseguradoList.count();
        ElementAsserts.assertTrue(numeroAseguradoActual == numeroAseguradoEsperado, "El número de asegurados registrados no es el esperado. Se esperaba: " + numeroAseguradoEsperado + " pero se encontró: " + numeroAseguradoActual);

        for (int i = 0; i < numeroAseguradoActual; i++) {
            Locator aseguradoItem = aseguradoList.nth(i);
            Locator numeroDocumentoLabel = aseguradoItem.locator("li.cnt-item:has(div.item-label:has-text('Número de documento:')) div.item-dato");
            ElementAsserts.assertVisible(numeroDocumentoLabel, "El número de documento del asegurado numero "+i + 1+" registrado no se muestra en la sección de asegurados después de agregarlo.");
            ElementAsserts.assertFilled(numeroDocumentoLabel, "El número de documento del asegurado numero "+i + 1+" registrado no se muestra correctamente en la sección de asegurados después de agregarlo.");
            String numeroDocumento = numeroDocumentoLabel.textContent();
            log.info("Número de documento del asegurado {}: {}", i + 1, numeroDocumento);
        }
    }
}
