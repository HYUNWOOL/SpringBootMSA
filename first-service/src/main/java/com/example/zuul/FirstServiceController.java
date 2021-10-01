package com.example.zuul;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first-service")
public class FirstServiceController {
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
    public String check(){
        return "hi in to the first Service";
    }


}
