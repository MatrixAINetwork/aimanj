package org.aimanj.protocol.core.methods.response;

import java.math.BigInteger;
import java.util.List;

import org.aimanj.utils.Numeric;

/**
 * Transaction object used by both {@link ManTransaction} and {@link ManBlock}.
 */
public class Transaction {
    private static final int CHAIN_ID_INC = 35;
    private static final int LOWER_REAL_V = 27;

    private String hash;
    private String nonce;
    private String blockHash;
    private String blockNumber;
    private String transactionIndex;
    private String from;
    private String to;
    private String value;
    private String gasPrice;
    private String gas;
    private String input;
//    private String creates;
//    private String publicKey;
//    private String raw;
    private String r;
    private String s;
    private long v;  // see https://github.com/aiManj/aiManj/issues/44
    public String Currency;
    public BigInteger TxEnterType;
    public String CommitTime;
    public Boolean IsEntrustTx;
    private int matrixType;
    private List<Object> extra_to;

    public Transaction() {
    }

    public Transaction(String hash, String nonce, String blockHash, String blockNumber,
                       String transactionIndex, String from, String to, String value,
                       String gas, String gasPrice, String input,
                       String publicKey, String r, String s, long v, String Currency,
                       BigInteger TxEnterType, String CommitTime, Boolean IsEntrustTx, int matrixType, List extra_to) {
        this.hash = hash;
        this.nonce = nonce;
        this.blockHash = blockHash;
        this.blockNumber = blockNumber;
        this.transactionIndex = transactionIndex;
        this.from = from;
        this.to = to;
        this.value = value;
        this.gasPrice = gasPrice;
        this.gas = gas;
        this.input = input;
//        this.creates = creates;
//        this.publicKey = publicKey;
//        this.raw = raw;
        this.r = r;
        this.s = s;
        this.v = v;
        this.Currency = Currency;
        this.TxEnterType = TxEnterType;
        this.CommitTime = CommitTime;
        this.IsEntrustTx = IsEntrustTx;
        this.matrixType = matrixType;
        this.extra_to = extra_to;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public BigInteger getNonce() {
        return Numeric.decodeQuantity(nonce);
    }

    public String getNonceRaw() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public BigInteger getBlockNumber() {
        return Numeric.decodeQuantity(blockNumber);
    }

    public String getBlockNumberRaw() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public BigInteger getTransactionIndex() {
        return Numeric.decodeQuantity(transactionIndex);
    }

    public String getTransactionIndexRaw() {
        return transactionIndex;
    }

    public void setTransactionIndex(String transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigInteger getValue() {
        return Numeric.decodeQuantity(value);
    }

    public String getValueRaw() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BigInteger getGasPrice() {
        return Numeric.decodeQuantity(gasPrice);
    }

    public String getGasPriceRaw() {
        return gasPrice;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    public BigInteger getGas() {
        return Numeric.decodeQuantity(gas);
    }

    public String getGasRaw() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
//
//    public String getCreates() {
//        return creates;
//    }
//
//    public void setCreates(String creates) {
//        this.creates = creates;
//    }

//    public String getPublicKey() {
//        return publicKey;
//    }
//
//    public void setPublicKey(String publicKey) {
//        this.publicKey = publicKey;
//    }
//
//    public String getRaw() {
//        return raw;
//    }
//
//    public void setRaw(String raw) {
//        this.raw = raw;
//    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public long getV() {
        return v;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public BigInteger getTxEnterType() {
        return TxEnterType;
    }

    public void setTxEnterType(BigInteger txEnterType) {
        TxEnterType = txEnterType;
    }

    public String getCommitTime() {
        return CommitTime;
    }

    public void setCommitTime(String commitTime) {
        CommitTime = commitTime;
    }

    public Boolean getEntrustTx() {
        return IsEntrustTx;
    }

    public void setEntrustTx(Boolean entrustTx) {
        IsEntrustTx = entrustTx;
    }

    public int getMatrixType() {
        return matrixType;
    }

    public void setMatrixType(int matrixType) {
        this.matrixType = matrixType;
    }

    public List<Object> getExtra_to() {
        return extra_to;
    }

    public void setExtra_to(List<Object> extra_to) {
        this.extra_to = extra_to;
    }

    public Long getChainId() {
        if (v == LOWER_REAL_V || v == (LOWER_REAL_V + 1)) {
            return null;
        }
        Long chainId = (v - CHAIN_ID_INC) / 2;
        return chainId;
    }

    // public void setV(byte v) {
    //     this.v = v;
    // }

    // Workaround until Gman & Parity return consistent values. At present
    // Parity returns a byte value, Gman returns a hex-encoded string
    // https://github.com/Matrix/go-Matrix/issues/3339
    public void setV(Object v) {
        if (v instanceof String) {
            this.v = Numeric.toBigInt((String) v).longValueExact();
        } else if (v instanceof Integer) {
            this.v = ((Integer) v).longValue();
        } else {
            this.v = (Long) v;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }

        Transaction that = (Transaction) o;

        if (getV() != that.getV()) {
            return false;
        }
        if (getHash() != null ? !getHash().equals(that.getHash()) : that.getHash() != null) {
            return false;
        }
        if (getNonceRaw() != null
                ? !getNonceRaw().equals(that.getNonceRaw()) : that.getNonceRaw() != null) {
            return false;
        }
        if (getBlockHash() != null
                ? !getBlockHash().equals(that.getBlockHash()) : that.getBlockHash() != null) {
            return false;
        }
        if (getBlockNumberRaw() != null
                ? !getBlockNumberRaw().equals(that.getBlockNumberRaw())
                : that.getBlockNumberRaw() != null) {
            return false;
        }
        if (getTransactionIndexRaw() != null
                ? !getTransactionIndexRaw().equals(that.getTransactionIndexRaw())
                : that.getTransactionIndexRaw() != null) {
            return false;
        }
        if (getFrom() != null ? !getFrom().equals(that.getFrom()) : that.getFrom() != null) {
            return false;
        }
        if (getTo() != null ? !getTo().equals(that.getTo()) : that.getTo() != null) {
            return false;
        }
        if (getValueRaw() != null
                ? !getValueRaw().equals(that.getValueRaw()) : that.getValueRaw() != null) {
            return false;
        }
        if (getGasPriceRaw() != null
                ? !getGasPriceRaw().equals(that.getGasPriceRaw()) : that.getGasPriceRaw() != null) {
            return false;
        }
        if (getGasRaw() != null
                ? !getGasRaw().equals(that.getGasRaw()) : that.getGasRaw() != null) {
            return false;
        }
        if (getInput() != null ? !getInput().equals(that.getInput()) : that.getInput() != null) {
            return false;
        }
//        if (getCreates() != null
//                ? !getCreates().equals(that.getCreates()) : that.getCreates() != null) {
//            return false;
//        }
//        if (getPublicKey() != null
//                ? !getPublicKey().equals(that.getPublicKey()) : that.getPublicKey() != null) {
//            return false;
//        }
//        if (getRaw() != null ? !getRaw().equals(that.getRaw()) : that.getRaw() != null) {
//            return false;
//        }
        if (getR() != null ? !getR().equals(that.getR()) : that.getR() != null) {
            return false;
        }
        return getS() != null ? getS().equals(that.getS()) : that.getS() == null;
    }

    @Override
    public int hashCode() {
        int result = getHash() != null ? getHash().hashCode() : 0;
        result = 31 * result + (getNonceRaw() != null ? getNonceRaw().hashCode() : 0);
        result = 31 * result + (getBlockHash() != null ? getBlockHash().hashCode() : 0);
        result = 31 * result + (getBlockNumberRaw() != null ? getBlockNumberRaw().hashCode() : 0);
        result = 31 * result
                + (getTransactionIndexRaw() != null ? getTransactionIndexRaw().hashCode() : 0);
        result = 31 * result + (getFrom() != null ? getFrom().hashCode() : 0);
        result = 31 * result + (getTo() != null ? getTo().hashCode() : 0);
        result = 31 * result + (getValueRaw() != null ? getValueRaw().hashCode() : 0);
        result = 31 * result + (getGasPriceRaw() != null ? getGasPriceRaw().hashCode() : 0);
        result = 31 * result + (getGasRaw() != null ? getGasRaw().hashCode() : 0);
        result = 31 * result + (getInput() != null ? getInput().hashCode() : 0);
//        result = 31 * result + (getCreates() != null ? getCreates().hashCode() : 0);
//        result = 31 * result + (getPublicKey() != null ? getPublicKey().hashCode() : 0);
//        result = 31 * result + (getRaw() != null ? getRaw().hashCode() : 0);
        result = 31 * result + (getR() != null ? getR().hashCode() : 0);
        result = 31 * result + (getS() != null ? getS().hashCode() : 0);
        result = 31 * result + BigInteger.valueOf(getV()).hashCode();
        return result;
    }
}
