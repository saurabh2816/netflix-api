package com.javatechie.crud.netflix.IPL.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/ipl/v1")
 public class IPLController {

    @GetMapping("/test")
    public String test() {

        return "Saurabh";

    }

}
