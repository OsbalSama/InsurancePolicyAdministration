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
public class Clientes {

    String archivo = "database\\db_clientes.dat";

    public String getArchivo() {
        return archivo;
    }

    
    public String[] getDBClients() {
        List<String> database = new ArrayList<>();
        try {
            String cadena;
            FileReader f = new FileReader(getArchivo());
            BufferedReader b = new BufferedReader(f);
            while ((cadena = b.readLine()) != null) {
                database.add(new Encryptor().DecryptData(cadena));
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

    public void ClearDB() {
        try {
            new File(getArchivo()).delete();
            new File(getArchivo()).createNewFile();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String getClientToString(String IDRoute) {
        String resp = "--- DATOS DE CLIENTE ---";
        String[] ruta = getClientbyID(IDRoute);
        if (ruta != null) {
            for (int i = 1; i < (ruta.length - 1); i++) {
                resp += "\n" + ruta[i] + ",";
            }
        } else {
            resp = "--- SIN CLIENTE SELECCIONADO ---";
        }
        return resp;
    }

    public String genID() {
        try {
            String id = "CLT_";
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
            String[] datos = getDBClients();
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

    public boolean insClient(String nuevo_reg) {
        nuevo_reg = new Encryptor().EncryptData(nuevo_reg);
        try {
            String[] database = getDBClients();
            if (database == null) {
                FileWriter w = new FileWriter(this.getArchivo());
                BufferedWriter bw = new BufferedWriter(w);
                bw.write(nuevo_reg);
                bw.newLine();
                bw.close();
            } else {
                FileWriter w = new FileWriter(this.getArchivo());
                BufferedWriter bw = new BufferedWriter(w);
                for (int i = 0; i < database.length; i++) {
                    bw.write(new Encryptor().EncryptData(database[i]));
                    bw.newLine();
                }
                bw.write(nuevo_reg);
                bw.newLine();
                bw.close();
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public String[] getClientbyID(String id) {
        String resp = "";
        try {
            String[] database = getDBClients();
            if (database != null) {
                for (int i = 0; i < database.length; i++) {
                    String[] temp = database[i].split(",");
                    if (id.equals(temp[0])) {
                        resp = database[i];
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        if (resp.equals("")) {
            return null;
        } else {
            return resp.split(",");
        }
    }

    public boolean updateClient(String nuevo_reg) {
        try {
            String[] database = getDBClients();
            FileWriter w = new FileWriter(getArchivo());
            BufferedWriter bw = new BufferedWriter(w);
            for (int i = 0; i < database.length; i++) {
                String[] reg_ind = database[i].split(",");
                String[] nreg = nuevo_reg.split(",");
                if (reg_ind[0].equals(nreg[0])) {
                    bw.write(new Encryptor().EncryptData(nuevo_reg));
                    bw.newLine();
                } else {
                    bw.write(new Encryptor().EncryptData(database[i]));
                    bw.newLine();
                }
            }
            bw.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean DelClient(String ClientID) {
        try {
            String[] allUsers = getDBClients();
            BufferedWriter bw = new BufferedWriter(new FileWriter(getArchivo()));
            for (int i = 0; i < allUsers.length; i++) {
                String[] registro = allUsers[i].split(",");
                if (!ClientID.equals(registro[0])) {
                    allUsers[i] = new Encryptor().EncryptData(allUsers[i]);
                    bw.write(allUsers[i]);
                    bw.newLine();
                }
            }
            bw.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public List loadClientes() {
        List<String> resp = new ArrayList<>();
        String[] database = getDBClients();
        if (database != null) {
            for (int i = 0; i < database.length; i++) {
                String[] cuenta = database[i].split(",");
                resp.add(cuenta[0]);
            }
        }
        return resp;
    }

}
