package org.aimanj.abi.datatypes.generated;

import java.math.BigInteger;
import org.aimanj.abi.datatypes.Uint;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.aimanj.codegen.AbiTypesGenerator in the
 * <a href="https://github.com/aiManj/aiManj/tree/master/codegen">codegen module</a> to update.
 */
public class Uint48 extends Uint {
    public static final Uint48 DEFAULT = new Uint48(BigInteger.ZERO);

    public Uint48(BigInteger value) {
        super(48, value);
    }

    public Uint48(long value) {
        this(BigInteger.valueOf(value));
    }
}
