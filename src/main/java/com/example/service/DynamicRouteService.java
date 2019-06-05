package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author tw0519
 */
@Service
@Slf4j
public class DynamicRouteService implements ApplicationEventPublisherAware {

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher publisher;


    /**
     * 增加路由
     */
    public String addRoute(RouteDefinition definition) {
        Mono<RouteDefinition> definitionMono = Mono.just(definition);
        routeDefinitionWriter.save(definitionMono).subscribe();
        notifyChanged();
        return "success";
    }


    /**
     * 更新路由
     */
    public String updateRoute(RouteDefinition definition) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            return "updateRoute fail,not find route  routeId: " + definition.getId();
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            notifyChanged();
            return "success";
        } catch (Exception e) {
            return "updateRoute route  fail";
        }

    }

    /**
     * 删除路由
     */
    public String deleteRoute(String id) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(id));

            notifyChanged();
            return "deleteRoute success";
        } catch (Exception e) {
            e.printStackTrace();
            return "deleteRoute fail";
        }
    }

    private void notifyChanged() {
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        log.info("update route success");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
