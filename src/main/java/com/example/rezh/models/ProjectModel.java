package com.example.rezh.models;

import com.example.rezh.entities.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectModel {
    private Long id;
    private String title;
    private String text;
    private String files;
    private LocalDateTime projectDate;


    public static ProjectModel toModel(Project projectEntity) {
        ProjectModel project = new ProjectModel();

        project.setId(projectEntity.getId());
        project.setTitle(projectEntity.getTitle());
        project.setText(projectEntity.getText());
        project.setFiles(projectEntity.getFiles());
        project.setProjectDate(projectEntity.getProjectDate());

        return project;
    }

    public static List<ProjectModel> toModel(Iterable<Project> projectEntities) {
        List<ProjectModel> projectModels = new ArrayList<ProjectModel>();
        for (Project projectEntity: projectEntities ) {
            ProjectModel project = new ProjectModel();

            project.setId(projectEntity.getId());
            project.setTitle(projectEntity.getTitle());
            project.setText(projectEntity.getText());
            project.setFiles(projectEntity.getFiles());
            project.setProjectDate(projectEntity.getProjectDate());

            projectModels.add(project);
        }
        return projectModels;
    }

}
