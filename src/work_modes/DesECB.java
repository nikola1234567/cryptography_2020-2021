package work_modes;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DesECB {

    Cipher encryptCipher;
    Cipher decryptCipher;

    public DesECB(byte[] keyBytes)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
        this.encryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        this.encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        this.decryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        this.decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    public byte[] encrypt(String plainText)
            throws BadPaddingException, IllegalBlockSizeException {
        return this.encryptCipher.doFinal(plainText.getBytes());
    }

    public String decrypt(byte[] cipherText)
            throws BadPaddingException, IllegalBlockSizeException {
        return new String(this.decryptCipher.doFinal(cipherText));
    }
}


