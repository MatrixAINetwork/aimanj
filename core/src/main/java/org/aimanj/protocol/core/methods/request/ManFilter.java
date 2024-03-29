package org.aimanj.protocol.core.methods.request;

import java.util.Arrays;
import java.util.List;

import org.aimanj.protocol.core.DefaultBlockParameter;

/**
 * Filter implementation as per
 * <a href="https://github.com/matrix/wiki/wiki/JSON-RPC#man_newfilter">docs</a>.
 */
public class ManFilter extends Filter<ManFilter> {
    private DefaultBlockParameter fromBlock;  // optional, params - defaults to latest for both
    private DefaultBlockParameter toBlock;
    private List<String> address;  // spec. implies this can be single address as string or list

    public ManFilter() {
        super();
    }

    public ManFilter(DefaultBlockParameter fromBlock, DefaultBlockParameter toBlock,
                     List<String> address) {
        super();
        this.fromBlock = fromBlock;
        this.toBlock = toBlock;
        this.address = address;
    }

    public ManFilter(DefaultBlockParameter fromBlock, DefaultBlockParameter toBlock,
                     String address) {
        this(fromBlock, toBlock, Arrays.asList(address));
    }

    public DefaultBlockParameter getFromBlock() {
        return fromBlock;
    }

    public DefaultBlockParameter getToBlock() {
        return toBlock;
    }

    public List<String> getAddress() {
        return address;
    }

    @Override
    ManFilter getThis() {
        return this;
    }
}
