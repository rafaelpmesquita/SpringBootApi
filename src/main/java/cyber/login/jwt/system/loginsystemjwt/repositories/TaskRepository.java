package cyber.login.jwt.system.loginsystemjwt.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cyber.login.jwt.system.loginsystemjwt.models.ProjectModel;
import cyber.login.jwt.system.loginsystemjwt.models.TaskModel;

public interface TaskRepository extends JpaRepository<TaskModel, Long> {
    List<TaskModel> findByProject(ProjectModel project);
}