package org.aimanj.protocol.core;

import java.math.BigInteger;
import java.util.Arrays;

import org.aimanj.abi.EventEncoder;
import org.aimanj.abi.TypeReference;
import org.aimanj.abi.datatypes.Event;
import org.aimanj.abi.datatypes.Uint;
import org.aimanj.protocol.core.methods.request.Transaction;

/**
 * Mordon Testnet Configuration.
 */
public class TestnetConfig implements IntegrationTestConfig {

    @Override
    public String validBlockHash() {
        https://jerry.matrix.io/block/1627453
        return "0xd67e59db999c3bd78bd4c2ba54689dba0c372ebcad09c8b9677970f37d64ca46";
    }

    @Override
    public BigInteger validBlock() {
        // https://jerry.matrix.io/block/71032
        return BigInteger.valueOf(71032);
    }

    @Override
    public BigInteger validBlockTransactionCount() {
        return BigInteger.valueOf(3);
    }

    @Override
    public BigInteger validBlockUncleCount() {
        return BigInteger.ZERO;
    }

    @Override
    public String validAccount() {
        // https://jerry.matrix.io/address/0xCB10FBad79F5e602699fFf2Bb4919Fbd87AbC8CC
        return "0xcb10fbad79f5e602699fff2bb4919fbd87abc8cc";
    }

    @Override
    public String validContractAddress() {
        // Deployed fibonacci example
        return "0x3c05b2564139fb55820b18b72e94b2178eaace7d";
    }

    @Override
    public String validContractAddressPositionZero() {
        return "0x0000000000000000000000000000000000000000000000000000000000000000";
    }

    @Override
    public String validContractCode() {
        return "0x";
    }

    @Override
    public Transaction buildTransaction() {
        return null;
    }

    @Override
    public String validTransactionHash() {
        return "0xf26d441775da4e01cb557dfe35e09ab8c8a69134b2687209e34348c11ae54509";
    }

    @Override
    public String validUncleBlockHash() {
        return "0x9d512dd0cad173dd3e7ec568794db03541c4a98448cc5940b695da604d118754";
    }

    @Override
    public BigInteger validUncleBlock() {
        return BigInteger.valueOf(1640092);
    }

    @Override
    public String encodedEvent() {
        Event event = new Event("Notify",
                Arrays.asList(
                        new TypeReference<Uint>(true) {},
                        new TypeReference<Uint>() {}));

        return EventEncoder.encode(event);
    }
}
