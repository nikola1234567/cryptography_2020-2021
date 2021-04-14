package rabbit;

import java.math.BigInteger;

public class Rabbit {
    RabbitInternalState masterState = new RabbitInternalState();
    RabbitInternalState workState = new RabbitInternalState();

    /*
    * param: key - klucot so koj kje raboti sifruvacot [128 biten]
    * */
    public Rabbit(BigInteger key) {
        BigInteger []k = new BigInteger[8];

        for (int i = 0; i<8 ; i++) {
            k[i] = HelperUtils.bitExtracted(key, 16, i * 16);
        }

        for (int i = 0; i < 8; i++) {
            this.masterState.xStateVariable[i] = i%2==0 ? k[(i+1)%8].shiftLeft(16).or(k[i]) : k[(i+5)%8].shiftLeft(16).or( k[(i+4)%8]);
            this.masterState.cCounterVariable[i] = i%2==0 ? k[(i+4)%8].shiftLeft(16).or(k[(i+5)%8]) : k[i].shiftLeft(16).or(k[(i+1)%8]);
        }

        this.masterState.fCarryBit = BigInteger.valueOf(0l);

        for (int i = 0; i<4; i++) {
            this.nextState(this.masterState);
        }

        for (int i = 0; i<8; i++)
            this.masterState.cCounterVariable[i] = this.masterState.cCounterVariable[i].xor(this.masterState.xStateVariable[(i+4)%8]);

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
        this.workState.cCounterVariable[0] = this.masterState.cCounterVariable[0].xor(HelperUtils.bitExtracted(iv, 32, 0));
        this.workState.cCounterVariable[1] = this.masterState.cCounterVariable[1].xor(
                (HelperUtils.bitExtracted(iv, 16, 48).shiftLeft(16).or(HelperUtils.bitExtracted(iv, 16, 16))));
        this.workState.cCounterVariable[2] = this.masterState.cCounterVariable[2].xor(HelperUtils.bitExtracted(iv, 32, 32));
        this.workState.cCounterVariable[3] = this.masterState.cCounterVariable[3].xor(
                (HelperUtils.bitExtracted(iv, 16, 32).shiftLeft(16).or(HelperUtils.bitExtracted(iv, 16, 0))));
        this.workState.cCounterVariable[4] = this.masterState.cCounterVariable[4].xor(HelperUtils.bitExtracted(iv, 32, 0));
        this.workState.cCounterVariable[5] = this.masterState.cCounterVariable[5].xor(
                (HelperUtils.bitExtracted(iv, 16, 48).shiftLeft(16).or(HelperUtils.bitExtracted(iv, 16, 16))));
        this.workState.cCounterVariable[6] = this.masterState.cCounterVariable[6].xor(HelperUtils.bitExtracted(iv, 32, 32));
        this.workState.cCounterVariable[7] = this.masterState.cCounterVariable[7].xor(
                (HelperUtils.bitExtracted(iv, 16, 32).shiftLeft(16).or(HelperUtils.bitExtracted(iv, 16, 0))));

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

            BigInteger messageBlock = HelperUtils.bitExtracted(message, 128, i*128);
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
        BigInteger g[] = new BigInteger[8];
        BigInteger cOld[] = new BigInteger[8];

        for (int i =0; i<8; i++) {
            cOld[i] = state.cCounterVariable[i];
        }

        state.cCounterVariable[0] = HelperUtils.shrinkTo32Bits(state.cCounterVariable[0].add(new BigInteger("4D34D34D", 16)).add(state.fCarryBit));
        state.cCounterVariable[1] = HelperUtils.shrinkTo32Bits(state.cCounterVariable[1].add(new BigInteger("D34D34D3", 16)).add(state.cCounterVariable[0].compareTo(cOld[0]) < 0 ? BigInteger.valueOf(1l) : BigInteger.valueOf(0l)));
        state.cCounterVariable[2] = HelperUtils.shrinkTo32Bits(state.cCounterVariable[2].add(new BigInteger("34D34D34", 16)).add(state.cCounterVariable[1].compareTo(cOld[1]) < 0 ? BigInteger.valueOf(1l) : BigInteger.valueOf(0l)));
        state.cCounterVariable[3] = HelperUtils.shrinkTo32Bits(state.cCounterVariable[3].add(new BigInteger("4D34D34D", 16)).add(state.cCounterVariable[2].compareTo(cOld[2]) < 0 ? BigInteger.valueOf(1l) : BigInteger.valueOf(0l)));
        state.cCounterVariable[4] = HelperUtils.shrinkTo32Bits(state.cCounterVariable[4].add(new BigInteger("D34D34D3", 16)).add(state.cCounterVariable[3].compareTo(cOld[3]) < 0 ? BigInteger.valueOf(1l) : BigInteger.valueOf(0l)));
        state.cCounterVariable[5] = HelperUtils.shrinkTo32Bits(state.cCounterVariable[5].add(new BigInteger("34D34D34", 16)).add(state.cCounterVariable[4].compareTo(cOld[4]) < 0 ? BigInteger.valueOf(1l) : BigInteger.valueOf(0l)));
        state.cCounterVariable[6] = HelperUtils.shrinkTo32Bits(state.cCounterVariable[6].add(new BigInteger("4D34D34D", 16)).add(state.cCounterVariable[5].compareTo(cOld[5]) < 0 ? BigInteger.valueOf(1l) : BigInteger.valueOf(0l)));
        state.cCounterVariable[7] = HelperUtils.shrinkTo32Bits(state.cCounterVariable[7].add(new BigInteger("D34D34D3", 16)).add(state.cCounterVariable[6].compareTo(cOld[6]) < 0 ? BigInteger.valueOf(1l) : BigInteger.valueOf(0l)));
        state.fCarryBit = (state.cCounterVariable[7].compareTo(cOld[7]) < 0 ? BigInteger.valueOf(1l) : BigInteger.valueOf(0l));

        for (int i = 0; i < 8 ; i++) {
            g[i] = this.applyGFunction(HelperUtils.shrinkTo32Bits(state.xStateVariable[i].add(state.cCounterVariable[i])));
        }

        state.xStateVariable[0] = HelperUtils.shrinkTo32Bits(g[0].add(BigInteger.valueOf(Long.rotateLeft(g[7].longValueExact(),16))).add(BigInteger.valueOf(Long.rotateLeft(g[6].longValueExact(), 16))));
        state.xStateVariable[1] = HelperUtils.shrinkTo32Bits(g[1].add(BigInteger.valueOf(Long.rotateLeft(g[0].longValueExact(), 8))).add(g[7]));
        state.xStateVariable[2] = HelperUtils.shrinkTo32Bits(g[2].add(BigInteger.valueOf(Long.rotateLeft(g[1].longValueExact(),16))).add(BigInteger.valueOf(Long.rotateLeft(g[0].longValueExact(), 16))));
        state.xStateVariable[3] = HelperUtils.shrinkTo32Bits(g[3].add(BigInteger.valueOf(Long.rotateLeft(g[2].longValueExact(), 8))).add(g[1]));
        state.xStateVariable[4] = HelperUtils.shrinkTo32Bits(g[4].add(BigInteger.valueOf(Long.rotateLeft(g[3].longValueExact(),16))).add(BigInteger.valueOf(Long.rotateLeft(g[2].longValueExact(), 16))));
        state.xStateVariable[5] = HelperUtils.shrinkTo32Bits(g[5].add(BigInteger.valueOf(Long.rotateLeft(g[4].longValueExact(), 8))).add(g[3]));
        state.xStateVariable[6] = HelperUtils.shrinkTo32Bits(g[6].add(BigInteger.valueOf(Long.rotateLeft(g[5].longValueExact(),16))).add(BigInteger.valueOf(Long.rotateLeft(g[4].longValueExact(), 16))));
        state.xStateVariable[7] = HelperUtils.shrinkTo32Bits(g[7].add(BigInteger.valueOf(Long.rotateLeft(g[6].longValueExact(), 8))).add(g[5]));
    }

