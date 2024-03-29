package org.aimanj.tx;

/**
 * Matrix chain ids as per
 * <a href="https://github.com/matrix/EIPs/blob/master/EIPS/eip-155.md">EIP-155</a>.
 */
public class ChainId {
    public static final byte NONE = -1;
    public static final byte MAINNET = 1;
    public static final byte EXPANSE_MAINNET = 2;
    public static final byte ROPSTEN = 3;
    public static final byte RINKEBY = 4;
    public static final byte ROOTSTOCK_MAINNET = 30;
    public static final byte ROOTSTOCK_TESTNET = 31;
    public static final byte KOVAN = 42;
    public static final byte MATRIX_CLASSIC_MAINNET = 61;
    public static final byte MATRIX_CLASSIC_TESTNET = 62;
}
