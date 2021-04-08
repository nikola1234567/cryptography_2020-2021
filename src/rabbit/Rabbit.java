package rabbit;

import java.math.BigInteger;

public class Rabbit {
    RabbitInternalState masterState = new RabbitInternalState();
    RabbitInternalState workState = new RabbitInternalState();

    /*
    * param: key - klucot so koj kje raboti sifruvacot [128 biten]
    * */
    public Rabbit(BigInteger key) {
        int []k = new int[8];

        for (int i = 0; i<8 ; i++) {
            k[i] = HelperUtils.bitExtracted(key, 16, i * 16);
        }

        for (int i = 0; i < 8; i++) {
            this.masterState.xStateVariable[i] =
                    Integer.toUnsignedLong(i%2==0 ? k[(i+1)%8] << 16 | k[i] : k[(i+5)%8] << 16 | k[(i+4)%8]);
            this.masterState.cCounterVariable[i] =
                    Integer.toUnsignedLong(i%2==0 ? k[(i+4)%8] << 16 | k[(i+5)%8] : k[i] << 16 | k[(i+1)%8]);
        }

        this.masterState.fCarryBit = 0l;

        for (int i = 0; i<4; i++) {
            this.nextState(this.masterState);
        }

        for (int i = 0; i<8; i++)
            this.masterState.cCounterVariable[i] ^= this.masterState.xStateVariable[(i+4)%8];

        for (int i = 0; i<8;i++) {
            this.workState.xStateVariable[i] = this.masterState.xStateVariable[i];
            this.workState.cCounterVariable[i] = this.masterState.cCounterVariable[i];
        }
        this.workState.fCarryBit = this.masterState.fCarryBit;
    }

    /*
    * param: iv - inicijaliziracki vektor [64 bita]
    * returns: void - ne vrakja niso, samo ja azurira sostojbata spored iv
    * */
    public void setupIV(BigInteger iv) {
        this.workState.cCounterVariable[0] = this.masterState.cCounterVariable[0] ^ Integer.toUnsignedLong(HelperUtils.bitExtracted(iv, 32, 0));
        this.workState.cCounterVariable[1] = this.masterState.cCounterVariable[1] ^
                (Integer.toUnsignedLong(HelperUtils.bitExtracted(iv, 16, 48)) << 16 | Integer.toUnsignedLong(HelperUtils.bitExtracted(iv, 16, 16)));
        this.workState.cCounterVariable[2] = this.masterState.cCounterVariable[2] ^ Integer.toUnsignedLong(HelperUtils.bitExtracted(iv, 32, 32));
        this.workState.cCounterVariable[3] = this.masterState.cCounterVariable[3] ^
                (Integer.toUnsignedLong(HelperUtils.bitExtracted(iv, 16, 32)) << 16 | Integer.toUnsignedLong(HelperUtils.bitExtracted(iv, 16, 0)));
        this.workState.cCounterVariable[4] = this.masterState.cCounterVariable[4] ^ Integer.toUnsignedLong(HelperUtils.bitExtracted(iv, 32, 0));
        this.workState.cCounterVariable[5] = this.masterState.cCounterVariable[5] ^
                (Integer.toUnsignedLong(HelperUtils.bitExtracted(iv, 16, 48)) << 16 | Integer.toUnsignedLong(HelperUtils.bitExtracted(iv, 16, 16)));
        this.workState.cCounterVariable[6] = this.masterState.cCounterVariable[6] ^ Integer.toUnsignedLong(HelperUtils.bitExtracted(iv, 32, 32));
        this.workState.cCounterVariable[7] = this.masterState.cCounterVariable[7] ^
                (Integer.toUnsignedLong(HelperUtils.bitExtracted(iv, 16, 32)) << 16 | Integer.toUnsignedLong(HelperUtils.bitExtracted(iv, 16, 0)));

        for (int i = 0; i<8; i++)
            this.workState.xStateVariable[i] = this.masterState.xStateVariable[i];
        this.workState.fCarryBit = this.masterState.fCarryBit;

        for (int i = 0; i<4; i++)
            nextState(workState);
    }

