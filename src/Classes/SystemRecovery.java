/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Viernes
 */
public class SystemRecovery {

    String archivo = "database\\";

    public String getArchivo() {
        return archivo;
    }

    public void SystemBackup() {
        try {
            JFileChooser savefile = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("Archivo ZIP", "zip");
            savefile.addChoosableFileFilter(filter);
            savefile.setAcceptAllFileFilterUsed(false);
            savefile.setSelectedFile(new File("Respaldo IPA " + new SimpleDateFormat("dd-MM-YYYY").format(new Date()) + ".zip"));
            int sf = savefile.showSaveDialog(null);
            if (sf == JFileChooser.APPROVE_OPTION) {
                if (comprimirCarpeta(savefile.getSelectedFile().toString(), savefile.getSelectedFile().getName())) {
                    JOptionPane.showMessageDialog(null, "<html><h1>RESPALDO PROCESADO CORRECTAMENTE</h1><font SIZE=5><p> Clic para cerrar...</font></html>", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            System.out.println("Error algo: " + e);
        }
    }

    public void agregarCarpeta(String ruta, String carpeta, ZipOutputStream zip) throws Exception {
        File directorio = new File(carpeta);
        for (String nombreArchivo : directorio.list()) {
            if (ruta.equals("")) {
                agregarArchivo(directorio.getName(), carpeta + "/" + nombreArchivo, zip);
            } else {
                agregarArchivo(ruta + "/" + directorio.getName(), carpeta + "/" + nombreArchivo, zip);
            }
        }
    }

    public void agregarArchivo(String ruta, String directorio, ZipOutputStream zip) throws Exception {
        File archivo = new File(directorio);
        if (archivo.isDirectory()) {
            agregarCarpeta(ruta, directorio, zip);
        } else {
            byte[] buffer = new byte[4096];
            int leido;
            FileInputStream entrada = new FileInputStream(archivo);
            zip.putNextEntry(new ZipEntry(ruta + "/" + archivo.getName()));
            while ((leido = entrada.read(buffer)) > 0) {
                zip.write(buffer, 0, leido);
            }
        }
    }

    public void comprimir(String archivo, String archivoZIP) throws Exception {
        ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(archivoZIP));
        agregarCarpeta("", archivo, zip);
        zip.flush();
        zip.close();
    }

    public boolean comprimirCarpeta(String ruta, String nombreArchivo) {
        boolean resp = false;
        try {
            comprimir(getArchivo(), ruta);
            resp = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

}
