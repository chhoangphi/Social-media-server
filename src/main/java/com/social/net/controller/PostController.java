package com.social.net.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/comment")
@RequiredArgsConstructor
public class PostController {
    @GetMapping
    public String est(){
        return "alo";
    }
}
