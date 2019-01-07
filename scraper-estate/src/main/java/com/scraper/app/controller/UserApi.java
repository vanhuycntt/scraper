package com.scraper.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserApi {

    @RequestMapping("api/user/key")
    public String getUserKey() {
        return UUID.randomUUID().toString();
    }
}
