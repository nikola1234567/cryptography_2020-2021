package digital_signatures;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;

public class DSA {

    private static DSA aliceDSA = null;
    private static DSA bobDSA = null;

    public BigInteger p;
    public BigInteger q;
    public BigInteger alpha;
    public BigInteger beta;
    private BigInteger d;

    public static DSA getInstance(String name) {
        boolean alice = name.toLowerCase().equals("alice");
        DSA instance = alice ? aliceDSA : bobDSA;
        if (instance == null)
            instance = new DSA(name);
        return instance;
    }

    private DSA(String name) {
        boolean alice = name.toLowerCase().equals("alice");
        p = new BigInteger(alice ? "62" : "59");
        q = new BigInteger(alice ? "32" : "29");
        alpha = new BigInteger(alice ? "6" : "3");
        d = new BigInteger(alice ? "10" : "7");
        beta = alpha.modPow(d, p);
    }

    /*
    * param: name - String - imeto na likot sto ja povikuva funkcijata (Alice/ Bob)
    * param: x - String - poraka sto kje ja enkriptirame i potpiseme
    * returns: Hashtable<String, BigInteger> - hesh tabela vo koja se samiot potpis i vrednosta r pod iminjata "signature"
    * i "r"
    * */
    public Hashtable<String, BigInteger> generateSignature(String name, String x) throws NoSuchAlgorithmException {
        boolean alice = name.toLowerCase().equals("alice");
        BigInteger Ke = new BigInteger(alice ? "13" : "10");
        BigInteger r = (alpha.modPow(Ke, p)).mod(q);
        BigInteger signature = HashingMethods.SHA256(x).add(d.multiply(r)).multiply(Ke.modInverse(q)).mod(q);
        Hashtable<String, BigInteger> res = new Hashtable<>();
        res.put("r", r);
        res.put("signature", signature);
        return res;
    }

    /*
    * param: key - DSAPublicKey - javniot kluc na toj so nija potpisal porakata
    * param: r - BigInteger - r vrednosta pri potpisuvanje
    * param: s - BigInteger - potpisot pri potpisuvanje
    * param: x - BigInteger - porakata sto e ispratena
    * returns: booelan - dali e potpisana od toj so tvrdi
    * */
    public boolean signatureVerification(DSAPublicKey key, BigInteger r, BigInteger s, String x) throws NoSuchAlgorithmException {
        BigInteger w = s.modInverse(key.getQ());
        BigInteger u1 = w.multiply(HashingMethods.SHA256(x)).mod(key.getQ());
        BigInteger u2 = w.multiply(r).mod(key.getQ());
        BigInteger v = ((key.getAlpha().modPow(u1, key.getP()).multiply(key.getBeta().modPow(u2, key.getP()))).mod(key.getP())).mod(key.getQ());
        return v.mod(key.getQ()).equals(r.mod(key.getQ()));
    }

    public DSAPublicKey getPublicKey() {
        return new DSAPublicKey(p, q, alpha, beta);
    }
}
