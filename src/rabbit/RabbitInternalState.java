package rabbit;

import java.math.BigInteger;

public class RabbitInternalState {
    BigInteger[] xStateVariable = new BigInteger[8];
    BigInteger[] cCounterVariable = new BigInteger[8];
    BigInteger fCarryBit;

    public RabbitInternalState() {}
}
