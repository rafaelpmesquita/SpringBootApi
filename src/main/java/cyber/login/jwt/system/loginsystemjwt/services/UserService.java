package cyber.login.jwt.system.loginsystemjwt.services;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cyber.login.jwt.system.loginsystemjwt.services.interfaces.IUserService;
import cyber.login.jwt.system.loginsystemjwt.dtos.UpdateUserDto;
import cyber.login.jwt.system.loginsystemjwt.exceptions.UnauthorizedException;
import cyber.login.jwt.system.loginsystemjwt.exceptions.UserNotFoundException;
import cyber.login.jwt.system.loginsystemjwt.models.UserModel;
import cyber.login.jwt.system.loginsystemjwt.repositories.UserRepository;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<UserModel> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = authentication.getPrincipal() instanceof UserModel ? (UserModel) authentication.getPrincipal()
                : null;
        if (user == null) {
             throw new UnauthorizedException("Usuário não autenticado.");
        }
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<UserModel> updateUser(UpdateUserDto userModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = authentication.getPrincipal() instanceof UserModel ? (UserModel) authentication.getPrincipal()
                : null;
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(userModel.password());
        user.setEmail(userModel.email());
        user.setPassword(encryptedPassword);
        user.setUpdatedAt(Date.from(Instant.now()));
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @Override
    public ResponseEntity<Object> deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
             throw new UserNotFoundException("Usuário com ID " + id + " não encontrado.");
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
