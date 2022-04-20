/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.Encryptor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JOptionPane;

/**
 *
 * @author OSBAL
 */
public class Usuarios {

    String archivo = "database\\db_usuarios.dat";

    public String[] getDBUsers() {
        String resp = "";
        try {
            String cadena;
            FileReader f = new FileReader(this.archivo);
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

    public void addRootUser() {
        try {
            String nreg = "123456,root,root,Administrador,";
            InsertRootUser(nreg);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void InsertRootUser(String nuevo_reg) {
        nuevo_reg = new Encryptor().EncryptData(nuevo_reg);
        try {
            FileWriter w = new FileWriter(this.archivo);
            BufferedWriter bw = new BufferedWriter(w);
            bw.write(nuevo_reg);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String[] getUsersbyID(String id) {
        String resp = "";
        try {
            String[] databasde = getDBUsers();
            for (int i = 0; i < databasde.length; i++) {
                String[] temp = databasde[i].split(",");
                if (temp[0].equals(id)) {
                    resp = databasde[i] + "<<";
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        if (resp.equals("")) {
            return null;
        } else {
            return resp.split("<<");
        }
    }

    public String[] getUsersbyName(String User) {
        String resp = "";
        String[] database = getDBUsers();
        try {
            for (int i = 0; i < database.length; i++) {
                String[] temp = database[i].split(",");
                if (temp[1].equals(User)) {
                    resp = database[i];
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

    public String genID() {
        String id = "";
        try {
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
            String[] allUsers = getUsersbyID(id);
            if (allUsers != null) {
                resp = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public boolean existUser(String User) {
        boolean resp = false;
        try {
            String[] allUsers = getUsersbyName(User);
            if (allUsers != null) {
                resp = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public boolean InsertUser(String nuevo_reg) {
        nuevo_reg = new Encryptor().EncryptData(nuevo_reg);
        try {
            String[] database = getDBUsers();
            if (database == null) {
                FileWriter w = new FileWriter(this.archivo);
                BufferedWriter bw = new BufferedWriter(w);
                bw.write(nuevo_reg);
                bw.newLine();
                bw.close();
            } else {
                FileWriter w = new FileWriter(this.archivo);
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

    public boolean UpdateUser(String nuevo_reg) {
        try {
            String[] database = getDBUsers();
            String[] nreg = nuevo_reg.split(",");
            FileWriter w = new FileWriter(this.archivo);
            BufferedWriter bw = new BufferedWriter(w);
            for (int i = 0; i < database.length; i++) {
                String[] reg_ind = database[i].split(",");
                if (reg_ind[0].equals(nreg[0])) {
                    bw.write(new Encryptor().EncryptData(nuevo_reg));
                    bw.newLine();
                } else {
                    bw.write(new Encryptor().EncryptData(database[i]));
                    bw.newLine();
                }
            }
            bw.close();
            bw.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public boolean DelUser(String userID) {
        try {
            String[] allUsers = getDBUsers();
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
            for (int i = 0; i < allUsers.length; i++) {
                String[] usuario_db = allUsers[i].split(",");
                if (!userID.equals(usuario_db[0])) {
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

    public boolean confirmar_acceso(String usuario, String contraseña) {
        boolean resp = false;
        try {
            String[] usuario_encontrado = getUsersbyName(usuario);
            if (usuario_encontrado != null) {
                if (contraseña.equals(usuario_encontrado[2])) {
                    resp = true;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }
}
