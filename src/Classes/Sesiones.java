/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.Encryptor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author OSBAL
 */
public class Sesiones {

    String archivo = "database\\db_sesiones.dat";
    DateFormat FormatoHora = new SimpleDateFormat("HH:mm");
    DateFormat FormatoFecha = new SimpleDateFormat("dd-MM-yyyy");

    public String getArchivo() {
        return archivo;
    }
    
    public void ClearDB() {
        try {
            new File(getArchivo()).delete();
            new File(getArchivo()).createNewFile();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String[] getDBSesiones() {
        String resp = "";
        try {
            String cadena;
            FileReader f = new FileReader(getArchivo());
            BufferedReader b = new BufferedReader(f);
            while ((cadena = b.readLine()) != null) {
                resp += new Encryptor().DecryptData(cadena) + "<<";
            }
            b.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        if (resp.equals("")) {
            return null;
        } else {
            return resp.split("<<");
        }
    }

    public String genID() {
        try {
            String id = "ssn_";
            for (int i = 0; i < 7; i++) {
                id += (int) (Math.random() * 10);
            }
            if (existID(id)) {
                genID();
            } else {
                return id;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public boolean existID(String id) {
        boolean resp = false;
        try {
            String[] datos = getDBSesiones();
            if (datos != null) {
                for (int i = 0; i < datos.length; i++) {
                    String[] sesion = datos[i].split(",");
                    if (sesion[0].equals(id)) {
                        resp = true;
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public void saveRegSession(String id, String usuario) {
        if (existe_reg(FormatoFecha.format(new Date()), usuario)) {
            UpdateLogg(usuario, FormatoFecha.format(new Date()));
        } else {
            saveSession(id, usuario);
        }
    }

    public void saveSession(String id, String usuario) {
        try {
            int hora_inicio = new Date().getHours();
            String turno = "";
            if (hora_inicio >= 0 && hora_inicio <= 15) {
                turno = "Matutino";
            } else if (hora_inicio > 15 && hora_inicio <= 24) {
                turno = "Vespertino";
            }
            String[] database = getDBSesiones();
            String nuevo_reg = id + "," + usuario + "," + FormatoFecha.format(new Date()) + "," + FormatoHora.format(new Date()) + "," + FormatoHora.format(new Date()) + "," + "1";
            nuevo_reg = new Encryptor().EncryptData(nuevo_reg);
            if (database == null) {
                FileWriter w = new FileWriter(getArchivo());
                BufferedWriter bw = new BufferedWriter(w);
                bw.write(nuevo_reg);
                bw.newLine();
                bw.close();
            } else {
                FileWriter w = new FileWriter(getArchivo());
                BufferedWriter bw = new BufferedWriter(w);
                for (int i = 0; i < database.length; i++) {
                    bw.write(new Encryptor().EncryptData(database[i]));
                    bw.newLine();
                }
                bw.write(nuevo_reg);
                bw.newLine();
                bw.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean existe_reg(String fecha, String user) {
        boolean resp = false;
        try {
            String[] database = getDBSesiones();
            if (database != null) {
                for (int i = 0; i < database.length; i++) {
                    String[] registro = database[i].split(",");
                    if (registro[1].equals(user) && registro[2].equals(fecha)) {
                        resp = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public void UpdateLogg(String user, String fecha) {
        try {
            String[] database = getDBSesiones();
            for (int i = 0; i < database.length; i++) {
                String[] registro = database[i].split(",");
                if (registro[1].equals(user) && registro[2].equals(fecha)) {
//                    registro[2] = FormatoFecha.format(new Date());//fecha
                    registro[4] = FormatoHora.format(new Date());//ultima hora
                    registro[5] = (Integer.parseInt(registro[5]) + 1) + "";//sesiones_iniciadas
                    String text = "";
                    for (int j = 0; j < registro.length; j++) {
                        text += registro[j] + ",";
                    }
                    database[i] = text;
                    break;
                }
            }
            FileWriter w = new FileWriter(getArchivo());
            BufferedWriter bw = new BufferedWriter(w);
            for (int i = 0; i < database.length; i++) {
                bw.write(new Encryptor().EncryptData(database[i]));
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
