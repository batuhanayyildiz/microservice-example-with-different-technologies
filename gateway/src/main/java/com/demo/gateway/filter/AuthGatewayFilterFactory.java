package com.demo.gateway.filter;

import com.demo.gateway.configuration.AuthGatewayFilterFactoryConfiguration;
import com.demo.gateway.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactoryConfiguration> {

    private final Logger logger= LoggerFactory.getLogger(AuthGatewayFilterFactory.class);
    private final SecurityService securityService;
    public AuthGatewayFilterFactory(SecurityService securityService) {
        super(AuthGatewayFilterFactoryConfiguration.class);
        this.securityService = securityService;
    }

    @Override
    public GatewayFilter apply(AuthGatewayFilterFactoryConfiguration config) {
        return (exchange, chain) -> {
            logger.info(config.getBaseMessage());
            ServerHttpRequest request=exchange.getRequest();
            boolean isValid = false;
            String authHeader = request.getHeaders().getFirst("Authorization");
            String token = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")){
                token = authHeader.substring(7);
                isValid=securityService.validateToken(token);
            }
            if(!isValid) return onError(exchange,HttpStatus.UNAUTHORIZED);
            return chain.filter(exchange);

        };

    }
    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }



}
