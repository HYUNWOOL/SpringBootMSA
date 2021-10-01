package com.example.cloudgateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {


    public GlobalFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(GlobalFilter.Config config) {
        //customPreFilter

     return (exchange, chain) -> {
         ServerHttpRequest request = exchange.getRequest();
         ServerHttpResponse response = exchange.getResponse();
        log.info("GlobalFilter baseMessage : {}",config.getBaseMessage());
        //custom Post filter

         if(config.isPreLogger()){
             log.info("GlobalFilter Start PRE : request id {}",request.getId());
         }
         return chain.filter(exchange).then(Mono.fromRunnable(() -> {
             if(config.isPostLogger()){
                 log.info("GlobalFilter End POST Response code : {}",response.getStatusCode());
             }
         }));
     };
    }

    @Data
    public static class Config{
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;

    }

}
