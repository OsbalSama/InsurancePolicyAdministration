/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.HeadlessException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

/**
 *
 * @author OSBAL
 */
public class DPFDocs {

    Font fuente = FontFactory.getFont(FontFactory.HELVETICA);

    public String genID() {
        try {
            String id = "PLZ_";
            for (int i = 0; i < 7; i++) {
                id += (int) (Math.random() * 10);
            }
            return id;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    //ESTA CLASE SE INVOCA PARA IMPRIMIR UNA NOTA DE VENTA
    public void printDocument(String[] DatGen, String[] registros, String printer) {
        String ruta = "docs\\Registro Polizas  " + DatGen[0] + " " + new SimpleDateFormat("dd-MM-YYYY").format(new Date()) + ".pdf";
        try {
            CreatePDFLetterSale(ruta, PageSize.LETTER, TableLetterSale(DatGen, registros), TableLetterFooterSale(DatGen));
            PrintDocument(ruta, printer);
        } catch (Exception e) {
            int resp = JOptionPane.showConfirmDialog(null, "<html><h1>¡OOPS NO SE PUDO IMPRIMIR TICKET!</h1><font SIZE=5><p>¿Desea imprimirlo como PDF?</font></html>", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                PrintDocument(ruta, "Imprimir como PDF");
            }
        }
    }

    //CREAR DOCUMENTO PDF VENTA TAMAÑO CARTA EN Register_Cash_v1.0.1\docs\Tickets_Compras
    public void CreatePDFLetterSale(String ruta, Rectangle tamaño, PdfPTable tabla, PdfPTable tableFooter) {
        try {
            int margenLeft = 36;
            int margenRigth = 36;
            int margenTop = 18;
            int margenBottom = 18;
            Document doc = new Document(tamaño, margenLeft, margenRigth, margenTop, margenBottom);
            PdfWriter pdfw = PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();
            doc.add(tabla);
            tableFooter.setTotalWidth(doc.right(doc.rightMargin()));
            tableFooter.writeSelectedRows(0, -1, margenLeft, tableFooter.getTotalHeight() + doc.bottom(doc.bottomMargin()), pdfw.getDirectContent());
            doc.add(new Paragraph(" "));
            doc.close();
        } catch (Exception e) {
            int resp = JOptionPane.showConfirmDialog(null, "<html><h1>¡OOPS NO SE PUDO IMPRIMIR TICKET!</h1><font SIZE=5><p>¿Desea imprimirlo como PDF?</font></html>", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                PrintDocument(ruta, "Imprimir como PDF");
            }
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    //PARTES TABLA TAMAÑO CARTA VENTA
    public PdfPTable TableLetterSale(String[] DatGen, String[] regs) {
        PdfPTable table = new PdfPTable(7);
        int tamaño = 9;
        table.setWidthPercentage(100);
        PdfPCell celda = new PdfPCell();
        String[] cont = new DatEmpresa().getDatEmpresa();
        try {
            //ENCABEZADO
            File f = null;
            if (new File("iconos\\LOGO.png").exists()) {
                f = new File("iconos\\LOGO.png");
            } else {
                f = new File("iconos\\LOGO.png");
            }
            com.itextpdf.text.Image imagen = com.itextpdf.text.Image.getInstance(f.getAbsolutePath());
            imagen.scaleToFit(150, 75);
            imagen.setAlignment(Element.ALIGN_CENTER);
            celda.setColspan(4);
            celda.addElement(imagen);
            table.addCell(celda);// LOGO  

            celda = new PdfPCell();
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setColspan(3);
            Paragraph p = new Paragraph(cont[1] + "\n", FontFactory.getFont(fuente.toString(), tamaño + 15));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            String text = "RFC:" + cont[2] + "\n"
                    + "ENCARGADO:" + cont[3] + "\n"
                    + "DIRECCION:" + cont[4] + "\n"
                    + "TELEFONO:" + cont[5] + "\n\n";
            p = new Paragraph(text, FontFactory.getFont(fuente.toString(), 6));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            table.addCell(celda);
            //FIN ENCABEZADO

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(fuente.toString(), tamaño)));
            celda.setColspan(7);
            celda.setBackgroundColor(BaseColor.GRAY);
            table.addCell(celda);//SEPARADOR

            p = new Paragraph("POLIZAS REGISTRADAS", FontFactory.getFont(fuente.toString(), tamaño + 3));
            p.setAlignment(Element.ALIGN_CENTER);            
            celda = new PdfPCell();            
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda.addElement(p);
            celda.setColspan(7);
            table.addCell(celda);

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(fuente.toString(), tamaño)));
            celda.setColspan(7);
            celda.setBackgroundColor(BaseColor.GRAY);
            table.addCell(celda);//SEPARADOR

            table.addCell(new Phrase("ID Registro", FontFactory.getFont(fuente.toString(), tamaño)));
            table.addCell(new Phrase("Poliza", FontFactory.getFont(fuente.toString(), tamaño)));
            table.addCell(new Phrase("Nombre Cliente", FontFactory.getFont(fuente.toString(), tamaño)));
            table.addCell(new Phrase("Vehiculo", FontFactory.getFont(fuente.toString(), tamaño)));
            table.addCell(new Phrase("Num. Placa", FontFactory.getFont(fuente.toString(), tamaño)));
            table.addCell(new Phrase("Num. Economico", FontFactory.getFont(fuente.toString(), tamaño)));
            table.addCell(new Phrase("F. Vencimiento", FontFactory.getFont(fuente.toString(), tamaño)));
            for (int i = 0; i < regs.length; i++) {
                String[] datosInd = regs[i].split(",");
                for (int j = 0; j < datosInd.length; j++) {
                    table.addCell(new Phrase( datosInd[j], FontFactory.getFont(fuente.toString(), tamaño)));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return table;
    }

    public PdfPTable TableLetterFooterSale(String[] datGen) {
        PdfPTable table = new PdfPTable(4);
        int tamaño = 9;
        Paragraph p = new Paragraph();
        table.setWidthPercentage(100);
        try {
            PdfPCell celda = new PdfPCell();
            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(fuente.toString(), tamaño)));
            celda.setColspan(4);
            celda.setBackgroundColor(BaseColor.GRAY);
            table.addCell(celda);//SEPARADOR

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(fuente.toString(), tamaño)));
            celda.setColspan(2);
            table.addCell(celda);

            p = new Paragraph("Fecha: " + new SimpleDateFormat("dd-MM-YYYY").format(new Date()) + ", " + new SimpleDateFormat("HH:mm").format(new Date())
                    + "\nLe atendio: "+datGen[2], FontFactory.getFont(fuente.toString(), tamaño));
            celda = new PdfPCell(p);
            p.setAlignment(Element.ALIGN_CENTER);
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(celda);

            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(new Barcodes().ImgBC128(datGen[0]));
            celda.addElement(image);
            p = new Paragraph("Folio: " + datGen[0], FontFactory.getFont(fuente.toString(), tamaño + 3));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(fuente.toString(), tamaño)));
            celda.setColspan(4);
            celda.setBackgroundColor(BaseColor.GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph(datGen[3], FontFactory.getFont(fuente.toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda.addElement(p);
            celda.setColspan(4);
            table.addCell(celda);

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(fuente.toString(), tamaño)));
            celda.setColspan(4);
            celda.setBackgroundColor(BaseColor.GRAY);
            table.addCell(celda);//SEPARADOR

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return table;
    }

    //IMPRIMIR DOCUMENTO EN FISICO
    public void PrintTicket(String ruta, String printer) {
        try {
            File arch = new File(ruta);
            PDDocument document = PDDocument.load(arch);
            PrintService myPrintService = this.getPrintDevice(printer);
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPageable(new PDFPageable(document));
            printerJob.setPrintService(myPrintService);
            printerJob.print();
            JOptionPane.showMessageDialog(null, "<html><h1>ARCHIVO IMPRESO CORRECTAMENTE</h1><font SIZE=5><p> Clic para cerrar…</font></html>", "¡Terminado!", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            int resp = JOptionPane.showConfirmDialog(null, "<html><h1>¡OOPS NO SE PUDO IMPRIMIR TICKET!</h1><font SIZE=5><p>¿Desea imprimirlo como PDF?</font></html>", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                PrintDocument(ruta, "Imprimir como PDF");
            }
            JOptionPane.showMessageDialog(null, "Error PrintTicket: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    //ESCOGER TIPO DE IMPRESION
    public void PrintDocument(String ruta, String impresora) {
        switch (impresora) {
            case "Imprimir como PDF":
                DigitalPrint(ruta);
                break;
            default:
                PrintTicket(ruta, impresora);
                break;
        }
    }

    //ESCOGER RUTA DE COPIADO DE ARCHIVO IMPRESO COMO PDF
    public void DigitalPrint(String ruta) {
        Path origen = FileSystems.getDefault().getPath(ruta);
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de PDF", "PDF", "pdf");
        chooser.setFileFilter(filtro);
        chooser.setSelectedFile(new File(new File(ruta).getName()));
        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            try {
                Path destino = FileSystems.getDefault().getPath(chooser.getSelectedFile().getAbsolutePath() + ".pdf");
                if (new File(destino.toString()).exists()) {
                    if (JOptionPane.showConfirmDialog(null, "Este archivo ya existe... \ndesea reemplazarlo?", "¡Atencion!", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
                        JOptionPane.showMessageDialog(null, "<html><h1>ARCHIVO GUARDADO CORRECTAMENTE</h1><font SIZE=5><p> Clic para cerrar…</font></html>", "¡Terminado!", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
                    JOptionPane.showMessageDialog(null, "<html><h1>ARCHIVO GUARDADO CORRECTAMENTE</h1><font SIZE=5><p> Clic para cerrar…</font></html>", "¡Terminado!", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (HeadlessException | IOException e) {
                JOptionPane.showMessageDialog(null, "Error DigitalPrint: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //OBTENER SERVICIO DE IMPRESION BUSCANDOLO POR NOMBRE
    public PrintService getPrintDevice(String nombre) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService impresora : printServices) {
            if (impresora.getName().contentEquals(nombre)) {
                return impresora;
            }
        }
        return null;
    }
}