    /*
    * returns: BigInteger - protokot na bitovi genereirani od tekovnata rabotna sostojba vo forma na cel broj
    * */
    private BigInteger getCurrentKeystream() {
        BigInteger s = BigInteger.valueOf(0);

        BigInteger s127_112 = HelperUtils.bitExtracted(this.workState.xStateVariable[6], 16, 16).xor(
                HelperUtils.bitExtracted(this.workState.xStateVariable[1], 16, 0));
        s = s.shiftLeft(16).or(s127_112);

        BigInteger s111_96 = HelperUtils.bitExtracted(this.workState.xStateVariable[6], 16, 0).xor(
                 HelperUtils.bitExtracted(this.workState.xStateVariable[3], 16, 16));
        s = s.shiftLeft(16).or(s111_96);

        BigInteger s95_80 = HelperUtils.bitExtracted(this.workState.xStateVariable[4], 16, 16).xor(
                 HelperUtils.bitExtracted(this.workState.xStateVariable[7], 16, 0));
        s = s.shiftLeft(16).or(s95_80);

        BigInteger s79_64 = HelperUtils.bitExtracted(this.workState.xStateVariable[4], 16, 0).xor(
                 HelperUtils.bitExtracted(this.workState.xStateVariable[1], 16, 16));
        s = s.shiftLeft(16).or(s79_64);

        BigInteger s63_48 = HelperUtils.bitExtracted(this.workState.xStateVariable[2], 16, 16).xor(
                 HelperUtils.bitExtracted(this.workState.xStateVariable[5], 16, 0));
        s = s.shiftLeft(16).or(s63_48);

        BigInteger s47_32 = HelperUtils.bitExtracted(this.workState.xStateVariable[2], 16, 0).xor(
                 HelperUtils.bitExtracted(this.workState.xStateVariable[7], 16, 16));
        s = s.shiftLeft(16).or(s47_32);

        BigInteger s16_31 = HelperUtils.bitExtracted(this.workState.xStateVariable[0], 16, 16).xor(
                 HelperUtils.bitExtracted(this.workState.xStateVariable[3], 16, 0));
        s = s.shiftLeft(16).or(s16_31);

        BigInteger s0_15 = HelperUtils.bitExtracted(this.workState.xStateVariable[0], 16, 0).xor(
                 HelperUtils.bitExtracted(this.workState.xStateVariable[5], 16, 16));
        s = s.shiftLeft(16).or(s0_15);

        return s;
    }

    /*
    * param: xSumC - max 64 biten broj
    * returns: BigInteger - ja vrakja slednata transformacija na vlezot:
    *           (xSumC)^2 ⊕ ((xSumC)^2 >> 32) mod 2^32
    * */
    private BigInteger applyGFunction(BigInteger xSumC) {
        BigInteger bigInteger1 = xSumC;
        BigInteger bigInteger2 = xSumC;
        BigInteger product = bigInteger1.multiply(bigInteger2);

        BigInteger highBits = product.shiftRight(32);
        BigInteger lowBits = product.and(new BigInteger("11111111111111111111111111111111", 2));
        BigInteger result = lowBits.xor(highBits);

        return HelperUtils.shrinkTo32Bits(result);
    }


}
