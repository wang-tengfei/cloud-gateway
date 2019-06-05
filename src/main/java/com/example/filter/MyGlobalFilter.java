package com.example.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author tw0519
 *全局过滤器
 */
@Component
@Slf4j
public class MyGlobalFilter implements GlobalFilter, Ordered {

    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //记录请求开始时间
        exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
            log.info("time is {}, and this is a {}", startTime, "global filter");
            if (startTime != null) {
                //打印
                log.info(exchange.getRequest().getURI() + " 耗时" + (System.currentTimeMillis() - startTime) + "ms");
            }
        }));
    }

    @Override
    public int getOrder() {
        //filter执行的优先级,值越小则优先级越大
        return 0;
    }
}
