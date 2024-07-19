package com.bej.springcloudapigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;


@Configuration
public class AppConfig {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/userAuth-app/**")
                        .uri("lb://userAuth-app"))
//                         .uri("http://localhost:8888/*"))

                        .route(p->p
                        .path("/movie-app/**")
                        .uri("lb://movie-app"))
//                        .uri("http://localhost:9090/*"))
//                .route(p->p
//                        .path("/mail-app/send-mail")
////                        .uri("lb://email-app"))
//                        .uri("http://localhost:65500/*"))
                .build();
    }

}




