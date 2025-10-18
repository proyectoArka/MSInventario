package com.Arka.MSInventario.infrastructure.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ApplicationYMLController {
    @Value("${ms.inventario.sku}")
    private String sku;

    @Value("${ms.inventario.item}")
    private String item;

    @GetMapping("/config")
    public Map<String, Object> getConfig() {
        return Map.of(sku, item);
    }
}
