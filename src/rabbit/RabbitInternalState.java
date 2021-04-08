package rabbit;

public class RabbitInternalState {
    Long[] xStateVariable = new Long[8];
    Long[] cCounterVariable = new Long[8];
    Long fCarryBit;

    public RabbitInternalState() {}
}
