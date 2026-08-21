package com.mapfre.utils;

import com.mapfre.exceptions.FrameworkException;
import com.microsoft.playwright.Download;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Objects;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public final class PdfContentValidator {

    private PdfContentValidator() {
    }

    public static void validatePdfIsReadable(Download download) {
        validatePdfContainsText(download, null);
    }

    public static void validatePdfContainsText(Download download, String expectedText) {
        Objects.requireNonNull(download, "download");

        try (InputStream inputStream = download.createReadStream();
             PDDocument document = PDDocument.load(inputStream)) {

            if (document.isEncrypted()) {
                throw new FrameworkException("El PDF descargado está cifrado y no se puede validar.");
            }

            String extractedText = new PDFTextStripper().getText(document);
            if (extractedText == null || extractedText.trim().isEmpty()) {
                throw new FrameworkException("El PDF descargado no contiene texto extraíble.");
            }

            if (expectedText != null && !expectedText.isBlank()) {
                String normalizedText = normalize(extractedText);
                String normalizedExpectedText = normalize(expectedText);
                if (!normalizedText.contains(normalizedExpectedText)) {
                    throw new FrameworkException("El PDF descargado no contiene el texto esperado: " + expectedText);
                }
            }

        } catch (IOException e) {
            throw new FrameworkException("No se pudo validar el contenido del PDF descargado en memoria.", e);
        } catch (RuntimeException e) {
            if (e.getCause() instanceof FrameworkException) {
                throw (FrameworkException) e.getCause();
            }
            throw new FrameworkException("Error al validar contenido del PDF descargado en memoria.", e);
        }
    }

    private static String normalize(String value) {
        if (value == null) {
            return "";
        }
        return value.replaceAll("\\s+", " ").trim().toLowerCase(Locale.ROOT);
    }
}
