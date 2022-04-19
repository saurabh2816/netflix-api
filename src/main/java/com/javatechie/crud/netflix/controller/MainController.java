package com.javatechie.crud.netflix.controller;

import com.javatechie.crud.netflix.model.Node;
import com.javatechie.crud.netflix.service.JSoupService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
public class MainController {


    @Autowired
    private JSoupService JSoupService;

    @GetMapping("/getOne")
    public ResponseEntity<List<Node>> getListOfNodesFromOneLink() {

        List<Node> res = JSoupService.getListOfNodesFromLink();
        return ResponseEntity.ok(res);

    }

    @GetMapping("/fun")
    public ResponseEntity<String> newendpoint() {

        String res = "saurabh and ronak";
        return ResponseEntity.ok(res);
    }
}
