import algorithm.SubstitutionCryptography;
import rabbit.Rabbit;

import java.io.IOException;
import java.math.BigInteger;
import java.util.BitSet;

public class LabMainApp {
    public static void main(String[] args) throws IOException {
        String toHex = "CFCFCFCFCFCFCFCFCFCFCFCFCFCFCFCF";
        BigInteger key = new BigInteger(toHex, 16);
        Rabbit r = new Rabbit(key);

        String ivHex = "0000000000000000";
        BigInteger iv = new BigInteger(ivHex, 16);
        r.setupIV(iv);

        String message = "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        System.out.println(message.length());
        BigInteger msg1 = new BigInteger(message, 2);
        r.encryptText(msg1, 128);
    }
}