    /*
    * param: message - porakata sto sakame da ja enkriptirame
    * param: msglen - dolzina na porakata za enkriptiranje
    * returns: void - ne vrakja nisto, go pecati enkriptiraniot tekst
    * */
    public void encryptText(BigInteger message, int msglen) {

        int safeCopyForLen = msglen;
        int i = 0;
        BigInteger encrypted = BigInteger.valueOf(0);

        while (safeCopyForLen > 0) {

            this.nextState(this.workState);

            BigInteger messageBlock = HelperUtils.messageBlockExtraction(message, 128, i*128);
            BigInteger keystream = this.getCurrentKeystream();
            System.out.println("KEYSTREAM: " + keystream.toString(16));
            System.out.println("BLOCK: " + messageBlock.toString(2));
            System.out.println("ENCRYPT BLOCK: " + messageBlock.xor(keystream).toString(2));
            System.out.println("===============================================================");
            encrypted = encrypted.shiftLeft(128).or(messageBlock.xor(keystream));

            i++;
            safeCopyForLen -= 128;
        }
        System.out.println(encrypted.toString(16));
    }

    /*
    * param: state - sostojbata koja kje ja azurirame [master/work]
    * returns: void - ne vrakja nisto, samo ja azurira sostojbata
    * */
    private void nextState(RabbitInternalState state) {
        Long g[] = new Long[8];
        Long cOld[] = new Long[8];

        for (int i =0; i<8; i++) {
            cOld[i] = state.cCounterVariable[i];
        }

        state.cCounterVariable[0] = HelperUtils.shrinkTo32Bits(state.cCounterVariable[0] + 0x4D34D34D + state.fCarryBit);
        state.cCounterVariable[1] = HelperUtils.shrinkTo32Bits(state.cCounterVariable[1] + 0xD34D34D3 + (state.cCounterVariable[0] < cOld[0] ? 1 : 0));
        state.cCounterVariable[2] = HelperUtils.shrinkTo32Bits(state.cCounterVariable[2] + 0x34D34D34 + (state.cCounterVariable[1] < cOld[1] ? 1 : 0));
        state.cCounterVariable[3] = HelperUtils.shrinkTo32Bits(state.cCounterVariable[3] + 0x4D34D34D + (state.cCounterVariable[2] < cOld[2] ? 1 : 0));
        state.cCounterVariable[4] = HelperUtils.shrinkTo32Bits(state.cCounterVariable[4] + 0xD34D34D3 + (state.cCounterVariable[3] < cOld[3] ? 1 : 0));
        state.cCounterVariable[5] = HelperUtils.shrinkTo32Bits(state.cCounterVariable[5] + 0x34D34D34 + (state.cCounterVariable[4] < cOld[4] ? 1 : 0));
        state.cCounterVariable[6] = HelperUtils.shrinkTo32Bits(state.cCounterVariable[6] + 0x4D34D34D + (state.cCounterVariable[5] < cOld[5] ? 1 : 0));
        state.cCounterVariable[7] = HelperUtils.shrinkTo32Bits(state.cCounterVariable[7] + 0xD34D34D3 + (state.cCounterVariable[6] < cOld[6] ? 1 : 0));
        state.fCarryBit = (state.cCounterVariable[7] < cOld[7] ? 1l : 0l);

        for (int i = 0; i < 8 ; i++) {
            g[i] = this.applyGFunction(HelperUtils.shrinkTo32Bits(state.xStateVariable[i] + state.cCounterVariable[i]));
        }

        state.xStateVariable[0] = HelperUtils.shrinkTo32Bits(g[0] + Long.rotateLeft(g[7],16) + Long.rotateLeft(g[6], 16));
        state.xStateVariable[1] = HelperUtils.shrinkTo32Bits(g[1] + Long.rotateLeft(g[0], 8) + g[7]);
        state.xStateVariable[2] = HelperUtils.shrinkTo32Bits(g[2] + Long.rotateLeft(g[1],16) + Long.rotateLeft(g[0], 16));
        state.xStateVariable[3] = HelperUtils.shrinkTo32Bits(g[3] + Long.rotateLeft(g[2], 8) + g[1]);
        state.xStateVariable[4] = HelperUtils.shrinkTo32Bits(g[4] + Long.rotateLeft(g[3],16) + Long.rotateLeft(g[2], 16));
        state.xStateVariable[5] = HelperUtils.shrinkTo32Bits(g[5] + Long.rotateLeft(g[4], 8) + g[3]);
        state.xStateVariable[6] = HelperUtils.shrinkTo32Bits(g[6] + Long.rotateLeft(g[5],16) + Long.rotateLeft(g[4], 16));
        state.xStateVariable[7] = HelperUtils.shrinkTo32Bits(g[7] + Long.rotateLeft(g[6], 8) + g[5]);
    }

