package org.aimanj.crypto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.Parameters;

/**
 * Unit tests for {@link MnemonicUtils} utility class.
 */
@RunWith(Parameterized.class)
public class MnemonicUtilsTest {

    /**
     * Path to test vectors generated by the BIP 39 reference implementation. Each
     * test vector include input entropy, mnemonic and seed. The passphrase "TREZOR"
     * is used for all vectors.
     *
     * @see <a href="https://github.com/trezor/python-mnemonic/blob/master/vectors.json">Test vectors</a>
     */
    private static final String SAMPLE_FILE = "build/resources/test/mnemonics/test-vectors.txt";

    /**
     * Loads the test vectors into a in-memory list and feed them one after another to
     * our parameterized tests.
     *
     * @return Collection of test vectors in which each vector is an array containing
     *         initial entropy, expected mnemonic and expected seed.
     * @throws IOException Shouldn't happen!
     */
    @Parameters
    public static Collection<Object[]> data() throws IOException {
        String data = Files.lines(Paths.get(SAMPLE_FILE)).collect(Collectors.joining("\n"));
        String[] each = data.split("###");

        List<Object[]> parameters = new ArrayList<>();
        for (String part : each) {
            parameters.add(part.trim().split("\n"));
        }

        return parameters;
    }

    /**
     * The initial entropy for the current test vector. This entropy should be used
     * to generate mnemonic and seed.
     */
    private byte[] initialEntropy;

    /**
     * Expected mnemonic for the given {@link #initialEntropy}.
     */
    private String mnemonic;

    /**
     * Expected seed based on the calculated {@link #mnemonic} and default passphrase.
     */
    private byte[] seed;

    public MnemonicUtilsTest(String initialEntropy, String mnemonic, String seed) {
        this.initialEntropy = Hex.decode(initialEntropy);
        this.mnemonic = mnemonic;
        this.seed = Hex.decode(seed);
    }

    @Test
    public void generateMnemonicShouldGenerateExpectedMnemonicWords() {
        String actualMnemonic = MnemonicUtils.generateMnemonic(initialEntropy);

        assertEquals(mnemonic, actualMnemonic);
    }

    @Test
    public void generateSeedShouldGenerateExpectedSeeds() {
        byte[] actualSeed = MnemonicUtils.generateSeed(mnemonic, "TREZOR");

        assertArrayEquals(seed, actualSeed);
    }

    @Test
    public void generateEntropyShouldGenerateExpectedEntropy() {
        byte[] actualEntropy = MnemonicUtils.generateEntropy(mnemonic);

        assertArrayEquals(initialEntropy, actualEntropy);
    }
}
