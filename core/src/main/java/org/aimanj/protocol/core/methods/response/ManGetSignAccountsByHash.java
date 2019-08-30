package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

import java.util.List;
import java.util.Map;

/**
 * @program: base
 * @description:
 * @author: Li.Jie
 * @create: 2019-07-19 11:34
 **/
public class ManGetSignAccountsByHash extends Response<List<Map<String, Object>>> {
    public List<Map<String, Object>> getSignAccounts() {
        return getResult();
    }
}