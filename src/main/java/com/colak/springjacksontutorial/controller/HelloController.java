package com.colak.springjacksontutorial.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/hello")
public class HelloController {

    // http://localhost:8080/api/hello/hello-json
    @GetMapping(path = "hello-json", produces = MediaType.APPLICATION_JSON_VALUE)
    HelloDto helloJson() {
        return new HelloDto("Hello Json");
    }

    // http://localhost:8080/api/hello/hello-xml
    @GetMapping(path = "hello-xml", produces = MediaType.APPLICATION_XML_VALUE)
    HelloDto helloXml() {
        return new HelloDto("Hello XMl");
    }

    // http://localhost:8080/api/hello/hello
    @GetMapping(path = "hello")
    HelloDto hello() {
        return new HelloDto("Hello Json");
    }

}
