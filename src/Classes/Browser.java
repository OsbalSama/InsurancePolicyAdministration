/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author OSBAL
 */
public class Browser {

    String archivo = "database\\db_browser.dat";

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
    
    public String[] getDBBrowser() {
        List<String> database = new ArrayList<>();
        try {
            String cadena;
            FileReader f = new FileReader(getArchivo());
            BufferedReader b = new BufferedReader(f);
            while ((cadena = b.readLine()) != null) {
                String reg = new Encryptor().DecryptData(cadena);
                if (!reg.equals("")) {
                    database.add(reg);
                }
            }
            b.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        if (database.isEmpty()) {
            return null;
        } else {
            String[] resp = new String[database.size()];
            for (int i = 0; i < resp.length; i++) {
                resp[i] = database.get(i);
            }
            return resp;
        }
    }

    public void updateBrowserDB() {
        String[] database = new Polizas().getDBPolizas();
        try {
            if (database != null) {
                FileWriter w = new FileWriter(this.getArchivo());
                BufferedWriter bw = new BufferedWriter(w);
                for (int i = 0; i < database.length; i++) {
                    String[] reg = database[i].split(",");
                    if (reg[0] != null || construirRegistro(reg[0]) != null) {
                        bw.write(new Encryptor().EncryptData(construirRegistro(reg[0])));
                        bw.newLine();
                    }
                }
                bw.newLine();
                bw.close();
            }
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String construirRegistro(String idp) {
        String resp = "";
        String[] poliza = new Polizas().getPolizabyID(idp);
        String[] cliente = new Clientes().getClientbyID(poliza[3]);
        String[] vehiculo = new Vehiculos().getVehiclebyID(poliza[4]);
        if (poliza != null && cliente != null && vehiculo != null) {
            resp += poliza[0] + ",";//idreg
            resp += poliza[1] + ",";//num Poliza
            resp += cliente[1] + " "+cliente[2]+ ",";//nom Cliente
            resp += vehiculo[1] + ",";//Desc Vehiculo
            resp += vehiculo[5] + ",";//Placa
            resp += vehiculo[7] + ",";//Num Economico
            resp += poliza[2] + ",";//Fecha vencimiento   
        } else {
            resp = null;
        }
        return resp;
    }
    
    public String[] getDBBgsrRegistry(String pista) {
        List<String> busqueda = new ArrayList<>();
        try {
            String[] database = getDBBrowser();
            if (database != null) {
                for (int i = 0; i < database.length; i++) {
                    String[] temp = database[i].split(",");
                    String idreg = temp[0].toLowerCase();
                    String poliza = temp[1].toLowerCase();
                    String nClient = temp[2].toLowerCase();
                    String descripcion = temp[3].toLowerCase();
                    String placa = temp[4].toLowerCase();
                    String numeco = temp[5].toLowerCase();
                    String fvenc = temp[6].toLowerCase();
                    
                    String indicio = pista.toLowerCase();
                    int intIndex0 = idreg.indexOf(indicio);
                    int intIndex1 = poliza.indexOf(indicio);
                    int intIndex2 = nClient.indexOf(indicio);
                    int intIndex3 = descripcion.indexOf(indicio);
                    int intIndex4 = placa.indexOf(indicio);
                    int intIndex5 = numeco.indexOf(indicio);
                    int intIndex6 = fvenc.indexOf(indicio);
                    if (intIndex0 != - 1 || intIndex1 != - 1|| intIndex2 != - 1|| intIndex3 != - 1|| intIndex4 != - 1|| intIndex5 != - 1|| intIndex6 != - 1) {
                        busqueda.add(database[i]);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        if (busqueda.isEmpty()) {
            return null;
        } else {
            String[] resp = new String[busqueda.size()];
            for (int i = 0; i < resp.length; i++) {
                resp[i] = busqueda.get(i);
            }
            return resp;
        }
    }
}
