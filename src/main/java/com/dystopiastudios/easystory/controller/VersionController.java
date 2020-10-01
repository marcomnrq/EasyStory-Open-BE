package com.dystopiastudios.easystory.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class VersionController {
    @GetMapping("/version")
    public String getVersion() {
        return "1.0";
    }
}
