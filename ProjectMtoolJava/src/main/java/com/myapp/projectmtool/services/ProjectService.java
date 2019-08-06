package com.myapp.projectmtool.services;

import com.myapp.projectmtool.domain.Project;
import com.myapp.projectmtool.exceptions.ProjectIdException;
import com.myapp.projectmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    //Create method
    public Project saveOrUpdateProject(Project project){
        //logic
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch (Exception e){
            throw  new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+ "' already exists");
        }
    }

    //read operation
    public Project findProjectByIdentifier(String projectId){

        //findByProjectIdentifier is built in method in ProjectRepository extends CrudRepository
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw  new ProjectIdException("Project ID '"+projectId+ "' doesn't exists");
        }
        return project;
    }

    //get all projects
    public  Iterable<Project> findAllProjects(){
        return  projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null){
            throw  new ProjectIdException("Cannot delete project '"+projectId+ "'. This project doesn't exists");
        }
        projectRepository.delete(project);

    }

}
