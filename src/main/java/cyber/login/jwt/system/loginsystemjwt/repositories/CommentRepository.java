package cyber.login.jwt.system.loginsystemjwt.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cyber.login.jwt.system.loginsystemjwt.models.CommentModel;
import cyber.login.jwt.system.loginsystemjwt.models.ProjectModel;
import cyber.login.jwt.system.loginsystemjwt.models.TaskModel;

public interface CommentRepository extends JpaRepository<CommentModel, Long> {

    List<CommentModel> findByTask(TaskModel task);

    List<CommentModel> findByProject(ProjectModel project);
    
}
