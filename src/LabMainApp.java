import DES.DES;
import digital_signatures.Person;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class LabMainApp {

    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException {
        /**
         * MAIN DEL LABORATORISKA 6 - Digital Signatures
         * **/
        Person alice = new Person("Alice");
        Person bob = new Person("Bob");

        // (1) Alice → Bob : αx
        bob.startCommunication(alice.getPublicKey());

        // (2) Bob → Alice: αy, EK(SB(αy, αx))
        alice.startCommunication(bob.getPublicKey());
        alice.receiveDsaPublicKeyFromOtherPerson(bob.getDsaPublicKey());
        alice.receiveEncryptedSignature(bob.generateEncryptedSignature());

        // (3) Alice → Bob : EK(SA(αx, αy))
        bob.receiveDsaPublicKeyFromOtherPerson(alice.getDsaPublicKey());
        bob.receiveEncryptedSignature(alice.generateEncryptedSignature());


        /**
         * MAIN DEL LABORATORISKA 5 - RSA
         * **/
//        RSA rsa = new RSA();
//        System.out.println("============== ALL PARAMETERS ==============");
//        System.out.println(rsa.getAllParameters());
//        System.out.println();
//        System.out.println("============== PUBLIC KEY ==============");
//        System.out.println(rsa.getPublicKey());
//        System.out.println();
//        rsa.runTestOfAlgorithm();
//        System.out.println();
//        System.out.println("============== ONE TRY OF ENCRYPTION ==============");
//        String n = "366511550775536784011035664875037180580058521422390977957430689899090641400352985895466013324668854927399732050982184373330590025639111046016132574833501920951029040848235720376709848562283007606150395871821959108546055200517047314197115116153764363530451809730864983617410689505853000548748581259249260146822684077817175003170664966117145206637654308724839808270225817066157257236451921304080315285474653180139295040519911910953904422304962547285997186742617025342248568606448461717989113475809507010998595985437712387732661842741202287361429862258923101043196191547729581392928468125910664544128489767968720842263941253397511187208734963778019142249799443775064887320684248463554272515442495203232645335582037631232495119060140318269344327618997517821627613853718217232764949948203209862492071530084627571119576258407532679659986468420735818313790321034215036521648506232095008508113127031311430898141308618768097201678095439897136056843680521332552150993222807700647712881276825573181134797852888003571307990507498405425061369403319288401361562884421686007684140947284426528430068350505321746133015027549675142112250908099488949715318348612146877760809930366144840282333477498706863586741326527191507667054006965532040923935228039";
//        String e = "65537";
//        String plainText = "Isprtena poraka za test na RSA algoritam, laboratoriska 5 po predmetot Kriptografija 2020/2021 leten semestar. ^-^";
//        BigInteger cipherText = rsa.encrypt(plainText, e, n);
//        System.out.println(String.format("n = %s\ne = %s\nplain text = %s\ncipher text = %s",
//                n, e, plainText, cipherText.toString(10)));
//
//        //napad na sebesi
//        BigInteger message = new BigInteger("123456");
//        BigInteger encrypted = rsa.selfEncrypt(message);
//        System.out.println(rsa.getAllParameters());
//
//        Attacks chosenCipherTextAttack = new Attacks();
//        BigInteger cracked = chosenCipherTextAttack.crack(encrypted, rsa);
//
//        System.out.println("message = " + message);
//        System.out.println("cracked = " + cracked);
//        System.out.println(cracked.equals(message) ?  "Success!" : "Failure!");
//        System.out.println("====================================================================================================================\n");
//
//        BigInteger messageToDecrypt = new BigInteger("82732067651234755943758878476523916674924529355852050521209934699239670436037557307219637265575258484654138666849744148114785263155409736691162279815958150621966667587206886200800491142814956939557195559499856581636089055106626407964090146119747968253067090136526708513241324836033303655227144927833459143025");
//        System.out.println("<DECRYPTED> ---->> " + rsa.decrypt(messageToDecrypt));
//        String n1 = "366511550775536784011035664875037180580058521422390977957430689899090641400352985895466013324668854927399732050982184373330590025639111046016132574833501920951029040848235720376709848562283007606150395871821959108546055200517047314197115116153764363530451809730864983617410689505853000548748581259249260146822684077817175003170664966117145206637654308724839808270225817066157257236451921304080315285474653180139295040519911910953904422304962547285997186742617025342248568606448461717989113475809507010998595985437712387732661842741202287361429862258923101043196191547729581392928468125910664544128489767968720842263941253397511187208734963778019142249799443775064887320684248463554272515442495203232645335582037631232495119060140318269344327618997517821627613853718217232764949948203209862492071530084627571119576258407532679659986468420735818313790321034215036521648506232095008508113127031311430898141308618768097201678095439897136056843680521332552150993222807700647712881276825573181134797852888003571307990507498405425061369403319288401361562884421686007684140947284426528430068350505321746133015027549675142112250908099488949715318348612146877760809930366144840282333477498706863586741326527191507667054006965532040923935228039";
//        String e1 = "65537";
//        String plainText1 = "2";
//        BigInteger cipherText1 = rsa.encrypt(plainText, e, n);
//
//        BigInteger messageForAttack = cipherText.multiply(cipherText1);
//        System.out.println(messageForAttack.toString());
//
//        String feedback = "\\x15\\x13\\n/~\\x9a\\xc5\\x19/\\x9a\\xa3\\xae\\xef\\x9b5lS9\\x80+\\xff\\xaaR\\x0c\\xa3\\xc0?\\xb0\\x11\\xbc\\xc2\\xda\\xf8\\x8a,\\xb9\\xc8\\xe3\\nf\\x00\\xeb7 \\'\\xcc\\x9c0D\\xb7\\xcd\\\\\\x14\\xef\\x81\\x86\\xc6\\xdd\\xdf\\xa4I\\x8f\\x1e\\xb5\\x0e\\x85\\x8d\\xf8C.\\xfdr\\xfb*\\xbaB6=:x|!a\\r\\x8a\\x1bo{\\xa1\\x05\\xb4\\x9ctt\\xdf\\x00\\x7f\\x90mK\\x86\\x9dj\\x96\\xfcw[i\\xf0{c\\xe5\\xf4\\xa4@\\x1ar\\x0bZB\\x85\"\\x00\\xfe\\x0c\\xb2\\xa4d\\xf0\\xfdQ\\x07?\\x03\\xaa\\rZ\\x80|\\xd3JX0\\xe0\\xbe\\xaaH\\xae\\xd8\\xf8k\\xf2\\x97\\xdb5\\xc9o/\\x077c\\x1a\\xa8J\\x98\\x10\\xd6/9\\xbe\\xf1\\xbd\\x19\\x981ce~-\\x15\\\\\\xb1\\x11\\x95\\xc6\\xdfJ\\x03\\x0b4\\xb4=cw\\xc1\\x83~\\xb3\\x85\\xa7I,\\xb2\\x85\\x0fS;\\x97\\x84\\x15\\xd4n\\x12\\x1b\\x91\\xff\\xb9\\xab\\xec\\x13\\x17\\x93\\xb3\\xc4\\xd9\\x12.\\x84";
//        BigInteger f = new BigInteger(feedback.getBytes());
//        BigInteger decrypted = f.divide(new BigInteger(plainText1));
//        Charset charset = StandardCharsets.US_ASCII;
//        System.out.println("==================================================");
//        System.out.println(charset.decode(ByteBuffer.wrap(decrypted.toByteArray())).toString());

        /**
         * MAIN DEL LABORATORISKA 4 - MODOVI NA RABOTA
         * **/
//        byte[] keyBytes = new byte[] { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd,
//                (byte) 0xef };
//        byte[] ivBytes = new byte[] { 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00 };
//
//        System.out.println("====================== Des ECB ===================");
//        DesECB desECB = new DesECB(keyBytes);
//        String text = "Nikola is the best of the best!!!";
//        System.out.println("TEXT: " + text);
//        byte[] encryptedText = desECB.encrypt(text);
//        String decryptedText = desECB.decrypt(encryptedText);
//        System.out.println("DECRYPTED TEXT: " + decryptedText);




//        byte[] encryptedTextAttack = attack.executeAttack(text, encryptedText);
//        String decryptedTextAttack = desECB.decrypt(encryptedTextAttack);
//        System.out.println("DECRYPTED TEXT AFTER ATTACK: " + decryptedTextAttack);

//        CutAndPasteATTACK attack = new CutAndPasteATTACK();
//        String text3 = "Alice digs Bob. Trudy digs Tom.";
//        System.out.println("PLAIN TEXT: " + text3);
//        byte[] encryptedText3 = desECB.encrypt(text3);
//        byte[] encryptedText3AfterAttack = attack.executeAttack(text3, encryptedText3);
//        String decryptedText3 = desECB.decrypt(encryptedText3AfterAttack);
//        System.out.println("DECRYPTED TEXT AFTER ATTACK: " + decryptedText3);

//        CutAndPasteATTACK attack = new CutAndPasteATTACK();
//        String text4 = "100 denari ili  500 denari da ti dadam?";
//        System.out.println("PLAIN TEXT: " + text4);
//        byte[] encryptedText4 = desECB.encrypt(text4);
//        byte[] encryptedText4AfterAttack = attack.executeAttack(text4, encryptedText4);
//        String decryptedText4 = desECB.decrypt(encryptedText4AfterAttack);
//        System.out.println("DECRYPTED TEXT AFTER ATTACK: " + decryptedText4);

//        CutAndPasteATTACK attack = new CutAndPasteATTACK();
//        String text5 = "Money for Trudy is $1000Money for Alice is $2";
//        System.out.println("PLAIN TEXT: " + text5);
//        byte[] encryptedText5 = desECB.encrypt(text5);
//        byte[] encryptedText5AfterAttack = attack.executeAttack(text5, encryptedText5);
//        String decryptedText5 = desECB.decrypt(encryptedText5AfterAttack);
//        System.out.println("DECRYPTED TEXT AFTER ATTACK: " + decryptedText5);

//        CutAndPasteATTACK attack = new CutAndPasteATTACK();
//        String text6 = "Deposit amount: 5 dollars";
//        System.out.println("PLAIN TEXT: " + text6);
//        byte[] encryptedText6 = desECB.encrypt(text6);
//        byte[] encryptedText6AfterAttack = attack.executeAttack(text6, encryptedText6);
//        String decryptedText6 = desECB.decrypt(encryptedText6AfterAttack);
//        System.out.println("DECRYPTED TEXT AFTER ATTACK: " + decryptedText6);

//        CutAndPasteATTACK attack = new CutAndPasteATTACK();
//        String text7 = "For asymmetric encryption, choose a key size of at least 2048 bits. I would encourage this purely for future-proofing your applications. Obviously, if you can afford it (from a hardware and software perspective), use at least 4096 bits key size.";
//        System.out.println("PLAIN TEXT: " + text7);
//        byte[] encryptedText7 = desECB.encrypt(text7);
//        byte[] encryptedText7AfterAttack = attack.executeAttack(text7, encryptedText7);
//        String decryptedText7 = desECB.decrypt(encryptedText7AfterAttack);
//        System.out.println("DECRYPTED TEXT AFTER ATTACK: " + decryptedText7);


//        System.out.println();
//        System.out.println("====================== Des CBC ===================");
//        DesCBC desCBC = new DesCBC(keyBytes, ivBytes);
//        String text1 = "Nikolaaaa!!!";
//        System.out.println("TEXT: " + text1);
//        byte[] encryptedText1 = desCBC.encrypt(text1);
//        String decryptedText1 = desCBC.decrypt(encryptedText1);
//        System.out.println("DECRYPTED TEXT: " + decryptedText1);


//        byte[] bytes = attack.executeAttack(text1, encryptedText1);
//        System.out.println(desCBC.decrypt(bytes));

//        String text3 = "Alice digs Bob. Trudy digs Tom.";
//        System.out.println("PLAIN TEXT: " + text3);
//        byte[] encryptedText3 = desCBC.encrypt(text3);
//        byte[] encryptedText3AfterAttack = attack.executeAttack(text3, encryptedText3);
//        String decryptedText3 = desCBC.decrypt(encryptedText3AfterAttack);
//        System.out.println("DECRYPTED TEXT AFTER ATTACK: " + decryptedText3);




    /**
     * MAIN DEL LABORATORISKA 2 - RABBIT ALGORITAM
     * **/
//        String toHex = "1B1B1B1B1B1B1B1B1B1B1B1B1B1B1B1B";
//        BigInteger key = new BigInteger(toHex, 16);
//        Rabbit r = new Rabbit(key);
//
//        String ivHex = "0000000000000000";
//        BigInteger iv = new BigInteger(ivHex, 16);
//        r.setupIV(iv);
//
//        String message = "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
//        System.out.println(message.length());
//        BigInteger msg1 = new BigInteger(message, 2);
//        r.encryptText(msg1, 128);

        /**
         * MAIN DEL LABORATORISKA 3 - DES ALGORITAM
         * **/
        DES cipher = new DES();

//        //baranje a)
//        String textА = "0000000000000000";
//        String keyА = "0000000000000000";
//
//        System.out.println("Encryption:\n");
//        textА = cipher.encrypt(textА, keyА);
//        System.out.println(
//                "\nCipher Text: " + textА.toUpperCase() + "\n");
//        System.out.println("=======================================================");
//
//        //baranje б)
//        String textB = "FFFFFFFFFFFFFFFF";
//        String keyB = "FFFFFFFFFFFFFFFF";
//
//        System.out.println("Encryption:\n");
//        textB = cipher.encrypt(textB, keyB);
//        System.out.println(
//                "\nCipher Text: " + textB.toUpperCase() + "\n");
//        System.out.println("=======================================================");

//        //baranje c)
//        String textC1 = "0000000000000000";
//        String keyC1 = "0000000000000000";
//
//        System.out.println("Encryption:\n");
//        textC1 = cipher.encrypt(textC1, keyC1);
//        System.out.println(
//                "\nCipher Text: " + textC1.toUpperCase() + "\n");
//        System.out.println("=======================================================");
//
//        String textC2 = "0000000000000001";
//        String keyC2 = "0000000000000000";
//
//        System.out.println("Encryption:\n");
//        textC2 = cipher.encrypt(textC2, keyC2);
//        System.out.println(
//                "\nCipher Text: " + textC2.toUpperCase() + "\n");
//        System.out.println("=======================================================");
//        System.out.println("Number of changed bits:" + cipher.numberOfBitsChanged(textC1, textC2));
//        System.out.println("=======================================================");
//
//        String textC3 = "0000000000000002";
//        String keyC3 = "0000000000000000";
//
//        System.out.println("Encryption:\n");
//        textC3 = cipher.encrypt(textC3, keyC3);
//        System.out.println(
//                "\nCipher Text: " + textC3.toUpperCase() + "\n");
//        System.out.println("=======================================================");
//        System.out.println("Number of changed bits:" + cipher.numberOfBitsChanged(textC1, textC3));
//        System.out.println("=======================================================");
//
//
//        String textC5 = "0000000000000004";
//        String keyC5 = "0000000000000000";
//
//        System.out.println("Encryption:\n");
//        textC5 = cipher.encrypt(textC5, keyC5);
//        System.out.println(
//                "\nCipher Text: " + textC5.toUpperCase() + "\n");
//        System.out.println("=======================================================");
//        System.out.println("Number of changed bits:" + cipher.numberOfBitsChanged(textC1, textC5));
//        System.out.println("=======================================================");
//
//        String textC6 = "0000000000000008";
//        String keyC6 = "0000000000000000";
//
//        System.out.println("Encryption:\n");
//        textC6 = cipher.encrypt(textC6, keyC6);
//        System.out.println(
//                "\nCipher Text: " + textC6.toUpperCase() + "\n");
//        System.out.println("=======================================================");
//        System.out.println("Number of changed bits:" + cipher.numberOfBitsChanged(textC1, textC6));
//        System.out.println("=======================================================");
//
//        String textC7 = "0000000000000010";
//        String keyC7 = "0000000000000000";
//
//        System.out.println("Encryption:\n");
//        textC7 = cipher.encrypt(textC7, keyC7);
//        System.out.println(
//                "\nCipher Text: " + textC7.toUpperCase() + "\n");
//        System.out.println("=======================================================");
//        System.out.println("Number of changed bits:" + cipher.numberOfBitsChanged(textC1, textC7));
//        System.out.println("=======================================================");


        // слаби клучеви
//        String text = "0000000000000000";
//        String weakKey1 = "FFFFFFF0000000";
//
//        System.out.println("ORIGINAL TEXT: " + text);
//        String encrypted_text = cipher.encrypt(text, weakKey1);
//        System.out.println("ENCRYPTED: " + encrypted_text);
//        String second_encryption = cipher.encrypt(encrypted_text.toUpperCase(), weakKey1);
//        System.out.println("ENCRYPTED SECOND TIME: " + second_encryption);

//        String text = "0000000000000000";
//        String weakKey2 = "0000000FFFFFFF";
//
//        System.out.println("ORIGINAL TEXT: " + text);
//        String encrypted_text = cipher.encrypt(text, weakKey2);
//        System.out.println("ENCRYPTED: " + encrypted_text);
//        String second_encryption = cipher.encrypt(encrypted_text.toUpperCase(), weakKey2);
//        System.out.println("ENCRYPTED SECOND TIME: " + second_encryption);
//
//        String textC6 = "0000000000000000";
//        String keyC6 = "0000000000000000";
//
//        System.out.println("Encryption:\n");
//        String text_encrypted = cipher.encryptWithKeepingHistory(textC6, keyC6);
//        System.out.println(
//                "\nCipher Text: " + text_encrypted.toUpperCase() + "\n");
//        System.out.println("=======================================================");
//
//        LinkedList<String> sBoxInputs = cipher.getSBoxInputs(textC6, keyC6);
//        for (String input : sBoxInputs) {
//            System.out.println(input);
//        }
//
//
//        String textC2 = "0000000000000001";
//        String keyC2 = "0000000000000000";
//
//        System.out.println("Encryption:\n");
//        String text2_encrypted = cipher.encryptWithKeepingHistory(textC2, keyC2);
//        System.out.println(
//                "\nCipher Text: " + text2_encrypted.toUpperCase() + "\n");
//        System.out.println("=======================================================");
//
//        LinkedList<String> sBoxInputs2 = cipher.getSBoxInputs(textC2, keyC2);
//        for (String input : sBoxInputs2) {
//            System.out.println(input);
//        }
//
//        System.out.println("============ DIFFERENCES ==========");
//        LinkedList<String> differences = cipher.getSBoxInputsDifferences(sBoxInputs, sBoxInputs2);
//        for (String entry: differences) {
//            System.out.println(entry);
//        }
//        System.out.println(differences.size());
    }
}
