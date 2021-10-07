package com.example.zuul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/first-service")
public class FirstServiceController {

    Environment env;

    @Autowired
    public FirstServiceController(Environment env) {
        this.env = env;
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome to the first Service";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header){
    System.out.println(header);
        return "welcome in to the first Service";
    }


    @GetMapping("/check")
    public String check(HttpServletRequest request){
    System.out.println("server port : " + request.getServerPort());
        return String.format("hi in to the first Service on port %s",env.getProperty("local.server.port"));
    }


}
