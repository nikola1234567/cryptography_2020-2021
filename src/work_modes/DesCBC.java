package work_modes;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class DesCBC {

    Cipher encryptCipher;
    Cipher decryptCipher;

    public DesCBC(byte[] keyBytes, byte[] ivBytes)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException {
        SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        this.encryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        this.encryptCipher.init(Cipher.ENCRYPT_MODE, key, iv);
        this.decryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        this.decryptCipher.init(Cipher.DECRYPT_MODE, key, iv);
    }

    public byte[] encrypt(String plaintext)
            throws BadPaddingException, IllegalBlockSizeException {
        return this.encryptCipher.doFinal(plaintext.getBytes());
    }

    public String decrypt(byte[] cipherText)
            throws BadPaddingException, IllegalBlockSizeException {
        return new String(this.decryptCipher.doFinal(cipherText));
    }
}


