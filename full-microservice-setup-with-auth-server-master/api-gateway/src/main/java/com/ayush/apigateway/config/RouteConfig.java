//package com.ayush.apigateway.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RouteConfig {
//
//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder){
//        return builder.routes()
//                .route(p->
//                   p.path("/account/**")
//                           .uri("lb://ACCOUNT-SERVICE/")
//                )
//                .route(p->
//                        p.path("/get")
//                        .uri("http://httpbin.org:80")
//                )
//                .build();
//    }
//}
