package work_modes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class CutAndPasteATTACK {

    public CutAndPasteATTACK() {}

    private LinkedList<String> getBlocks(String text) {
        LinkedList<String> blocksAsString = new LinkedList<>();
        int textLength = text.length();
        for (int i = 0, j=0; ;i+=8,j+=16) {
            String stringBlock = i+8 >= textLength ? text.substring(i) : text.substring(i, i+8);
            blocksAsString.add(stringBlock);
            if (i+8 >= textLength)
                break;
        }
        return blocksAsString;
    }

    private void showInstructions(String plainText, int numberOfBlocks) {
        System.out.println("\n --------------------------------------->");
        System.out.println(String.format("You encrypted text has %d blocks of data", numberOfBlocks));
        System.out.println("Insert the format in which you want the attack to be executed");
        System.out.println("=== EXAMPLE FOR 5 BLOCKS ===");
        System.out.println("1 4 3 2 0");
        System.out.println("--: if you want to delete a block don't insert it in the format");

        System.out.println("========== YOUR BLOCKS =======");
        LinkedList<String> blocks = this.getBlocks(plainText);
        int i = 0;
        for (String block : blocks) {
            System.out.println(" === C" + i + " : " + block + " === ");
            i++;
        }
        System.out.println("<--------------------------------------\n");
    }

    private int[] parseInput(String input) {
        String[] blocks = input.split(" ");
        int[] blocksAsInts = new int[blocks.length];
        for (int i = 0; i<blocks.length; i++) {
            blocksAsInts[i] = Integer.parseInt(blocks[i]);
        }
        return blocksAsInts;
    }

    public byte[] executeAttack(String plainText, byte[] encryptedText) throws IOException {
        int numberOfBlocks = (int) Math.ceil((double) encryptedText.length / 8.0);
        this.showInstructions(plainText, numberOfBlocks);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputFormat = br.readLine();
        int[] indexes = this.parseInput(inputFormat);

        byte[] newBlockFormat = new byte[encryptedText.length];
        int j = 0;

        for (int index: indexes) {
            for (int i = index*8; i < index*8+8; i++){
                if (i >= 0 && i < encryptedText.length){
                    newBlockFormat[j] = encryptedText[i];
                    j++;
                }
            }
        }

        return newBlockFormat;
    }
}
