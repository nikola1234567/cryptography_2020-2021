import DES.DES;
import work_modes.CutAndPasteATTACK;
import work_modes.DesECB;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class LabMainApp {

    public static String padTextIfNeeded(String text) {
        if (text.length() % 8 == 0)
            return text;
        String result = text;

        for (int i = 0 ; result.length() % 8 != 0 ; i++) {
            result += " ";
        }

        return result;
    }

//    public static String unpadTextIfNeeded(String text) {
//
//    }

    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException {

        /**
         * MAIN DEL LABORATORISKA 4 - MODOVI NA RABOTA
         * **/
        byte[] keyBytes = new byte[] { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd,
                (byte) 0xef };
        byte[] ivBytes = new byte[] { 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00 };

        System.out.println("====================== Des ECB ===================");
        DesECB desECB = new DesECB(keyBytes);
        String text = "Nikola is the best of the best!!!";
        System.out.println("TEXT: " + text);
        byte[] encryptedText = desECB.encrypt(text);
        String decryptedText = desECB.decrypt(encryptedText);
        System.out.println("DECRYPTED TEXT: " + decryptedText);




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

        CutAndPasteATTACK attack = new CutAndPasteATTACK();
        String text6 = "Deposit amount: 5 dollars";
        System.out.println("PLAIN TEXT: " + text6);
        byte[] encryptedText6 = desECB.encrypt(text6);
        byte[] encryptedText6AfterAttack = attack.executeAttack(text6, encryptedText6);
        String decryptedText6 = desECB.decrypt(encryptedText6AfterAttack);
        System.out.println("DECRYPTED TEXT AFTER ATTACK: " + decryptedText6);

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
