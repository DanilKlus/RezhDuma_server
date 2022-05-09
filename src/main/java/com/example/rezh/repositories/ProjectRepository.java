package com.example.rezh.repositories;


import com.example.rezh.entities.Document;
import com.example.rezh.entities.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project getById(Long id);

    @Query("Select c from Project c where c.title like %:title% or c.text like %:text%")
    List<Project> findAllByTitleContainingOrTextContaining(String title, String text);

    @Query("Select c from Project c order by c.id desc")
    List<Project> findAll();
}