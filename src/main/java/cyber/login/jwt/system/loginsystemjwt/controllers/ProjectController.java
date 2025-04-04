package cyber.login.jwt.system.loginsystemjwt.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cyber.login.jwt.system.loginsystemjwt.dtos.ManipulateProjectDto;
import cyber.login.jwt.system.loginsystemjwt.models.ProjectModel;
import cyber.login.jwt.system.loginsystemjwt.services.interfaces.IProjectService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("projects")
public class ProjectController {
    private final IProjectService projectService;

    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("")
    public ResponseEntity<ProjectModel> createProject(@RequestBody ManipulateProjectDto projectModel){
        return projectService.createProject(projectModel);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<ProjectModel>> getAllUserProjects(){
        return projectService.getAllUserProjects();
    }

    @GetMapping("")
    public ResponseEntity<ProjectModel> getProjectById(@RequestParam long id){
        return projectService.getProjectById(id);
    }

    @PutMapping("")
    public ResponseEntity<ProjectModel> updateProject(@RequestBody ManipulateProjectDto projectRequest){
        return projectService.updateProject(projectRequest);
    }

    @DeleteMapping("")
    public ResponseEntity<Boolean> deleteProject(@RequestParam long id){
        return projectService.deleteProject(id);
    }
}
