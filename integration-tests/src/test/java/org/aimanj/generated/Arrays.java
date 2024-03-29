package org.aimanj.generated;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.Callable;
import org.aimanj.abi.TypeReference;
import org.aimanj.abi.datatypes.Address;
import org.aimanj.abi.datatypes.DynamicArray;
import org.aimanj.abi.datatypes.Function;
import org.aimanj.abi.datatypes.Type;
import org.aimanj.abi.datatypes.generated.StaticArray10;
import org.aimanj.abi.datatypes.generated.Uint256;
import org.aimanj.crypto.Credentials;
import org.aimanj.protocol.aiManj;
import org.aimanj.protocol.core.RemoteCall;
import org.aimanj.tx.Contract;
import org.aimanj.tx.TransactionManager;
import org.aimanj.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.aiManj.io/command_line.html">aimanj command line tools</a>,
 * or the org.aimanj.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/aiManj/aiManj/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with aimanj version 4.0.1.
 */
public class Arrays extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50610546806100206000396000f30060806040526004361061006c5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416633a69db9481146100715780633cac14c814610149578063a30cc5f61461015e578063b96f54d1146101d2578063beda363b14610250575b600080fd5b34801561007d57600080fd5b506040805160048035808201356020818102850181019095528084526100f9943694602493909290840191819060009085015b828210156100e95760408051808201825290808402870190600290839083908082843750505091835250506001909101906020016100b0565b509396506102a595505050505050565b60408051602080825283518183015283519192839290830191858101910280838360005b8381101561013557818101518382015260200161011d565b505050509050019250505060405180910390f35b34801561015557600080fd5b506100f9610371565b34801561016a57600080fd5b506040805160c081019091526100f9903690600490610184908260066000835b828210156101c357604080518082018252908084028601906002908390839080828437505050918352505060019091019060200161018a565b50929550610383945050505050565b3480156101de57600080fd5b506040805161014081810190925261021791369160049161014491908390600a9083908390808284375093965061042e95505050505050565b604051808261014080838360005b8381101561023d578181015183820152602001610225565b5050505090500191505060405180910390f35b34801561025c57600080fd5b50604080516020600480358082013583810280860185019096528085526100f99536959394602494938501929182918501908490808284375094975061047a9650505050505050565b6060600080600084519250826002026040519080825280602002602001820160405280156102dd578160200160208202803883390190505b50935060009150600090505b828110156103695784818151811015156102ff57fe5b602090810291909101015151845185908490811061031957fe5b60209081029091010152845160019092019185908290811061033757fe5b6020908102919091018101510151845185908490811061035357fe5b60209081029091010152600191820191016102e9565b505050919050565b60408051600081526020810190915290565b60408051600c8082526101a08201909252606091600691600091829190602082016101808038833901905050935060009150600090505b82811015610369578481600681106103ce57fe5b60200201515184518590849081106103e257fe5b602090810290910101526001909101908481600681106103fe57fe5b602002015160016020020151848381518110151561041857fe5b60209081029091010152600191820191016103ba565b6104366104fa565b600a60005b81811015610473578360001982840301600a811061045557fe5b60200201518382600a811061046657fe5b602002015260010161043b565b5050919050565b606060008083519150816040519080825280602002602001820160405280156104ad578160200160208202803883390190505b509250600090505b8181101561047357838160010183038151811015156104d057fe5b9060200190602002015183828151811015156104e857fe5b602090810290910101526001016104b5565b61014060405190810160405280600a9060208202803883395091929150505600a165627a7a7230582021d6572865cb6696eb46b3a3fbf295406269d806e9d7949682a800ea387474610029";

    public static final String FUNC_MULTIDYNAMIC = "multiDynamic";

    public static final String FUNC_RETURNARRAY = "returnArray";

    public static final String FUNC_MULTIFIXED = "multiFixed";

    public static final String FUNC_FIXEDREVERSE = "fixedReverse";

    public static final String FUNC_DYNAMICREVERSE = "dynamicReverse";

    @Deprecated
    protected Arrays(String contractAddress, aiManj aiManj, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, aiManj, credentials, gasPrice, gasLimit);
    }

    protected Arrays(String contractAddress, aiManj aiManj, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, aiManj, credentials, contractGasProvider);
    }

    @Deprecated
    protected Arrays(String contractAddress, aiManj aiManj, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, aiManj, transactionManager, gasPrice, gasLimit);
    }

    protected Arrays(String contractAddress, aiManj aiManj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, aiManj, transactionManager, contractGasProvider);
    }

    public RemoteCall<List> multiDynamic(List<List<BigInteger>> input) {
        final Function function = new Function(FUNC_MULTIDYNAMIC, 
                java.util.Arrays.<Type>asList(new org.aimanj.abi.datatypes.DynamicArray<org.aimanj.abi.datatypes.generated.StaticArray2>(
                        org.aimanj.abi.datatypes.generated.StaticArray2.class,
                        org.aimanj.abi.Utils.typeMap(input, org.aimanj.abi.datatypes.generated.StaticArray2.class,
                org.aimanj.abi.datatypes.generated.Uint256.class))),
                java.util.Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<List> returnArray() {
        final Function function = new Function(FUNC_RETURNARRAY, 
                java.util.Arrays.<Type>asList(), 
                java.util.Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<List> multiFixed(List<List<BigInteger>> input) {
        final Function function = new Function(FUNC_MULTIFIXED, 
                java.util.Arrays.<Type>asList(new org.aimanj.abi.datatypes.generated.StaticArray6<org.aimanj.abi.datatypes.generated.StaticArray2>(
                        org.aimanj.abi.datatypes.generated.StaticArray2.class,
                        org.aimanj.abi.Utils.typeMap(input, org.aimanj.abi.datatypes.generated.StaticArray2.class,
                org.aimanj.abi.datatypes.generated.Uint256.class))),
                java.util.Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<List> fixedReverse(List<BigInteger> input) {
        final Function function = new Function(FUNC_FIXEDREVERSE, 
                java.util.Arrays.<Type>asList(new org.aimanj.abi.datatypes.generated.StaticArray10<org.aimanj.abi.datatypes.generated.Uint256>(
                        org.aimanj.abi.datatypes.generated.Uint256.class,
                        org.aimanj.abi.Utils.typeMap(input, org.aimanj.abi.datatypes.generated.Uint256.class))),
                java.util.Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray10<Uint256>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<List> dynamicReverse(List<BigInteger> input) {
        final Function function = new Function(FUNC_DYNAMICREVERSE, 
                java.util.Arrays.<Type>asList(new org.aimanj.abi.datatypes.DynamicArray<org.aimanj.abi.datatypes.generated.Uint256>(
                        org.aimanj.abi.datatypes.generated.Uint256.class,
                        org.aimanj.abi.Utils.typeMap(input, org.aimanj.abi.datatypes.generated.Uint256.class))),
                java.util.Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public static RemoteCall<Arrays> deploy(aiManj aiManj, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Arrays.class, aiManj, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Arrays> deploy(aiManj aiManj, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Arrays.class, aiManj, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Arrays> deploy(aiManj aiManj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Arrays.class, aiManj, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Arrays> deploy(aiManj aiManj, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Arrays.class, aiManj, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static Arrays load(String contractAddress, aiManj aiManj, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Arrays(contractAddress, aiManj, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Arrays load(String contractAddress, aiManj aiManj, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Arrays(contractAddress, aiManj, transactionManager, gasPrice, gasLimit);
    }

    public static Arrays load(String contractAddress, aiManj aiManj, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Arrays(contractAddress, aiManj, credentials, contractGasProvider);
    }

    public static Arrays load(String contractAddress, aiManj aiManj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Arrays(contractAddress, aiManj, transactionManager, contractGasProvider);
    }
}
