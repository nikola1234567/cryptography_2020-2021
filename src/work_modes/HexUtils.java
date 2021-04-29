package work_modes;

import java.util.LinkedList;

public class HexUtils {

    public static String addPaddingIfNeeded(String blockOfText) {
        String result = blockOfText;
        result += "8";
        int extraBytes = blockOfText.length() == 16 ? 15 : 16 - blockOfText.length() - 1;

        for (int i = 0; i < extraBytes; i++ ) {
            result += "0";
        }

        return result;
    }

    // Char -> Decimal -> Hex
    public static String convertStringToHex(String str) {

        StringBuffer hex = new StringBuffer();

        // loop chars one by one
        for (char temp : str.toCharArray()) {

            // convert char to int, for char `a` decimal 97
            int decimal = (int) temp;

            // convert int to hex, for decimal 97 hex 61
            hex.append(Integer.toHexString(decimal));
        }

        return hex.toString();

    }

    // Hex -> Decimal -> Char
    public static String convertHexToString(String hex) {

        StringBuilder result = new StringBuilder();

        // split into two chars per loop, hex, 0A, 0B, 0C...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            String tempInHex = hex.substring(i, (i + 2));

            //convert hex to decimal
            int decimal = Integer.parseInt(tempInHex, 16);

            // convert the decimal to char
            result.append((char) decimal);

        }

        return result.toString();

    }

    // hexadecimal to binary conversion
    public static String hextoBin(String input)
    {
        int n = input.length() * 4;
        input = Long.toBinaryString(
                Long.parseUnsignedLong(input, 16));
        while (input.length() < n)
            input = "0" + input;
        return input;
    }

    // binary to hexadecimal conversion
    public static String binToHex(String input)
    {
        int n = (int)input.length() / 4;
        input = Long.toHexString(
                Long.parseUnsignedLong(input, 2));
        while (input.length() < n)
            input = "0" + input;
        return input;
    }

    public static LinkedList<String> getBlocks(String text, BlockType type) {
        LinkedList<String> blocks = new LinkedList<>();
        LinkedList<String> blocksAsString = new LinkedList<>();

        String textInHexFormat = HexUtils.convertStringToHex(text).toUpperCase();
        int textLength = text.length();
        int textInHexFormatLength = textInHexFormat.length();

        for (int i = 0, j=0; ;i+=8,j+=16) {
            String stringBlock = i+8 >= textLength ? text.substring(i) : text.substring(i, i+8);
            blocksAsString.add(stringBlock);
            String hexBlock = i+8 >= textLength ? textInHexFormat.substring(j) : textInHexFormat.substring(j, j+16);
            blocks.add(hexBlock);

            if (i+8 >= textLength)
                break;
        }

        return type == BlockType.AS_HEX ? blocks : blocksAsString;
    }

}