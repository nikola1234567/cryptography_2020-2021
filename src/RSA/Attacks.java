package RSA;

import java.io.IOException;
import java.math.BigInteger;

public class Attacks {
    private final static BigInteger CHOSEN_PLAIN_TEXT = new BigInteger("2");

    /*
    * param: encryptedMessage - BigInteger - enkriptirana poraka
    * param: rsa - RSA - instanca od RSA algoritmot
    * param: e - String - javen eksponent
    * param: n - String - modulus
    * return: BigInteger - dekriptirana poraka
    * */
    public BigInteger crack(BigInteger encryptedMessage, RSA rsa, String e, String n) throws IOException {
        return rsa.selfDecrypt(rsa.encrypt(CHOSEN_PLAIN_TEXT, e, n)
                .multiply(encryptedMessage))
                .divide(CHOSEN_PLAIN_TEXT);
    }

    /*
     * param: encryptedMessage - BigInteger - enkriptirana poraka
     * param: rsa - RSA - instanca od RSA algoritmot
     * return: BigInteger - dekriptirana poraka
     * */
    public BigInteger crack(BigInteger encryptedMessage, RSA rsa){
        return rsa.selfDecrypt(rsa.selfEncrypt(CHOSEN_PLAIN_TEXT)
                .multiply(encryptedMessage))
                .divide(CHOSEN_PLAIN_TEXT);
    }

}
