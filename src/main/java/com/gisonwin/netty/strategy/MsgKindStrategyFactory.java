package com.gisonwin.netty.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/6/5 14:33
 */
@Service
public class MsgKindStrategyFactory {
    @Autowired
    Map<String, MsgKindStrategy> kinds = new ConcurrentHashMap<>();
    public MsgKindStrategy getMsgKindStrategy(String type) {
        return kinds.get(type);
    }
}
