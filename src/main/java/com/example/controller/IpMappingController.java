package com.example.controller;

import com.example.model.IpMapping;
import com.example.service.IpMappingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IpMappingController {

    private final IpMappingService service;

    public IpMappingController(IpMappingService service) {
        this.service = service;
    }

    @GetMapping("/ip-mappings")
    public List<IpMapping> getMappings() {
        return service.findAll();
    }

    @GetMapping("/find-ip")
    public List<IpMapping> getMappings(@RequestParam("ip") int ip) {
        return service.findByIp(ip);
    }
}
