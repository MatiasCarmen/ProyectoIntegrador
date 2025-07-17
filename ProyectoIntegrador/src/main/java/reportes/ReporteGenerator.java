package reportes;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReporteGenerator {
    protected static final String DIRECTORIO_REPORTES = System.getProperty("user.home") + "\\Documents\\Reportes\\";

    protected static Workbook crearLibro() {
        return new XSSFWorkbook();
    }

    protected static CellStyle crearEstiloEncabezado(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        return headerStyle;
    }

    protected static String obtenerNombreArchivo(String prefijo) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        return DIRECTORIO_REPORTES + prefijo + "_" + now.format(formatter) + ".xlsx";
    }

    protected static void guardarLibro(Workbook workbook, String nombreArchivo) throws IOException {
        // Crear el directorio si no existe
        java.io.File dir = new java.io.File(DIRECTORIO_REPORTES);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Guardar el archivo
        try (FileOutputStream fileOut = new FileOutputStream(nombreArchivo)) {
            workbook.write(fileOut);
        }
    }

    public static String exportarListaAExcel(String nombreArchivo, String[] columnas, java.util.List<Object[]> datos) {
        try (Workbook workbook = crearLibro()) {
            Sheet sheet = workbook.createSheet(nombreArchivo);
            CellStyle headerStyle = crearEstiloEncabezado(workbook);
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(i);
            }
            int rowNum = 1;
            for (Object[] fila : datos) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 0; i < fila.length; i++) {
                    if (fila[i] != null) {
                        row.createCell(i).setCellValue(fila[i].toString());
                    } else {
                        row.createCell(i).setCellValue("");
                    }
                }
            }
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }
            String archivo = obtenerNombreArchivo(nombreArchivo);
            guardarLibro(workbook, archivo);
            return archivo;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
