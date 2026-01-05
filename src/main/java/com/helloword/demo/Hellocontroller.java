package com.helloword.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Hellocontroller {

    @GetMapping("/")
    public String helloworld() {
        return "hello";
    }
}
