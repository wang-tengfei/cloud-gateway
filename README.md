# spring cloud gateway

## spring cloud gateway 说明文档
  Spring Cloud Gateway是Spring Cloud官方推出的第二代网关框架，取代Zuul网关。
  网关作为流量的，在微服务系统中有着非常作用，网关常见的功能有路由转发、权限校验、限流控制等作用。

##功能模块
* spring cloud gateway
* eureka client
* netflix hystrix
* opentracing jaeger

##配置gateway

1. 添加依赖jar
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
    ```

2. 配置gateway
    ```properties
    server:
      port: 8088
    
    spring:
      application:
        name: gateway
    #  profiles:
    #    active: path_route
      cloud:
        gateway:
          discovery:
            locator:
              enabled: false
              lower-case-service-id: true
          routes:
            - id: project-two
              #服务的负载均衡地址
              uri: lb://PROJECT-TWO
              predicates:
                - Path=/two/**
              filters:
                - StripPrefix=0  #StripPrefix=1转发之前将/two去掉
                - Authorize=true #开启/关闭 AuthorizeGatewayFilterFactory 过滤
            - id: project-one
              #服务的负载均衡地址
              uri: lb://PROJECT-ONE
              predicates:
                # 匹配请求路径，只有指定路径可以访问通过
                - Path=/one/**
                # 匹配请求方式，只有指定方式可以访问通过
                - Method=GET
                # 匹配请求头，只有指定请求头可以访问通过
                - Header=Host, localhost:8088
                # 匹配host，只有指定host可以访问通过
                - Host=localhost:8088
    #            - RemoteAddr=127.0.0.1 # 指定IP可以访问
    #            - Query=name #请求参数匹配
              filters:
                - StripPrefix=1 #StripPrefix=1转发之前将/one去掉
                # 添加请求头
                - AddRequestHeader=X-Request-name, tengfei
                - AddRequestHeader=X-Request-age, 18
                # 添加响应头
                - AddResponseHeader=Success, ture
                # 限流策略
                - name: RequestRateLimiter
                  args:
                    #用于限流的键的解析器的 Bean 对象的名字。它使用 SpEL 表达式根据#{@beanName}从 Spring 容器中获取 Bean 对象
                    key-resolver: '#{@hostAddrKeyResolver}'
                    #令牌桶每秒填充平均速率
                    redis-rate-limiter.replenishRate: 1
                    #令牌桶总容量
                    redis-rate-limiter.burstCapacity: 3
                # 熔断
                - name: Hystrix
                  args:
                    name: fallbackcmd
                    fallbackUri: forward:/fallback
    ```