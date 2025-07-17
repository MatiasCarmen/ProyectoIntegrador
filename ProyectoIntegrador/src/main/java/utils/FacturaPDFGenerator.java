package utils;

import entidades.Cliente;
import entidades.DetalleAdicional;
import entidades.DetalleDescuento;
import entidades.DetallePlan;
import entidades.Producto;
import controladores.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FacturaPDFGenerator {

    public static void generarFacturaPDF(String idCuenta, Cliente cliente, String rutaSalida) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream content = new PDPageContentStream(document, page);
        PDType1Font fontBold = PDType1Font.HELVETICA_BOLD;
        PDType1Font fontNormal = PDType1Font.HELVETICA;

        // Logo Claro
        try {
            PDImageXObject logo = PDImageXObject.createFromFile("/imagenes/logo_claro.png", document);
            content.drawImage(logo, 50, 740, 100, 40);
        } catch (Exception e) {
            System.out.println("No se encontró el logo, omitiendo...");
        }

        // Título
        content.beginText();
        content.setFont(fontBold, 16);
        content.newLineAtOffset(200, 750);
        content.showText("Factura de Servicio - Claro Chile");
        content.endText();

        // Fecha y cliente
        content.beginText();
        content.setFont(fontNormal, 12);
        content.newLineAtOffset(50, 710);
        content.showText("Fecha de emisión: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        content.endText();

        content.beginText();
        content.setFont(fontNormal, 12);
        content.newLineAtOffset(50, 690);
        content.showText("Cliente: " + cliente.getNombres() + " " + cliente.getApellidoP());
        content.endText();

        content.beginText();
        content.setFont(fontNormal, 12);
        content.newLineAtOffset(50, 675);
        content.showText("RUT: " + cliente.getRut());
        content.endText();

        int y = 640;
        BigDecimal total = BigDecimal.ZERO;

        ControladorPlanes planCtrl = new ControladorPlanes();
        ControladorDetalle_planes detPlanCtrl = new ControladorDetalle_planes();
        ControladorAdicionales adiCtrl = new ControladorAdicionales();
        ControladorDetalle_adicionales detAdiCtrl = new ControladorDetalle_adicionales();
        ControladorDescuentos descCtrl = new ControladorDescuentos();
        ControladorDetalle_descuentos detDescCtrl = new ControladorDetalle_descuentos();
        ControladorProductos prodCtrl = new ControladorProductos();

        String idServicio = CuentasClienteControlador.obtenerCuentaServicioAsociada(idCuenta);

        // Cabecera tabla
        y = dibujarCabeceraTabla(content, y, "DETALLE", "TIPO", "MONTO");

        // PLANES
        try {
            String idPlan = planCtrl.obtenerPlanPorIdCuenta(idServicio).getIdPlan();
            List<DetallePlan> detalles = detPlanCtrl.obtenerDetallesPlan(idPlan);
            for (DetallePlan dp : detalles) {
                Producto p = prodCtrl.obtenerProducto(dp.getIdProducto());
                if (p != null) {
                    y = dibujarFilaTabla(content, y, p.getDescripcion(), "Plan", dp.getCosto() + "$", fontNormal);
                    total = total.add(dp.getCosto());
                }
            }
        } catch (Exception e) {
            // no plan
        }

        // ADICIONALES
        try {
            String idAdi = adiCtrl.obtenerAdicionalPorIdCuenta(idServicio).getIdAdicionales();
            List<DetalleAdicional> adicionales = detAdiCtrl.obtenerDetallesAdicional(idAdi);
            for (DetalleAdicional da : adicionales) {
                Producto p = prodCtrl.obtenerProducto(da.getIdProducto());
                if (p != null) {
                    y = dibujarFilaTabla(content, y, p.getDescripcion(), "Adicional", da.getCosto() + "$", fontNormal);
                    total = total.add(da.getCosto());
                }
            }
        } catch (Exception e) {
            // no adicionales
        }

        // DESCUENTOS
        try {
            String idDescuento = descCtrl.obtenerDescuentoPorIdCuenta(idServicio).getIdDescuentos();
            List<DetalleDescuento> descuentos = detDescCtrl.obtenerDetallesDescuento(idDescuento);
            for (DetalleDescuento dd : descuentos) {
                Producto p = prodCtrl.obtenerProducto(dd.getIdProducto());
                if (p != null) {
                    y = dibujarFilaTabla(content, y, p.getDescripcion(), "Descuento", dd.getCosto() + "$", fontNormal);
                    total = total.subtract(dd.getCosto());
                }
            }
        } catch (Exception e) {
            // no descuentos
        }

        // TOTAL
        y -= 15;
        content.beginText();
        content.setFont(fontBold, 14);
        content.newLineAtOffset(50, y);
        content.showText("TOTAL A PAGAR: $" + total);
        content.endText();

        content.close();
        document.save(rutaSalida);
        document.close();
    }

    private static int dibujarCabeceraTabla(PDPageContentStream content, int y, String col1, String col2, String col3)
            throws IOException {
        content.setNonStrokingColor(200, 0, 0); // Rojo
        content.addRect(45, y - 5, 500, 20);
        content.fill();
        content.setNonStrokingColor(0, 0, 0); // Negro

        content.beginText();
        content.setFont(PDType1Font.HELVETICA_BOLD, 12);
        content.newLineAtOffset(50, y);
        content.showText(col1);
        content.newLineAtOffset(250, 0);
        content.showText(col2);
        content.newLineAtOffset(100, 0);
        content.showText(col3);
        content.endText();
        return y - 25;
    }

    private static int dibujarFilaTabla(PDPageContentStream content, int y, String detalle, String tipo, String monto,
            PDType1Font font) throws IOException {
        content.setNonStrokingColor(255, 255, 255); // Blanco
        content.addRect(45, y - 5, 500, 20);
        content.fill();
        content.setNonStrokingColor(0, 0, 0); // Negro

        content.beginText();
        content.setFont(font, 11);
        content.newLineAtOffset(50, y);
        content.showText(detalle);
        content.newLineAtOffset(250, 0);
        content.showText(tipo);
        content.newLineAtOffset(100, 0);
        content.showText(monto);
        content.endText();
        return y - 20;
    }
}