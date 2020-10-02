package hajas.titkositas;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author Hajas Erik
 */
public class GenerateAES {

    private SecretKey AESkey;
    
    /**
     * 
     * A konstruktor
     * Ez generálja a szimmetrikus kulcsot
     * 
     */
    
    public GenerateAES() {
        try {
            SecureRandom securerandom = new SecureRandom();
            KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
            keygenerator.init(256, securerandom);
            AESkey = keygenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
        }
    }

    // Ez titkosítja a szimmetrikus kulcsot, az asszimetrikussal
    byte[] EncryptRSA(PublicKey RSAkey) {
        byte[] EncryptedKey = null;
        try {

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, RSAkey);
            byte[] des = AESkey.getEncoded();
            cipher.update(des);
            EncryptedKey = cipher.doFinal();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return EncryptedKey;
    }

    //Alice visszafejti az üzenetet a szimmetrikus kulccsal
    void ReadMessage(byte[] Message) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, AESkey);
            byte[] decryptedMessage = cipher.doFinal(Message);
            System.out.println(new String(decryptedMessage, "UTF8"));
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

}
