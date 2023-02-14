package com.opentelemetry.multiroute.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class EndpointController {

    @GetMapping("/payment")
    @ResponseBody
    public String payment() {
        return "Hello from payment";
    }

    @GetMapping("/email")
    @ResponseBody
    public String email() {
        return "Hello from email";
    }

    @GetMapping("/stockavailability")
    @ResponseBody
    public String stockavailability() {
        return "Hello from stock availability";
    }

    @GetMapping("/refund")
    @ResponseBody
    public String refund() {
        return "Hello from refund";
    }

    @GetMapping("/shipping")
    @ResponseBody
    public String shipping() {
        return "Hello from shipping";
    }
}
