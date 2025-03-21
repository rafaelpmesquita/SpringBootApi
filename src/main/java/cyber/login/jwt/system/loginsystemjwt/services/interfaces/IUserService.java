package cyber.login.jwt.system.loginsystemjwt.services.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import cyber.login.jwt.system.loginsystemjwt.dtos.UpdateUserDto;
import cyber.login.jwt.system.loginsystemjwt.models.UserModel;
import java.util.UUID;

public interface IUserService {
    ResponseEntity<UserModel> getCurrentUser();
    ResponseEntity<UserModel> updateUser(UpdateUserDto userModel);
    ResponseEntity<List<UserModel>> getAllUsers();
    ResponseEntity<Object> deleteUser(UUID id);
}
