package testdata;

import com.mapfre.utils.excel.ExcelXlsEditor;

import java.nio.file.*;

public final class TemplateFactory {
    private TemplateFactory() {}

    public static Path buildPlanillaWithNineDigits(String nineDigits) {
        try {
            Path template = Paths.get("src/test/resources/testdata/uploads/templates/Formato_trama_sctr.xls");
            Path outDir = Paths.get("target/test-output/uploads");
            Files.createDirectories(outDir);

            Path outFile = outDir.resolve("template" + System.nanoTime() + ".xls");
            Files.copy(template, outFile, StandardCopyOption.REPLACE_EXISTING);

            ExcelXlsEditor.updateCell(outFile, "Trabajadores", 1, 1, nineDigits); // example cell
            return outFile;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Path buildPlanillaWithNineDigits(String nineDigits, String sheetName, int rowIndex, int colIndex) {
        try {
            Path template = Paths.get("src/test/resources/testdata/uploads/templates/"+sheetName+".xls");
            Path outDir = Paths.get("target/test-output/uploads");
            Files.createDirectories(outDir);

            Path outFile = outDir.resolve("template_" +sheetName+"_"+rowIndex+"_"+colIndex+"_"+ System.nanoTime() + ".xls");
            Files.copy(template, outFile, StandardCopyOption.REPLACE_EXISTING);

            ExcelXlsEditor.updateCell(outFile, "Trabajadores", 1, 1, nineDigits); // example cell
            return outFile;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}