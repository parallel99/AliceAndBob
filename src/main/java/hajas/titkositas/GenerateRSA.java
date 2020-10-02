package hajas.titkositas;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Hajas Erik
 */
public class GenerateRSA {
    
    private KeyPair pair;
    private SecretKey AliceKey;

    /**
     * 
     * A konstruktor
     * Ez generálja az asszimetrikus kulcsokat
     * 
     */
    
    public GenerateRSA() {
        try { 
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048);
            pair = keyPairGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
        }
    }

    //Ez adja vissza a nyilvános kulcsot
    PublicKey GetPublicKey() {
        return pair.getPublic();
    }

    //Ez dekódolja az asszimetrikus kulcsot
    void DecryptRSA(byte[] EncryptedAES) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            
            cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
            cipher.update(EncryptedAES);
            byte[] AESkey = cipher.doFinal();
            AliceKey = new SecretKeySpec(AESkey, "AES");
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
    
    //Ez küldi az üzenetet Alice-nek
    byte[] SendMessage(String msg){
        try {
            
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, AliceKey);
            byte[] encryptedMessage = cipher.doFinal(msg.getBytes());
            
            return encryptedMessage;
            
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return null;
    }
}
