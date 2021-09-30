package com.example.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class ZuulLoggingFilter extends ZuulFilter {


//    실행될때마다 처음 먼저 생성.
    @Override
    public Object run() throws ZuulException {
        log.info("************* printing logs: ");

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("************* printing request: " + request.getRequestURL());
        return null;
    }

    //필터를 먼저할것인가 나중에 할 것인가.
    @Override
    public String filterType() {
        return "pre";
    }

    //필터순서
    @Override
    public int filterOrder() {
        return 1;
    }

    //필터 사용여부
    @Override
    public boolean shouldFilter() {
        return true;
    }

}
