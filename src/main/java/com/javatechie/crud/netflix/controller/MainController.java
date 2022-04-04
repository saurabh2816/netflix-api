package com.javatechie.crud.netflix.controller;

import com.javatechie.crud.netflix.entity.Product;
import com.javatechie.crud.netflix.service.TestingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MainController {


    @Autowired
    private TestingService testingService;

    @GetMapping("/test")
    public ResponseEntity<String> testendpoint() {


        String res = testingService.something();

        return ResponseEntity.ok(res);
    }

}
