package com.example;

import com.example.config.HostAddrKeyResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * @author tw0519
 */
@SpringBootApplication
@EnableEurekaClient
public class CloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayApplication.class, args);
    }

    @Bean
    public HostAddrKeyResolver hostAddrKeyResolver() {
        return new HostAddrKeyResolver();
    }

//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(p-> p
//                        .path("/two")
//                        .filters(f-> f.addRequestHeader("Hello", "WTF"))
//                        .uri("http://localhost:8095"))
//                .route(p-> p
//                    .host("*.hystrix.com")
//                    .filters(f-> f
//                            .hystrix(config -> config
//                            .setName("mycmd")
//                            .setFallbackUri("forward:/fallback"))
//                    )
//                .uri("http://localhost:8095"))
//                .build();
//    }
}
