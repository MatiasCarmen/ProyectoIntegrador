package reportes;

import dao.ClienteDAO;
import entidades.Cliente;
import com.google.common.collect.ImmutableList;
import org.apache.poi.ss.usermodel.*;
import java.io.IOException;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ReporteClientes extends ReporteGenerator {
    private static final Logger LOGGER = Logger.getLogger(ReporteClientes.class.getName());
    private static final String[] COLUMNAS = {
        "RUT", "Nombres", "Apellido Paterno", "Apellido Materno",
        "Correo", "Teléfono", "Edad", "Dirección", "Comuna"
    };

    public static String generarExcel() {
        try (Workbook workbook = crearLibro()) {
            Sheet sheet = workbook.createSheet("Clientes");

            // Crear estilo para encabezados
            CellStyle headerStyle = crearEstiloEncabezado(workbook);

            // Crear encabezados
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < COLUMNAS.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(COLUMNAS[i]);
                cell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(i);
            }

            // Obtener datos de clientes
            ClienteDAO clienteDAO = new ClienteDAO();
            ImmutableList<Cliente> clientes = clienteDAO.listarClientes();

            // Llenar datos
            int rowNum = 1;
            for (Cliente cliente : clientes) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(cliente.getRut());
                row.createCell(1).setCellValue(cliente.getNombres());
                row.createCell(2).setCellValue(cliente.getApellidoP());
                row.createCell(3).setCellValue(cliente.getApellidoM());
                row.createCell(4).setCellValue(cliente.getCorreo());
                row.createCell(5).setCellValue(cliente.getTelefono());
                row.createCell(6).setCellValue(cliente.getEdad());
                row.createCell(7).setCellValue(cliente.getDireccion());
                row.createCell(8).setCellValue(cliente.getIdComuna());
            }

            // Ajustar ancho de columnas
            for (int i = 0; i < COLUMNAS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Guardar archivo
            String nombreArchivo = obtenerNombreArchivo("Clientes");
            guardarLibro(workbook, nombreArchivo);

            return nombreArchivo;
        } catch (IOException e) {
            LOGGER.severe("Error al generar el reporte Excel: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                "Error al generar el reporte: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
