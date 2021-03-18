import algorithm.SubstitutionCryptography;

import java.io.IOException;

public class LabMainApp {
    public static void main(String[] args) throws IOException {
        SubstitutionCryptography substitutionCryptography = new SubstitutionCryptography();
//        substitutionCryptography.encryptText("C:\\Users\\DELL\\Desktop\\nikola\\crypto_lab1\\src\\files\\textNotCrypted.txt",
//                "C:\\Users\\DELL\\Desktop\\nikola\\crypto_lab1\\src\\files\\textCrypted.txt");

        substitutionCryptography.generateFrequencies("C:\\Users\\DELL\\Desktop\\nikola\\crypto_lab1\\src\\files\\crypted.txt",
                "C:\\Users\\DELL\\Desktop\\nikola\\crypto_lab1\\src\\files\\frequencies.txt");

//        substitutionCryptography.resultOfReplacing(new String[]{"ј"}, new String[]{"а"},"C:\\Users\\DELL\\Desktop\\nikola\\crypto_lab1\\src\\files\\crypted.txt");

//        substitutionCryptography.resultOfReplacing(
//                new String[]{"ј","у","и","ќ","р","х","д"},
//                new String[]{"а","и","е","н","т","о","к"},
//                "C:\\Users\\DELL\\Desktop\\nikola\\crypto_lab1\\src\\files\\crypted.txt");

        substitutionCryptography.resultOfReplacing(
                new String[]{"ј","ќ","у", "и","х", "к","ф", "т", "р", "м", "љ", "н", "д","њ", "ѓ", "г", "ѕ","а", "џ","в", "п", "с", "ц", "ш","з", "в","о", "ж"},
                new String[]{"а","н","и", "е","о", "т", "м", "с", "в", "д", "к", "ј", "р", "л", "п", "ч", "з", "ф", "ц","у", "ш", "г", "ж", "њ","ѓ", "у","б", "х"},
                "C:\\Users\\DELL\\Desktop\\nikola\\crypto_lab1\\src\\files\\crypted.txt");
//        substitutionCryptography.resultOfReplacing(new String[]{"ј","у","и"}, new String[]{"а","е","о"},"C:\\Users\\DELL\\Desktop\\nikola\\crypto_lab1\\src\\files\\crypted.txt");


    }
}
