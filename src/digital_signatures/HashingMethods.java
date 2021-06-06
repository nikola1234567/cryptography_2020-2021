package digital_signatures;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingMethods {
    public static BigInteger SHA256(String message) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte [] hashedMessage = messageDigest.digest(message.getBytes());
        return new BigInteger(1, hashedMessage);
    }
}
