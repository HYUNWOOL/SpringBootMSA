package com.example.catalogservice.service;

import com.example.catalogservice.entitiy.Catalog;

public interface CatalogService {

    Iterable<Catalog> getAllCatalog();
}
