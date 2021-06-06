package digital_signatures;

import java.math.BigInteger;

public class DSAPublicKey {
    BigInteger p;
    BigInteger q;
    BigInteger alpha;
    BigInteger beta;

    public DSAPublicKey(BigInteger p, BigInteger q, BigInteger alpha, BigInteger beta) {
        this.p = p;
        this.q = q;
        this.alpha = alpha;
        this.beta = beta;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getAlpha() {
        return alpha;
    }

    public BigInteger getBeta() {
        return beta;
    }

    @Override
    public String toString() {
        return "DSAPublicKey{" +
                "p=" + p +
                ", q=" + q +
                ", alpha=" + alpha +
                ", beta=" + beta +
                '}';
    }
}
