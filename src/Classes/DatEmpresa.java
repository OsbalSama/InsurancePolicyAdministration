/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JOptionPane;

/**
 *
 * @author Bonum
 */
public class DatEmpresa {

    String archivo = "database\\db_empresa.dat";

    public String[] getDatEmpresa() {
        String resp = "";
        try {
            String cadena;
            FileReader f = new FileReader(this.archivo);
            BufferedReader b = new BufferedReader(f);
            while ((cadena = b.readLine()) != null) {
                
                resp += new Encryptor().DecryptData(cadena);
            }
            b.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error:" + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        if (resp.equals("")) {
            return null;
        } else {
            return resp.split(",");
        }
    }

    public void setDatEmpresa(String nuevo_reg) {
       String NR = new Encryptor().EncryptData(nuevo_reg);
        try {
            FileWriter w = new FileWriter(this.archivo);
            BufferedWriter bw = new BufferedWriter(w);
            bw.write(NR);
            bw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

}
