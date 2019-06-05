package com.example.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author tw0519
 * 自定义过滤器
 * 使用方法：
 *         filters:
 *         # 关键在下面一句，值为true则开启认证，false则不开启
 *         # 这种配置方式和spring cloud gateway内置的GatewayFilterFactory一致
 *         - Authorize=true
 *
 */
@Component
@Slf4j
public class AuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizeGatewayFilterFactory.Config> {

    public AuthorizeGatewayFilterFactory() {
        super(Config.class);
        log.info("Loaded GatewayFilterFactory [Authorize]");
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("enabled");
    }

    @Override
    public GatewayFilter apply(AuthorizeGatewayFilterFactory.Config config) {
        log.info("this is a customer filter");
        return ((exchange, chain) -> {
            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            return chain.filter(exchange.mutate().request(builder.build()).build());
        });
    }


    @Data
    public static class Config {
        /**
         * 控制是否开启认证
         */
        private boolean enabled;

        public Config() {
        }

    }
}
