package RSA;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class RSA {
    BigInteger primeNumber1;
    BigInteger primeNumber2;
    BigInteger publicKeyN;
    BigInteger publicKeyE;
    BigInteger privateKeyD;

    /*
    * konstruktor preku koj se inicijaliziraat parametrite na algoritmot
    * */
    public RSA() {
        this.primeNumber1 = new BigInteger("10813255079903187995362471945742370564965102709177105698534554815737392517160245161827037279142355028467804985008599209566109661534050050462081176133294799");
        this.primeNumber2 = new BigInteger("8510550132782927263006728934210669124536246404129419529410663803190697945762815370336070516551971859096176632195451815053505156612121303991047804593516967");
        this.publicKeyN = this.primeNumber1.multiply(this.primeNumber2);
        this.publicKeyE = new BigInteger("65537");

        BigInteger numberOne = new BigInteger("1");
        BigInteger tempPhiValueOfN = (this.primeNumber1.subtract(numberOne)).multiply(this.primeNumber2.subtract(numberOne));
        this.privateKeyD = this.publicKeyE.modInverse(tempPhiValueOfN);
    }

    /*
     * returns: String - go vrakja javniot kluc vo forma na string
     * +: pomosna funkcija
     * */
    public String getPublicKey() {
        return String.format("N = %s\ne = %s", this.publicKeyN.toString(10), this.publicKeyE.toString(10));
    }

    /*
     * returns: String - gi vrakja site parametri na algoritmot vo forma na string
     * +: pomosna funkcija
     * */
    public String getAllParameters() {
        return String.format("primeNumber1 = %s\nprimeNumber2 = %s\nN = %s\ne = %s\nd = %s\n",
                this.primeNumber1.toString(10),
                this.primeNumber2.toString(10),
                this.publicKeyN.toString(10),
                this.publicKeyE.toString(10),
                this.privateKeyD.toString(10));
    }

    /*
    * param: plainText - String - porakata sto sakame da ja enkriptirame
    * param: publicKeyEParam - String - javniot eksponent
    * param: publicKeyNParam - String - modulus na algoritmot
    * return: BigInteger - enkriptirana poraka
    * +: enkriptiranata poraka isto taka ja zacuvuva vo datoteka
    * */
    public BigInteger encrypt(String plainText, String publicKeyEParam, String publicKeyNParam) throws IOException {
        byte[] plainTextBytes = plainText.getBytes();
        BigInteger M = new BigInteger(plainTextBytes);
        BigInteger N = new BigInteger(publicKeyNParam);
        BigInteger E = new BigInteger(publicKeyEParam);
        BigInteger encryptedText = M.modPow(E, N);

        String path = "C:\\Users\\DELL\\Desktop\\documents\\nikola-NEW\\nikola\\crypto_lab1\\src\\RSA\\encrypted_text.txt";
        PrintWriter pw = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(
                                new File(path)
                        )
                )
        );

        String textToBeWrittenToFile = String.format("PLAIN TEXT: %s\nCIPHER TEXT: %s",
                plainText, encryptedText.toString(10));
        pw.println(textToBeWrittenToFile);
        pw.flush();
        pw.close();

        return encryptedText;
    }

    /*
     * param: plainText - BigInteger - porakata sto sakame da ja enkriptirame vekje kastirana vo brojki
     * param: publicKeyEParam - String - javniot eksponent
     * param: publicKeyNParam - String - modulus na algoritmot
     * return: BigInteger - enkriptirana poraka
     * */
    public BigInteger encrypt(BigInteger plainText, String publicKeyEParam, String publicKeyNParam) throws IOException {
        BigInteger N = new BigInteger(publicKeyNParam);
        BigInteger E = new BigInteger(publicKeyEParam);
        BigInteger encryptedText = plainText.modPow(E, N);
        return encryptedText;
    }

    /*
    * param: cipherText - BigInteger - kriptiran tekst
    * return: String - porakata dekriptirana
    * */
    public String decrypt(BigInteger cipherText) {
        BigInteger decryptedAsBigInt = cipherText.modPow(this.privateKeyD, this.publicKeyN);
        Charset charset = StandardCharsets.US_ASCII;
        return charset.decode(ByteBuffer.wrap(decryptedAsBigInt.toByteArray())).toString();
    }

    /*
    * param: message - BigInteget - poraka za kriptiranje vekje pretvorena vo cel broj
    * return: BigInteger - kriptirana poraka
    * +: ovaa funkcija kriptira so javniot kluc kreiran vo konstruktorot
    * */
    public BigInteger selfEncrypt(BigInteger message) {
        return message.modPow(this.publicKeyE, this.publicKeyN);
    }

    /*
    * param: encrypted - BigIntegre - kriptirana poraka
    * return: BigInteger - dekriptirana poraka vo forma na string
    * */
    public BigInteger selfDecrypt(BigInteger encrypted) {
        return encrypted.modPow(this.privateKeyD, this.publicKeyN);
    }

    /*
    * return: Void
    * +: pomosna funkcija za testiranje na algoritmot
    * */
    public void runTestOfAlgorithm() throws IOException {
        String message = "Test poraka za proverka na algoritmot. ";
        BigInteger encrypted = this.encrypt(message, this.publicKeyE.toString(), this.publicKeyN.toString());
        String decrypted = this.decrypt(encrypted);
        System.out.println("========== PLAIN TEXT ==========");
        System.out.println(message);
        System.out.println("========== DECRYPTED TEXT ==========");
        System.out.println(decrypted);
    }

    /*GETTERS & SETTERS sekcija*/

    public BigInteger getPrimeNumber1() {
        return primeNumber1;
    }

    public BigInteger getPrimeNumber2() {
        return primeNumber2;
    }

    public BigInteger getPublicKeyN() {
        return publicKeyN;
    }

    public BigInteger getPublicKeyE() {
        return publicKeyE;
    }

    public BigInteger getPrivateKeyD() {
        return privateKeyD;
    }
}
