package com.mapfre.playwright.pageobjects.poliza.auto.cotizacion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.Checkpoint;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

public class ProductosACotizarPage extends BasePage {
    private final Locator productoSelect;
    private final Locator tipoUsoSelect;
    private final Locator inicioVigenciaInput;
    private final Locator tuElecccionDoradaPremiumCheckBox;
    private final Locator generarCotizacionButton;
    private final Locator ventanaConfirmacionTitulo;
    private final Locator ventanaConfirmacionDescripcion;
    private final Locator ventanaConfirmacionYErrorOKButton;
    private final Locator ventanaErrorTitulo;
    private final Locator ventanaErrorDescripcion;
    private final Locator successSummaryLocator;
    private final Locator warningErrorDescripcion;
    private final Locator title;

    public ProductosACotizarPage(Page page) {
        super(page);
        this.productoSelect = page.getByLabel("Producto");
        this.tipoUsoSelect = page.getByLabel("Tipo de uso");
        this.inicioVigenciaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName(Pattern.compile("Inicio Vigencia:")));
        this.tuElecccionDoradaPremiumCheckBox = page.locator("span").filter(new Locator.FilterOptions().setHasText("DORADA / PREMIUM I"));
        this.generarCotizacionButton = page.getByText("Generar Cotización");
        this.ventanaConfirmacionTitulo = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName(Pattern.compile("¿Estás seguro que quieres")));
        this.ventanaConfirmacionDescripcion = page.getByText("Recuerda que una vez guardada");
        this.ventanaConfirmacionYErrorOKButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK"));
        this.ventanaErrorTitulo = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Error"));
        this.ventanaErrorDescripcion = page.getByText(Pattern.compile("Error en"));
        this.successSummaryLocator = page.getByLabel("successSummaryLocator");
        this.warningErrorDescripcion = page.getByLabel(Pattern.compile("Para continuar"));
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Cotización guardada de autos"));
    }

    public void fillFormEligeProducto(String producto, String tipoUso) {
        selectAndSync(productoSelect,producto);
        selectAndSync(tipoUsoSelect,tipoUso);
        scrollPageBy(0,100);
        Checkpoint.capture("Campo Inicio de Vigencia");
        ElementAsserts.assertFilled(inicioVigenciaInput,"Campo inicio de vigencia");
    }

    public void clickFormEligeProducto(){
        scrollPageBy(0,10000);
        clickAndSync(generarCotizacionButton);
        if(ElementAsserts.assertVisible(ventanaConfirmacionTitulo,5000)){
            log.info("Se muestra la ventana para confirmar la cotizacion: "+ ventanaConfirmacionTitulo.innerText());
            clickAndSync(ventanaConfirmacionYErrorOKButton);
        }else if(ElementAsserts.assertVisible(warningErrorDescripcion,5000)){
            ElementAsserts.assertVisibileUIMessage(warningErrorDescripcion,"Error - "+ventanaConfirmacionTitulo.innerText());
        }else{
            log.info("No se mostró la ventana de confirmación ni la ventana de warning");
        }
    }

    public void processAndAssertSuccess(long timeoutMs, long quietMs){
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizad)
                ventanaErrorDescripcion,
                title,
                ventanaErrorTitulo,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + ventanaErrorDescripcion.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            throw new FrameworkException("Error de timeout ni SUCCESS or ERROR econtrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
    }
}
