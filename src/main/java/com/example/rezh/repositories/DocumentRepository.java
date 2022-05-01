package com.example.rezh.repositories;


import com.example.rezh.entities.Document;
import com.example.rezh.entities.News;
import org.springframework.data.repository.CrudRepository;

public interface DocumentRepository extends CrudRepository<Document, Long> {
    Document getById(Long id);
}