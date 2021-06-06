package digital_signatures;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;

public class Person {

    String name;

    BigInteger privateKey;
    BigInteger publicKey;
    DSAPublicKey dsaPublicKey;

    BigInteger otherPersonPublicKey;
    DSAPublicKey otherPersonDsaPublicKey;
    BigInteger privateSymmetricKey;

    DES des;

    public Person(String name) {
        this.name = name;
        this.privateKey =  DiffieHellmanAlgorithm.getInstance().generatePrivateKey();
        this.publicKey = DiffieHellmanAlgorithm.getInstance().createPublicKey(this.privateKey);
        this.dsaPublicKey = DSA.getInstance(name).getPublicKey();
        System.out.println(this);
    }

    /* GETTERS & SETTERS za atributite */
    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public BigInteger getPrivateSymmetricKey() {
        return privateSymmetricKey;
    }

    public BigInteger sharePublicKey() {
        return this.publicKey;
    }

    public BigInteger shareEncryptedSignature() {
        return null;
    }

    public DSAPublicKey getDsaPublicKey() {
        return dsaPublicKey;
    }

    public void receiveDsaPublicKeyFromOtherPerson(DSAPublicKey key) {
        this.otherPersonDsaPublicKey = key;
    }

    /*
    * param: key - BigInteger - klucot na toj sho saka da komunicira so nas
    * returns: void - ne vrakja niso, se inicijaliziraat parametri samo
    * */
    public void startCommunication(BigInteger key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        this.otherPersonPublicKey = key;
        this.privateSymmetricKey = DiffieHellmanAlgorithm.getInstance().generateSymmetricKey(
                this.privateKey,
                this.otherPersonPublicKey
        );
        this.des = new DES(privateSymmetricKey.toByteArray());
    }

    /*
    * param: encryptedSignature - BigInteger - enkriptirana i potpisana poraka
    * returns: void - ja preprakja da se verificira
    * */
    public void receiveEncryptedSignature(BigInteger encryptedSignature) throws BadPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException {
        this.verifyOtherPersonSignature(encryptedSignature);
    }

    /*
     * param: encryptedSignature - BigInteger - enkriptirana i potpisana poraka
     * returns: void - ja verificira samata poraka i pecati soodvetna poraka
     * */
    private void verifyOtherPersonSignature(BigInteger encryptedSignature) throws BadPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException {
        String decryptedMessage = this.des.decrypt(encryptedSignature.toByteArray());

        String []blocks = decryptedMessage.split(",");
        String x = blocks[0] + "," + blocks[1];
        BigInteger r = new BigInteger(blocks[2]);
        BigInteger s = new BigInteger(blocks[3]);

        if (DSA.getInstance(name).signatureVerification(otherPersonDsaPublicKey, r, s, x)) {
            System.out.println("Successful digital signing!!!!!");
        } else System.out.println("FAILED!!!!!!!!!!!");
    }

    /*
    * returns: BigInteger - enkriptirana i potpisana poraka
    * */
    public BigInteger generateEncryptedSignature() throws NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        String message = this.publicKey.toString() + "," + this.otherPersonPublicKey.toString();
        Hashtable<String, BigInteger> signatureParams = DSA.getInstance(this.name).generateSignature(this.name, message);
        BigInteger signature = signatureParams.get("signature");
        BigInteger r = signatureParams.get("r");
        String messageToEncrypt = message + "," + r.toString() + "," + signature.toString();
        byte[] encryptedMessage = this.des.encrypt(messageToEncrypt);
        return new BigInteger(encryptedMessage);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", privateKey=" + privateKey +
                ", publicKey=" + publicKey +
                '}';
    }
}
