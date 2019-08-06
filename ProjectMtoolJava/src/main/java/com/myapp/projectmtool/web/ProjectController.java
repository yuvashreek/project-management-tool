package com.myapp.projectmtool.web;

import com.myapp.projectmtool.domain.Project;
import com.myapp.projectmtool.services.MapValidationErrorService;
import com.myapp.projectmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("") //create and update
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result){
        //checking error by passing request body into MapValidationService
        //helps us getting valid PROJECT OBJECT
        //MapValidationService doesnt check at a db level validation (eg : @column as projectIdentifier)
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap!=null) return errorMap;

        //projectIdentifier error happens at db level --> custom exception handling in exceptions package -->@ControllerAdvice annotation
        //Above is called in service layer

        //to run logic calling service layer
        Project project1 = projectService.saveOrUpdateProject(project);
        return  new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId){
        Project project = projectService.findProjectByIdentifier(projectId);
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAProjects(){return  projectService.findAllProjects();}

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId){
         projectService.deleteProjectByIdentifier(projectId);
        return new ResponseEntity<String>("Project with ID: '"+projectId+" 'was deleted", HttpStatus.OK );
    }

}