    /*
    * returns: BigInteger - protokot na bitovi genereirani od tekovnata rabotna sostojba vo forma na cel broj
    * */
    private BigInteger getCurrentKeystream() {
        BigInteger s = BigInteger.valueOf(0);

        int s127_112 = HelperUtils.bitExtracted(BigInteger.valueOf(this.workState.xStateVariable[6]), 16, 16)
                ^ HelperUtils.bitExtracted(BigInteger.valueOf(this.workState.xStateVariable[1]), 16, 0);
        s = s.shiftLeft(16).or(BigInteger.valueOf(s127_112));

        int s111_96 = HelperUtils.bitExtracted(BigInteger.valueOf(this.workState.xStateVariable[6]), 16, 0)
                ^ HelperUtils.bitExtracted(BigInteger.valueOf(this.workState.xStateVariable[3]), 16, 16);
        s = s.shiftLeft(16).or(BigInteger.valueOf(s111_96));

        int s95_80 = HelperUtils.bitExtracted(BigInteger.valueOf(this.workState.xStateVariable[4]), 16, 16)
                ^ HelperUtils.bitExtracted(BigInteger.valueOf(this.workState.xStateVariable[7]), 16, 0);
        s = s.shiftLeft(16).or(BigInteger.valueOf(s95_80));

        int s79_64 = HelperUtils.bitExtracted(BigInteger.valueOf(this.workState.xStateVariable[4]), 16, 0)
                ^ HelperUtils.bitExtracted(BigInteger.valueOf(this.workState.xStateVariable[1]), 16, 16);
        s = s.shiftLeft(16).or(BigInteger.valueOf(s79_64));

        int s63_48 = HelperUtils.bitExtracted(BigInteger.valueOf(this.workState.xStateVariable[2]), 16, 16)
                ^ HelperUtils.bitExtracted(BigInteger.valueOf(this.workState.xStateVariable[5]), 16, 0);
        s = s.shiftLeft(16).or(BigInteger.valueOf(s63_48));

        int s47_32 = HelperUtils.bitExtracted(BigInteger.valueOf(this.workState.xStateVariable[2]), 16, 0)
                ^ HelperUtils.bitExtracted(BigInteger.valueOf(this.workState.xStateVariable[7]), 16, 16);
        s = s.shiftLeft(16).or(BigInteger.valueOf(s47_32));

        int s16_31 = HelperUtils.bitExtracted(BigInteger.valueOf(this.workState.xStateVariable[0]), 16, 16)
                ^ HelperUtils.bitExtracted(BigInteger.valueOf(this.workState.xStateVariable[3]), 16, 0);
        s = s.shiftLeft(16).or(BigInteger.valueOf(s16_31));

        int s0_15 = HelperUtils.bitExtracted(BigInteger.valueOf(this.workState.xStateVariable[0]), 16, 0)
                ^ HelperUtils.bitExtracted(BigInteger.valueOf(this.workState.xStateVariable[5]), 16, 16);
        s = s.shiftLeft(16).or(BigInteger.valueOf(s0_15));

        return s;
    }

    /*
    * param: xSumC - max 64 biten broj
    * returns: long - ja vrakja slednata transformacija na vlezot:
    *           (xSumC)^2 ⊕ ((xSumC)^2 >> 32) mod 2^32
    * */
    private Long applyGFunction(long xSumC) {
        BigInteger bigInteger1 = BigInteger.valueOf(xSumC);
        BigInteger bigInteger2 = BigInteger.valueOf(xSumC);
        BigInteger product = bigInteger1.multiply(bigInteger2);

        BigInteger highBits = product.shiftRight(32);
        BigInteger lowBits = product.and(new BigInteger("11111111111111111111111111111111", 2));
        BigInteger result = lowBits.xor(highBits);

        return result.longValueExact();
    }


}
