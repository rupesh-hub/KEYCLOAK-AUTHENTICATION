package com.ayush.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.net.http.HttpClient;

@EnableEurekaClient
@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder){
		return builder.routes()
				.route(p->
						p.path("/login")
								.filters(f->f.rewritePath("/login","/oauth/token"))
								.uri("lb://user-service")
				)
				.route(p->
						p.path("/get")
								.uri("http://httpbin.org:80")
				)
				.build();
	}

/*	@Bean
	public HttpClient httpClient() {
		return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
	}*/

}
