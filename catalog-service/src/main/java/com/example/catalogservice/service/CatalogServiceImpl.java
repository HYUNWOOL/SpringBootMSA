package com.example.catalogservice.service;

import com.example.catalogservice.entitiy.Catalog;
import com.example.catalogservice.repository.CatalogRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service
public class CatalogServiceImpl implements CatalogService{

    @Autowired
    private CatalogRepository catalogRepository;

    @Override
    public Iterable<Catalog> getAllCatalog() {
        return catalogRepository.findAll();
    }
}
