package com.example.cloudgateway.filter;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    Environment env;

    public AuthorizationHeaderFilter(Environment env){
        super(Config.class);
        this.env = env;
    }

    public static class Config{

    }

    //login -> toekn-> users(with token) -> header(include token)
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                return onError(exchange, "no authorization header", HttpStatus.UNAUTHORIZED);
            }

            String auth = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = auth.replace("Bearer", "");

            if(!isJwtValid(jwt)){
                return onError(exchange, "JWT token is not Valid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        });
    }

    private boolean isJwtValid(String jwt) {
        boolean result = true;
        String subject = null;
        try{
            subject = Jwts.parser().setSigningKey(env.getProperty("token.secret"))
                .parseClaimsJws(jwt).getBody()
                .getSubject();

        }catch (Exception e){
            result = false;
        }

        if(subject == null || subject.isEmpty()){
            result = false;
        }


        return result;

    }

    //mono 단일값, flux 여러값 -> Spring WebFlux.
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);

        log.error(err);
        return response.setComplete();
    }
}
