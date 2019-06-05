package com.example.conroller;

import com.example.service.DynamicRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * @author tw0519
 */
@RestController
@RequestMapping("/")
public class DynamicRouteController {

    @Autowired
    private DynamicRouteService routeService;

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;


    @RequestMapping(value = "routes", method = RequestMethod.GET)
    public Object getRoutes() {
        return routeDefinitionLocator.getRouteDefinitions();
    }

    @RequestMapping(value = "route/{route_id}", method = RequestMethod.GET)
    public Object getRouteById(@PathVariable("route_id") String routeId) {
        Flux<RouteDefinition> definitions = routeDefinitionLocator.getRouteDefinitions();

        return definitions.filter(w -> w.getId().equals(routeId));
    }

    @RequestMapping(value = "route", method = RequestMethod.POST)
    public Object saveRoute(@RequestBody RouteDefinition routeDefinition) {
        return routeService.addRoute(routeDefinition);
    }

    @RequestMapping(value = "route/{route_id}", method = RequestMethod.PUT)
    public Object updateRoute(@PathVariable("route_id")String routeId, @RequestBody RouteDefinition routeDefinition) {
        return routeService.updateRoute(routeDefinition);
    }

    @RequestMapping(value = "route/{route_id}", method = RequestMethod.DELETE)
    public Object deleteRoute(@PathVariable("route_id")String routeId) {
        return routeService.deleteRoute(routeId);
    }
}
