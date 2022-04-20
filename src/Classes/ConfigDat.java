/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.awt.Image;
import javax.swing.ImageIcon;
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
public class ConfigDat {

    String archivo = "config\\config.dat";

    public String getArchivo() {
        return archivo;
    }

    public ImageIcon getMKTIcon(int Width, int Height) {
        File f = new File("iconos\\marketingPNG.png");
        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(Width, Height, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(newimg);
    }

    public String getFiratUse() {
        String resp = null;
        try {
            String[] condig_dat = getConfigDatContent();
            String[] limpio = condig_dat[0].split(",");
            resp = limpio[0];
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public boolean first_use() {
        boolean resp = false;
        try {
            if (getFiratUse().equals("desactivado")) {
                resp = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public void setFiratUse(String Estado) {
        try {
            String[] Config = getConfigDatContent();
            if (Estado.equals("activado")) {
                Config[0] = "activado,";
            } else {
                Config[0] = "desactivado,";
            }
            FileWriter save = new FileWriter(archivo);
            BufferedWriter bw = new BufferedWriter(save);
            for (int i = 0; i < Config.length; i++) {
                bw.write(new Encryptor().EncryptData(Config[i]));
                bw.newLine();
            }
            bw.close();
            save.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String[] getConfigDatContent() {
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

    public void showConfigDatContent() {
        try {
            String[] condig_dat = getConfigDatContent();
            for (int i = 0; i < condig_dat.length; i++) {
                System.out.println(condig_dat[i]);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

//    public void setSerialNumber(String client, String email, String key) {
//        try {
//            String[] condig_dat = getConfigDatContent();
//            condig_dat[2] = client + "," + email + "," + key + ",";
//            FileWriter save = new FileWriter(archivo);
//            BufferedWriter bw = new BufferedWriter(save);
//            for (int i = 0; i < condig_dat.length; i++) {
//                bw.write(new Encryptor().EncryptData(condig_dat[i]));
//                bw.newLine();
//            }
//            bw.close();
//            save.close();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    public String[] getSerialNumber() {
//        String[] resp = null;
//        try {
//            String[] condig_dat = getConfigDatContent();
//            if (condig_dat != null) {
//                resp = condig_dat[2].split(",");
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
//        }
//        return resp;
//    }
    //ICONOS Y LOGOS
    public ImageIcon getLogo(int Width, int Height) {
        File f = new File("iconos\\Logo.png");
        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(Width, Height, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(newimg);
    }

    public ImageIcon getDocumentLogo(int Width, int Height) {
        File f = new File("iconos\\docPNG.png");
        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(Width, Height, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(newimg);
    }

    public ImageIcon getCarIcon(int Width, int Height) {
        File f = new File("iconos\\carPNG.png");
        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(Width, Height, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(newimg);
    }

    public ImageIcon getClientIcon(int Width, int Height) {
        File f = new File("iconos\\clientPNG.png");
        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(Width, Height, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(newimg);
    }

    public ImageIcon getLevelGrantedIcon(int Width, int Height) {
        File f = new File("iconos\\LevelGranted.png");
        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(Width, Height, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(newimg);
    }

    public ImageIcon getLevelLockedIcon(int Width, int Height) {
        File f = new File("iconos\\LevelLocked.png");
        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(Width, Height, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(newimg);
    }

    public ImageIcon getLevelAdminIcon(int Width, int Height) {
        File f = new File("iconos\\LevelAdmin.png");
        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(Width, Height, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(newimg);
    }

    public ImageIcon getUserIcon(int Width, int Height) {
        File f = new File("iconos\\user.png");
        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(Width, Height, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(newimg);
    }

    public ImageIcon getExample(int Width, int Height) {
        File f = new File("iconos\\Example.png");
        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(Width, Height, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(newimg);
    }

    public ImageIcon getImageIcon() {
        ImageIcon resp = null;
        try {
            File f = new File("iconos\\appPNG.png");
            resp = new ImageIcon(f.getAbsolutePath());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

}
