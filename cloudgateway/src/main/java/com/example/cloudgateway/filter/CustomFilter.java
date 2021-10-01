package com.example.cloudgateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {


    public CustomFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(CustomFilter.Config config) {
        //customPreFilter

     return (exchange, chain) -> {
         ServerHttpRequest request = exchange.getRequest();
         ServerHttpResponse response = exchange.getResponse();
        log.info("CustomPreFilter : request id {}",request.getId());
        //custom Post filter

         return chain.filter(exchange).then(Mono.fromRunnable(() -> {
             log.info("CustomPostFilter Response code : {}",response.getStatusCode());
         }));
     };
    }

    public static class Config{

    }

}
