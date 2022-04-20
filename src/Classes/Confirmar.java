/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import javax.swing.JOptionPane;

/**
 *
 * @author terra
 */
public class Confirmar {

    public boolean ConfirmAcceso(String usuario, String contraseña) {
        boolean resp = false;
        try {
            String[] usuarios_db = new Usuarios().getUsersbyName(usuario);
            if (usuarios_db != null) {
                if (contraseña.equals(usuarios_db[2])) {
                    resp = true;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }
}
