package digital_signatures;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class DiffieHellmanAlgorithm {

    private static DiffieHellmanAlgorithm instance = null;

    BigInteger publicParameterN;
    BigInteger publicParameterG;

    public static DiffieHellmanAlgorithm getInstance() {
        if (instance == null)
            instance = new DiffieHellmanAlgorithm();
        return instance;
    }

    private DiffieHellmanAlgorithm() {
        Random random = new Random();
        this.publicParameterN = BigInteger.probablePrime(64, random);
        this.publicParameterG = new BigInteger("12");
    }

    /*
    * returns: Big Integer - privaten kluc ne pogolem od javniot parametar N
    * */
    public BigInteger generatePrivateKey() {
        return new BigInteger(publicParameterN.bitLength() -2, new SecureRandom()).mod(publicParameterN);
    }

    /*
     * param: privateKey - BigInteger - privaten kluc so koj se generira javniot
     * returns: Big Integer - javniot kluc ne pogolem od javniot parametar N
     * */
    public BigInteger createPublicKey(BigInteger privateKey) {
        return publicParameterG.modPow(privateKey, publicParameterN);
    }

    /*
    * param: privateKey - BigInteger - privaten kluc
    * param: publicKey - BigInteger - javen kluc
    * returns: BigInteger - kluc za simetricna kriptografija
    * */
    public BigInteger generateSymmetricKey(BigInteger privateKey, BigInteger publicKey) {
        return publicKey.modPow(privateKey, publicParameterN);
    }

    @Override
    public String toString() {
        return "DiffieHellmanAlgorithm{" + "\n" +
                "publicParameterN=" + publicParameterN + "\n" +
                ", publicParameterG=" + publicParameterG + "\n" +
                '}';
    }
}
