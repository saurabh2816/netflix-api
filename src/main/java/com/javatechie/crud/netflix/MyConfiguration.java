package com.javatechie.crud.netflix;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class MyConfiguration {

    /* Feel free to change the type of key and value in the Map
     * from String, String to anything of your choice
     */
    @Bean
    public Map<String, String> myMap(){
        java.util.Map<String, String> map = new java.util.HashMap<String, String>();
        map.put("Hello", "world");
        map.put("Saurabh", "Rana");
        return map;
    }

    /*Your other bean exporting methods*/

}