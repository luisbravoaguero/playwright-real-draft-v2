package com.mapfre.playwright.support.export;

import com.mapfre.models.SctrDocumento;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Exporta documentos SCTR a un único archivo TXT dentro de:
 *   target/Export/documentoSctr/DocumentoSctr_<MMdd_HHmmss>.txt
 *
 * - NO crea carpetas por ejecución.
 * - NO deduplica: exporta exactamente lo que se recibió (incluye duplicados).
 * - Si ya existe un archivo con el mismo timestamp (misma ejecución en el mismo segundo),
 *   agrega sufijo _1, _2, ... para evitar sobrescribir.
 */
public class SctrExportService {

    private static final Logger LOG = LoggerFactory.getLogger(SctrExportService.class);

    /** Directorio base por defecto (ojo: 'Export' con mayúscula). */
    private final Path baseDir;

    /** Formato del timestamp para el nombre del archivo: MMdd_HHmmss */
    private static final DateTimeFormatter TS_FILE = DateTimeFormatter.ofPattern("MMdd_HHmmss");

    public SctrExportService() {
        this(Paths.get("target", "export", "documentoSctr"));
    }

    public SctrExportService(Path baseDir) {
        this.baseDir = Objects.requireNonNull(baseDir, "baseDir no puede ser null");
    }

    /**
     * Exporta a un único TXT: target/Export/documentoSctr/DocumentoSctr_<MMdd_HHmmss>.txt
     * SIN deduplicar (se respetan los duplicados tal cual vinieron).
     *
     * @param docs lista de documentos tal cual los leyó la UI (puede contener duplicados)
     * @return ruta al archivo TXT generado
     */
    public Path exportTxt(List<SctrDocumento> docs) {
        Objects.requireNonNull(docs, "docs no puede ser null");
        try {
            Files.createDirectories(baseDir);

            final int totalLeidos = docs.size(); // total tal cual
            LOG.info("[SCTR] Nro de documentos exportados a .TXT: total={}", totalLeidos);

            String baseName = "DocumentoSctr_" + TS_FILE.format(LocalDateTime.now());
            Path txt = nextAvailable(baseDir.resolve(baseName + ".txt"));

            writeTxt(txt, docs); // <<-- escribimos la lista tal cual, sin filtrar

            LOG.info("[SCTR] TXT: {}", txt.toAbsolutePath());
            return txt;

        } catch (IOException e) {
            throw new IllegalStateException("Error exportando Documentos SCTR a TXT", e);
        }
    }

    /** Si el path existe, agrega sufijo _1, _2, ... hasta hallar nombre disponible. */
    private Path nextAvailable(Path initial) throws IOException {
        if (!Files.exists(initial)) return initial;

        String fileName = initial.getFileName().toString();          // DocumentoSctr_MMdd_HHmmss.txt
        int dot = fileName.lastIndexOf('.');
        String name = dot > 0 ? fileName.substring(0, dot) : fileName; // DocumentoSctr_MMdd_HHmmss
        String ext  = dot > 0 ? fileName.substring(dot) : "";          // .txt

        int i = 1;
        Path candidate;
        do {
            candidate = initial.getParent().resolve(name + "_" + i + ext);
            i++;
        } while (Files.exists(candidate));
        return candidate;
    }

    /** Escribe cada documento en una línea. No altera el orden ni elimina duplicados. */
    private void writeTxt(Path path, List<SctrDocumento> docs) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(path, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {

            for (SctrDocumento d : docs) {
                String linea = d.linea() == null ? "" : d.linea();
                String fecha = d.fechaRegistro();
                if (fecha != null && !fecha.isBlank()) {
                    w.write(linea + " | Fecha: " + fecha);
                } else {
                    w.write(linea);
                }
                w.newLine();
            }

            // Si quieres marcar explícitamente que no hubo resultados, descomenta:
            // if (docs.isEmpty()) {
            //   w.write("SIN RESULTADOS");
            //   w.newLine();
            // }
        }
    }
}