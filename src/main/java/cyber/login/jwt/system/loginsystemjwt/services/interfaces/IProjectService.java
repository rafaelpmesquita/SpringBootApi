package cyber.login.jwt.system.loginsystemjwt.services.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import cyber.login.jwt.system.loginsystemjwt.dtos.ManipulateProjectDto;
import cyber.login.jwt.system.loginsystemjwt.models.ProjectModel;
public interface IProjectService {
    ResponseEntity<ProjectModel> createProject(ManipulateProjectDto projectModel);
    ResponseEntity<List<ProjectModel>> getAllUserProjects();
    ResponseEntity<ProjectModel> getProjectById(long id);
    ResponseEntity<ProjectModel> updateProject(ManipulateProjectDto projectRequest);
    ResponseEntity<Boolean> deleteProject(long id);

}
