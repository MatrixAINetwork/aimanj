package org.aimanj.protocol.scenarios;

import java.math.BigInteger;

import org.junit.Test;

import org.aimanj.generated.SimpleStorage;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class SimpleStorageContractIT extends Scenario {

    @Test
    public void testSimpleStorageContract() throws Exception {
        BigInteger value = BigInteger.valueOf(1000L);
        SimpleStorage simpleStorage = SimpleStorage.deploy(
                aiManj, ALICE, GAS_PRICE, GAS_LIMIT).send();
        assertNotNull(simpleStorage.set(value).send());
        assertThat(simpleStorage.get().send(), is(value));
    }
}
