package cyber.login.jwt.system.loginsystemjwt.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cyber.login.jwt.system.loginsystemjwt.dtos.ManipulateProjectDto;
import cyber.login.jwt.system.loginsystemjwt.models.ProjectModel;
import cyber.login.jwt.system.loginsystemjwt.models.UserModel;
import cyber.login.jwt.system.loginsystemjwt.repositories.ProjectRepository;
import cyber.login.jwt.system.loginsystemjwt.services.interfaces.IProjectService;
import cyber.login.jwt.system.loginsystemjwt.services.interfaces.IUserService;

import java.util.Date;
import java.util.List;

@Service
public class ProjectService implements IProjectService {

    private final ProjectRepository projectRepository;
    private final IUserService userService;

    public ProjectService(ProjectRepository projectRepository, IUserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<ProjectModel> createProject(ManipulateProjectDto projectRequest) {
        ProjectModel project = new ProjectModel(projectRequest.name(), projectRequest.description(),
        userService.getCurrentUser().getBody(), new Date());
        projectRepository.save(project);
        return ResponseEntity.ok(project);
    }

    @Override
    public ResponseEntity<List<ProjectModel>> getAllUserProjects() {
        UserModel usuarioLogado = userService.getCurrentUser().getBody();
        List<ProjectModel> projects = projectRepository.findByOwner(usuarioLogado);
        return ResponseEntity.ok(projects);
    }

    @Override
    public ResponseEntity<ProjectModel> getProjectById(long id) {
        ProjectModel project = projectRepository.findById(id).get();
        return ResponseEntity.ok(project);
    }

    @Override
    public ResponseEntity<ProjectModel> updateProject(ManipulateProjectDto projectRequest) {
        ProjectModel project = projectRepository.findById(projectRequest.id()).get();
        project.setName(projectRequest.name());
        project.setDescription(projectRequest.description());
        project.setUpdatedAt(new Date());
        projectRepository.save(project);
        return ResponseEntity.ok(project);
    }

    @Override
    public ResponseEntity<Boolean> deleteProject(long id) {
        ProjectModel project = projectRepository.findById(id).get();
        projectRepository.delete(project);
        return ResponseEntity.ok(true);
    }
}
