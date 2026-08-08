package com.mapfre.utils.waits;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public final class GestorModal {

    private static final long TIMEOUT_MS = 5000;
    private static final long QUIET_MS = 300;
    private static final long POLL_MS = 100;

    private GestorModal() {
    }

    /**
     * Hace click en un botón del modal si éste aparece.
     *
     * @param modalLocator locator del modal
     * @param nombreBoton texto exacto del botón
     * @return true si el modal apareció y se hizo click; false si nunca apareció
     */
    public static boolean clickBotonSiExiste(
            Locator modalLocator,
            String nombreBoton
    ) {

        boolean modalVisible = OptionalModalWait.waitForOptionalModal(
                modalLocator.page(),
                modalLocator,
                TIMEOUT_MS,
                QUIET_MS,
                POLL_MS
        );

        if (!modalVisible) {
            return false;
        }

        modalLocator.waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
        );

        Locator boton = modalLocator.getByRole(
                AriaRole.BUTTON,
                new Locator.GetByRoleOptions()
                        .setName(nombreBoton)
        );

        boton.click();

        return true;
    }

    /**
     * Obtiene el texto de cualquier locator.
     *
     * @param locator locator del elemento
     * @return texto limpio o cadena vacía si ocurre un error
     */
    public static String obtenerTexto(Locator locator) {
        try {
            String texto = locator.textContent();
            return texto != null ? texto.trim() : "";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Verifica si un modal aparece y se estabiliza.
     *
     * @param modalLocator locator del modal
     * @return true si el modal apareció
     */
    public static boolean existeModal(
            Locator modalLocator
    ) {

        return OptionalModalWait.waitForOptionalModal(
                modalLocator.page(),
                modalLocator,
                TIMEOUT_MS,
                QUIET_MS,
                POLL_MS
        );
    }
}