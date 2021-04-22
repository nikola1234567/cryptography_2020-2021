import DES.DES;

import java.io.IOException;

public class LabMainApp {
    public static void main(String[] args) throws IOException {
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

        //baranje c)
        String textC1 = "0000000000000000";
        String keyC1 = "0000000000000000";

        System.out.println("Encryption:\n");
        textC1 = cipher.encrypt(textC1, keyC1);
        System.out.println(
                "\nCipher Text: " + textC1.toUpperCase() + "\n");
        System.out.println("=======================================================");

        String textC2 = "0000000000000001";
        String keyC2 = "0000000000000000";

        System.out.println("Encryption:\n");
        textC2 = cipher.encrypt(textC2, keyC2);
        System.out.println(
                "\nCipher Text: " + textC2.toUpperCase() + "\n");
        System.out.println("=======================================================");
        System.out.println("Number of changed bits:" + cipher.numberOfBitsChanged(textC1, textC2));
        System.out.println("=======================================================");

        String textC3 = "0000000000000002";
        String keyC3 = "0000000000000000";

        System.out.println("Encryption:\n");
        textC3 = cipher.encrypt(textC3, keyC3);
        System.out.println(
                "\nCipher Text: " + textC3.toUpperCase() + "\n");
        System.out.println("=======================================================");
        System.out.println("Number of changed bits:" + cipher.numberOfBitsChanged(textC1, textC3));
        System.out.println("=======================================================");


        String textC5 = "0000000000000004";
        String keyC5 = "0000000000000000";

        System.out.println("Encryption:\n");
        textC5 = cipher.encrypt(textC5, keyC5);
        System.out.println(
                "\nCipher Text: " + textC5.toUpperCase() + "\n");
        System.out.println("=======================================================");
        System.out.println("Number of changed bits:" + cipher.numberOfBitsChanged(textC1, textC5));
        System.out.println("=======================================================");

        String textC6 = "0000000000000008";
        String keyC6 = "0000000000000000";

        System.out.println("Encryption:\n");
        textC6 = cipher.encrypt(textC6, keyC6);
        System.out.println(
                "\nCipher Text: " + textC6.toUpperCase() + "\n");
        System.out.println("=======================================================");
        System.out.println("Number of changed bits:" + cipher.numberOfBitsChanged(textC1, textC6));
        System.out.println("=======================================================");

        String textC7 = "0000000000000010";
        String keyC7 = "0000000000000000";

        System.out.println("Encryption:\n");
        textC7 = cipher.encrypt(textC7, keyC7);
        System.out.println(
                "\nCipher Text: " + textC7.toUpperCase() + "\n");
        System.out.println("=======================================================");
        System.out.println("Number of changed bits:" + cipher.numberOfBitsChanged(textC1, textC7));
        System.out.println("=======================================================");


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
