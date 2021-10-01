package com.example.zuul;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/second-service")
public class SecondServiceController {
    @GetMapping("/welcome")
    public String welcome(){
        return "welcome to the second Service";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("second-request") String header){
        System.out.println(header);
        return "welcome in to the second Service";
    }

    @GetMapping("/check")
    public String check(){
        return "hi in to the second Service";
    }
}
