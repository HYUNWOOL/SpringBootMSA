package com.example.catalogservice.controller;

import com.example.catalogservice.entitiy.Catalog;
import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.ResponseCatalog;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalog-service")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @Autowired
    Environment env;

    @GetMapping("/health-check")
    public String health_check(){
        return String.format("It's Working this Class on Port %s",env
            .getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getUsers(){
        Iterable<Catalog> catalogs = catalogService.getAllCatalog();
        List<ResponseCatalog> result = new ArrayList<>();

        catalogs.forEach(
            t->{
                result.add(new ModelMapper().map(t, ResponseCatalog.class));
            }
        );

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
