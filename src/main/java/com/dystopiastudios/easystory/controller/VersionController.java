package com.dystopiastudios.easystory.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://easystory-open.web.app")
public class VersionController {
    @Operation(summary = "Get API version")
    @GetMapping("/version")
    public String getVersion() {
        return "1.0";
    }
}
