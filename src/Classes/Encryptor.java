/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author OSBAL
 */
public class Encryptor {

    public String clearText(String text) {
        String resp = "";
        for (int i = 0; i < text.length(); i++) {
            if (isVarCharKey(text.charAt(i)) || isLetterKey(text.charAt(i)) || isNumberKey(text.charAt(i))) {
                resp += text.charAt(i);
            }
        }
        return resp;
    }

    public String getIntValue(String text) {
        String resp = "";
        for (int i = 0; i < text.length(); i++) {
            if (isNumberKey(text.charAt(i))) {
                resp += text.charAt(i);
            }
        }
        return resp;
    }

    public String getDoubleValue(String text) {
        String resp = "";
        for (int i = 0; i < text.length(); i++) {
            if (isDoubleKey(text.charAt(i))) {
                resp += text.charAt(i);
            }
        }
        return resp;
    }

    public boolean isVarCharKey(char caracter) {
        return (int) caracter == 32;
    }

    public boolean isLetterKey(char caracter) {
        return (((int) caracter >= 65) || ((int) caracter <= 90)) || (((int) caracter >= 97) || ((int) caracter <= 122));
    }

    public boolean isDoubleKey(char caracter) {
        return (((int) caracter >= 48) || ((int) caracter <= 57) || caracter == '.');
    }

    public boolean isNumberKey(char caracter) {
        return (((int) caracter >= 48) || ((int) caracter <= 57));
    }

    public String EncryptData(String txt) {
        String texto = clearText(txt);
        String secretKey = "qualityinfosolutions"; //llave para encriptar datos
        String base64EncryptedString = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return base64EncryptedString;
    }

    public String DecryptData(String textoEncriptado) throws Exception {
        String secretKey = "qualityinfosolutions"; //llave para desenciptar datos
        String base64EncryptedString = "";
        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plainText = decipher.doFinal(message);
            base64EncryptedString = new String(plainText, "UTF-8");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return base64EncryptedString;
    }
}
