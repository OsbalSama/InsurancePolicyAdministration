/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Dialogs.Datos_Tienda_2;
import Dialogs.Datos_Tienda;
import Dialogs.InsUser;
import Frames.Login;
import javax.swing.JOptionPane;

/**
 *
 * @author OSBAL
 */
public class ConfigApp {

    boolean terminado = false;
    String nombreApp = "Insurance Policy Administration Free";

    public String getNombreApp() {
        return nombreApp;
    }

    public boolean isTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    public void InicioSistema() {
        boolean continuar = false;
        while (continuar == false) {
            int resp_0 = JOptionPane.showConfirmDialog(null, "<html><h1>Bienvenido a " + getNombreApp() + "!</h1><br>"
                    + "<center>Para comenzar a trabajar en este Admin de Polizas de seguros, es necesario establecer algunas <p>configuraciones importantes…</center></html>", "", JOptionPane.OK_CANCEL_OPTION);
            if (resp_0 == JOptionPane.CANCEL_OPTION || resp_0 == JOptionPane.CLOSED_OPTION) {
                int resp = JOptionPane.showConfirmDialog(null, "¿Seguro que desea cancelar?", "¡Atencion!", JOptionPane.YES_NO_OPTION);
                if (resp == 0) {
                    System.exit(resp);
                }
            } else {
                continuar = true;
            }
        }
        new Usuarios().addRootUser();
        continuar = false;
        while (continuar == false) {
            InsUser iu = new InsUser(null, true);
            iu.setDatFirstUse();
            iu.show();
            if (iu.isOk() == true) {
                continuar = true;
            } else if (iu.getContinuar() == 2) {
                int resp = JOptionPane.showConfirmDialog(null, "¿Seguro que desea cancelar?", "¡Atencion!", JOptionPane.YES_NO_OPTION);
                if (resp == 0) {
                    System.exit(resp);
                }
            }
        }
        continuar = false;
        while (continuar == false) {
            Datos_Tienda dt = new Datos_Tienda(null, true);
            dt.setDatFirstUse();
            dt.show();
            if (dt.getContinuar() == 1) {
                continuar = true;
            } else if (dt.getContinuar() == 2) {
                int resp = JOptionPane.showConfirmDialog(null, "¿Seguro que desea cancelar?", "¡Atencion!", JOptionPane.YES_NO_OPTION);
                if (resp == 0) {
                    System.exit(resp);
                }
            }
        }
        Datos_Tienda_2 mdt = new Datos_Tienda_2(null, true);
        mdt.setDatFirstUse();
        mdt.asignar_datos();
        mdt.show();
//  AQUI HAY QUE METER OTRO PROCESO SI HACE FALTA
//        continuar = false;
//        while (continuar == false) {
//            
//        }
        new Clientes().ClearDB();
        new Vehiculos().ClearDB();
        new Polizas().ClearDB();
        new Browser().ClearDB();
        new Sesiones().ClearDB();

        JOptionPane.showMessageDialog(null, "<html><h1>¡Bienvenido a " + getNombreApp() + "!</h1><font SIZE=5>"
                + "<p>Todo listo... ahora si, COMENCEMOS!</font></html>", "¡Bienvenido!", JOptionPane.WARNING_MESSAGE);
        new ConfigDat().setFiratUse("activado");
        Login cs = new Login();
        cs.show();
    }

}
