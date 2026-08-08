    package com.mapfre.playwright.pageobjects.poliza.soat;

    import com.mapfre.asserts.ElementAsserts;
    import com.mapfre.playwright.pageobjects.BasePage;
    import com.microsoft.playwright.Locator;
    import com.microsoft.playwright.Page;
    import com.microsoft.playwright.options.SelectOption;
    import com.microsoft.playwright.options.WaitForSelectorState;

    import static com.mapfre.utils.UiSync.waitForAppIdle;
    import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

    public class EmisionPolizaSoatPage extends BasePage {

        private final Locator tituloEmisionSoat;
        private final Locator tituloDatosContratante;

        // INPUTS VEHICULO
        private final Locator txtNumeroPlaca;
        private final Locator inputMarcaModelo;
        private final Locator inputNumeroChasis;
        private final Locator inputAnioFabricacion;
        private final Locator inputNumeroAsientos;

        // SELECTS VEHICULO
        private final Locator selectTipoVehiculo;
        private final Locator selectProducto;
        private final Locator selectTipoUso;

        // MODAL
        private final Locator btnOkModalAlerta;
        private final Locator btnSiguiente;

        // AUTOCOMPLETADO
        private final Locator opcionesAutocomplete;

        // ==================== DATOS DEL CONTRATANTE ====================
        private final Locator selectTipoDocumento;
        private final Locator txtNumeroDocumento;

        private final Locator inputNombres;
        private final Locator inputApellidoPaterno;
        private final Locator inputApellidoMaterno;

        private final Locator inputTelefono;
        private final Locator inputCorreo;

        // ================= DATOS DE DIRECCIÓN =================
        private final Locator selectDepartamento;
        private final Locator selectProvincia;
        private final Locator selectDistrito;

        private final Locator selectTipoVia;
        private final Locator inputNombreVia;

        // ================= VALIDAR CALCULO DE PRIMA =================
        private final Locator lblTotalPrima;
        private final Locator btnEmitirSoat;

        // ================= CALCULAR PRIMA =================
        private final Locator btnCalcularPrima;

        private final Locator modalError;
        private final Locator mensajeModalError;
        private final Locator btnOkModalError;
        private final Locator btnExpansion;

        public EmisionPolizaSoatPage(Page page) {
            super(page);

            this.tituloEmisionSoat = page.getByText("Emisión póliza SOAT");
            this.tituloDatosContratante = page.getByText("Datos del contratante");

            // VEHICULO
            this.txtNumeroPlaca = page.locator("input[name='nPlaca']");
            this.inputMarcaModelo = page.locator("input[name='nModeloMarca']");
            this.inputNumeroChasis = page.locator("input[name='nNumeroChasis']");
            this.inputAnioFabricacion = page.locator("input[name='year']");
            this.inputNumeroAsientos = page.locator("input[name='nNumAsientos']");

            this.selectTipoVehiculo = page.locator("select[matnativecontrol]").nth(0);
            this.selectProducto     = page.locator("select[matnativecontrol]").nth(1);
            this.selectTipoUso      = page.locator("select[matnativecontrol]").nth(2);

            this.btnOkModalAlerta = page.locator(".swal2-confirm");
            this.btnSiguiente = page.getByText("Siguiente");

            this.opcionesAutocomplete = page.locator("[role='option']");

            // ================= CONTRATANTE =================
            this.selectTipoDocumento = page.locator("select").nth(0);
            this.txtNumeroDocumento = page.locator("input[name='documentNumber']");

            this.inputNombres = page.locator("input[name='Nombre']");
            this.inputApellidoPaterno = page.locator("input[name='ApellidoPaterno']");
            this.inputApellidoMaterno = page.locator("input[name='ApellidoMaterno']");

            this.inputTelefono = page.locator("input[name='Telefono2']");
            this.inputCorreo = page.locator("input[name='CorreoElectronico']");
            // ========= DIRECCIÓN =========
            this.selectDepartamento = page.locator("select").nth(1);
            this.selectProvincia   = page.locator("select").nth(2);
            this.selectDistrito    = page.locator("select").nth(3);
            // Tipo de vía
            this.selectTipoVia = page.locator("select").nth(4);
            // Nombre vía
            this.inputNombreVia = page.locator("input[name='NombreVia']");
            // ================= CALCULAR PRIMA =================
            this.btnCalcularPrima= page.getByText("Calcular prima");
            // ================= VALIDAR CALCULO DE PRIMA =================
            this.lblTotalPrima = page.locator("text=S/").last();
            this.btnEmitirSoat = page.getByText(" Emitir Soat ");
            // ===== MODAL ERROR EMISIÓN =====
            this.modalError = page.locator(".swal2-popup");
            this.mensajeModalError = page.locator("#swal2-html-container");
            this.btnOkModalError = page.locator(".swal2-confirm");
            this.btnExpansion = page.locator("//mat-expansion-panel-header[.//b[contains(text(),'Póliza SOAT')]]");
        }
        public void assertLoaded() {
            assertThat(tituloEmisionSoat).isVisible();
        }
        public void assertLoadedDatosContratante(){
            assertThat(tituloDatosContratante).isVisible();
        }
        // ================= VEHICULO =================
        public void ingresarDatosVehiculo(String placa,
                                          String tipoVehiculo,
                                          String marcaModelo,
                                          String chasis,
                                          String anioFabricacion,
                                          String producto,
                                          String tipoUso,
                                          String nroAsientos) {

            // autogenerar placa
            String placaFinal = (placa == null || placa.trim().isEmpty())
                    ? autoGenerarPlaca(tipoVehiculo)
                    : placa;

            txtNumeroPlaca.fill(placaFinal);
            txtNumeroPlaca.press("Tab");
            log.info("✅ Placa usada: {}", placaFinal);
            manejarModalSiExiste();
            validarModalGlobal();
            waitForAppIdle();
            manejarSelect(selectTipoVehiculo, tipoVehiculo);
            validarModalGlobal();
            manejarSelect(selectProducto, producto);
            validarModalGlobal();
            manejarSelect(selectTipoUso, tipoUso);
            validarModalGlobal();
            llenarMarcaModelo(marcaModelo);
            String chasisFinal = (chasis == null || chasis.trim().isEmpty())
                    ? autoGenerarChasis()
                    : chasis;
            validarModalGlobal();
            manejarInput(inputNumeroChasis, chasisFinal);
            validarModalGlobal();
            manejarInput(inputAnioFabricacion, anioFabricacion);
            validarModalGlobal();
            inputNumeroAsientos.fill("");
            validarModalGlobal();
            //inputNumeroAsientos.fill(nroAsientos);
            pressSequentiallyAndBlurSync(inputNumeroAsientos, nroAsientos);
            ElementAsserts.assertTextHasValue(inputNumeroAsientos, nroAsientos, "validar número de asientos");
            validarModalGlobal();
            inputNumeroAsientos.press("Tab");
        }

        // ================= CONTRATANTE =================

        public void ingresarDatosContratante() {

            // Tipo documento
            manejarSelect(selectTipoDocumento, "DNI");

            txtNumeroDocumento.fill("45228115");
            txtNumeroDocumento.press("Tab");

            log.info("Número documento ingresado");

            // Espera backend
            page.waitForTimeout(500);
        }

        public void ingresarDatosPrincipales(){
            // Datos principales
            manejarInput(inputNombres, "JUAN");
            manejarInput(inputApellidoPaterno, "PEREZ");
            manejarInput(inputApellidoMaterno, "GOMEZ");

        }
        public void ingresarDatosContacto(){
            // Datos contacto
            manejarInput(inputTelefono, "946256844");
            manejarInput(inputCorreo, "test@email.com");
        }

        public void ingresarDatosDireccion() {
            // Departamento
            manejarSelect(selectDepartamento, "LIMA");
            page.waitForTimeout(500);
            // Provincia
            manejarSelect(selectProvincia, "LIMA");
            // esperar que cargue Distrito
            page.waitForTimeout(500);
            // Distrito
            manejarSelect(selectDistrito, "MIRAFLORES");
            // Tipo de vía
            manejarSelect(selectTipoVia, "CALLE");
            // Nombre vía
            manejarInput(inputNombreVia, "LARCO");
            log.info("Dirección ingresada correctamente");
        }


        private void manejarModalSiExiste() {

            int intentos = 0;
            long inicio = System.currentTimeMillis();

            while (System.currentTimeMillis() - inicio < 6000) {  // hasta 6 segundos

                try {

                    if (modalError.isVisible()) {

                        log.warn("Modal detectado (intento {}), cerrando...", intentos + 1);

                        page.evaluate("""
                        const btn = document.querySelector('.swal2-confirm');
                        if (btn) btn.click();
                    """);

                        modalError.waitFor(new Locator.WaitForOptions()
                                .setState(com.microsoft.playwright.options.WaitForSelectorState.HIDDEN));
                        intentos++;
                    }

                } catch (Exception ignored) {}
                page.waitForTimeout(400);
            }
            log.info("Fin manejo de modales (listener completo)");
        }
        private void manejarInput(Locator locator, String value) {

            if (value == null || value.isEmpty()) return;

            if (!locator.isEditable()) {
                log.info("Campo bloqueado");
                return;
            }
            String current = locator.inputValue().trim();
            if (!current.isEmpty() && !current.equals("0")) {
                log.info("Campo ya con valor: {}", current);
                return;
            }
            locator.scrollIntoViewIfNeeded();
            locator.click();
            locator.fill("");
            locator.fill(value);
            locator.blur();
            log.info("Campo llenado: {}", value);
        }

        private void manejarSelect(Locator select, String value) {

            if (value == null || value.isEmpty()) return;
            select.waitFor();
            String current = select.locator("option:checked").textContent();
            String val = current == null ? "" : current.trim().toUpperCase();
            boolean esValorInicial = val.isEmpty() || val.contains("SELECCIONE") || val.contains("TIPO");

            if (!esValorInicial) {
                log.info(" Select ya tiene valor: {}", current);
                return;
            }

            select.selectOption(new SelectOption().setLabel(value));
            log.info(" Select seteado a: {}", value);
        }
        private void llenarMarcaModelo(String value) {

            if (value == null || value.isEmpty()) return;

            if (!inputMarcaModelo.isEditable()) {
                log.info("Marca y modelo bloqueado");
                return;
            }

            String current = inputMarcaModelo.evaluate("el => el.value").toString().trim();

            if (!current.isEmpty() && current.equalsIgnoreCase(value)) {
                log.info("Marca ya correcta: {}", current);
                return;
            }

            inputMarcaModelo.scrollIntoViewIfNeeded();
            inputMarcaModelo.click();
            inputMarcaModelo.fill(""); // ✅ limpiar por si acaso

            // USAR EL VALOR DINÁMICO
            inputMarcaModelo.pressSequentially(value,
                    new Locator.PressSequentiallyOptions().setDelay(80));

            opcionesAutocomplete.first().waitFor(new Locator.WaitForOptions().setTimeout(5000));

            Locator opcion = opcionesAutocomplete
                    .filter(new Locator.FilterOptions().setHasText(value))
                    .first();

            if (opcion.count() > 0) {
                opcion.click();
                log.info("Marca seleccionada exacta: {}", value);
            } else {
                opcionesAutocomplete.first().click();
                log.warn("No encontró coincidencia exacta, selecciona primera opción");
            }
        }


        public void clickSiguiente() {
            btnSiguiente.scrollIntoViewIfNeeded();
            //assertThat(btnSiguiente).isVisible();
            ElementAsserts.assertVisible(btnSiguiente, "validar botón siguiente");
            page.waitForTimeout(300);

            try {
                btnSiguiente.click();
                log.info("Click botón siguiente realizado");

            } catch (Exception e) {
                btnSiguiente.click(new Locator.ClickOptions().setForce(true));
                log.info("Click forzado ejecutado");
            }
        }
        public void clickCalcularprima(){
            btnCalcularPrima.scrollIntoViewIfNeeded();
            assertThat(btnCalcularPrima).isVisible();
            btnCalcularPrima.click();
            waitForAppIdle();
        }
        public void validarPrimaCalculada() {

            String monto = lblTotalPrima.textContent();

            if (monto == null || monto.trim().isEmpty()) {
                throw new RuntimeException("No se muestra monto en total");
            }

            if (!monto.contains("S/")) {
                throw new RuntimeException("El monto no tiene formato esperado: " + monto);
            }
            log.info("Prima calculada correctamente: {}", monto);
        }

        public void emitirPolizaYValidarResultado(){
            btnEmitirSoat.click();
            waitForAppIdle();
            boolean hayError = false;
            String mensaje = "";

            try {
                mensajeModalError.waitFor(new Locator.WaitForOptions().setTimeout(5000));
                hayError = true;
                mensaje = mensajeModalError.textContent();
            } catch (Exception e) {

            }

            if (hayError) {
                log.error("ERROR EN EMISIÓN: {}", mensaje);
                page.evaluate("""
                const btn = document.querySelector('.swal2-confirm');
                if (btn) btn.click();
            """);
                throw new RuntimeException("Fallo en emisión SOAT: " + mensaje);
            }

            log.info("Póliza emitida correctamente");
            btnExpansion.click();
            page.waitForTimeout(200);

        }
        private String autoGenerarPlaca(String tipoVehiculo) {

            if ("MOTOCICLETA".equalsIgnoreCase(tipoVehiculo)) {
                return generarPlacaMoto();
            }

            return generarPlacaAuto();
        }

        private String autoGenerarChasis() {

            // VIN válido sin caracteres prohibidos
            String caracteres = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";

            StringBuilder chasis = new StringBuilder();

            for (int i = 0; i < 17; i++) {
                chasis.append(caracteres.charAt((int) (Math.random() * caracteres.length())));
            }

            String resultado = chasis.toString();

            log.info("Chasis auto generado: {}", resultado);

            return resultado;
        }
        private void validarModalGlobal() {

            try {
                if (page.locator(".swal2-popup").isVisible()) {

                    log.warn("Modal detectado globalmente...");

                    page.evaluate("""
                    const btn = document.querySelector('.swal2-confirm');
                    if (btn) btn.click();
                """);

                    page.locator(".swal2-popup").waitFor(
                            new Locator.WaitForOptions()
                                    .setState(com.microsoft.playwright.options.WaitForSelectorState.HIDDEN)
                    );

                    log.info("Modal cerrado globalmente");
                }
            } catch (Exception ignored) {}
        }
        private String generarPlacaAuto() {

            String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String numeros = "0123456789";

            StringBuilder placa = new StringBuilder();

            // formato correcto autos Perú: LLLNNN
            for (int i = 0; i < 3; i++) {
                placa.append(letras.charAt((int) (Math.random() * letras.length())));
            }

            for (int i = 0; i < 3; i++) {
                placa.append(numeros.charAt((int) (Math.random() * numeros.length())));
            }

            String resultado = placa.toString();
            log.info("🚗 Placa auto generada: {}", resultado);
            return resultado;
        }

        private String generarPlacaMoto() {

            String letras = "ABCDF";
            String numeros = "0123456789";

            StringBuilder placa = new StringBuilder();

            // 30% nuevo formato, 70% antiguo
            boolean formatoNuevo = Math.random() < 0.3;

            if (formatoNuevo) {
                // LLLNNNN
                for (int i = 0; i < 3; i++) {
                    placa.append(letras.charAt((int) (Math.random() * letras.length())));
                }
            } else {
                // LLNNNN
                for (int i = 0; i < 2; i++) {
                    placa.append(letras.charAt((int) (Math.random() * letras.length())));
                }
            }

            for (int i = 0; i < 4; i++) {
                placa.append(numeros.charAt((int) (Math.random() * numeros.length())));
            }

            String resultado = placa.toString();
            log.info("🏍 Placa moto generada: {}", resultado);
            return resultado;
        }

    }