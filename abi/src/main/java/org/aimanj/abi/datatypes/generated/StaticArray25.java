package org.aimanj.abi.datatypes.generated;

import java.util.List;
import org.aimanj.abi.datatypes.StaticArray;
import org.aimanj.abi.datatypes.Type;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.aimanj.codegen.AbiTypesGenerator in the
 * <a href="https://github.com/aiManj/aiManj/tree/master/codegen">codegen module</a> to update.
 */
public class StaticArray25<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray25(List<T> values) {
        super(25, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray25(T... values) {
        super(25, values);
    }

    public StaticArray25(Class<T> type, List<T> values) {
        super(type, 25, values);
    }

    @SafeVarargs
    public StaticArray25(Class<T> type, T... values) {
        super(type, 25, values);
    }
}
