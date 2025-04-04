package cyber.login.jwt.system.loginsystemjwt.repositories;

import cyber.login.jwt.system.loginsystemjwt.models.ProjectModel;
import cyber.login.jwt.system.loginsystemjwt.models.UserModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectModel, Long> {
    List<ProjectModel> findByOwner(UserModel owner);
}
