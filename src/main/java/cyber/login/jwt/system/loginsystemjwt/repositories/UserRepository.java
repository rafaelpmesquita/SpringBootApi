package cyber.login.jwt.system.loginsystemjwt.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import cyber.login.jwt.system.loginsystemjwt.models.UserModel;


public interface UserRepository extends JpaRepository<UserModel, UUID>{
    UserModel findByEmail(String email);
}
