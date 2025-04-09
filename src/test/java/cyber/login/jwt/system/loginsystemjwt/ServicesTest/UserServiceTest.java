package cyber.login.jwt.system.loginsystemjwt.ServicesTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cyber.login.jwt.system.loginsystemjwt.dtos.UpdateUserDto;
import cyber.login.jwt.system.loginsystemjwt.enums.UserRole;
import cyber.login.jwt.system.loginsystemjwt.exceptions.UnauthorizedException;
import cyber.login.jwt.system.loginsystemjwt.exceptions.UserNotFoundException;
import cyber.login.jwt.system.loginsystemjwt.models.UserModel;
import cyber.login.jwt.system.loginsystemjwt.repositories.UserRepository;
import cyber.login.jwt.system.loginsystemjwt.services.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserService userService;

    private UserModel testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserModel("user@email.com", "password", UserRole.USER);

        
    }

    @Test
    void testGetCurrentUser_Success() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(testUser);
        ResponseEntity<UserModel> response = userService.getCurrentUser();

        assertNotNull(response.getBody());
        assertEquals("user@email.com", response.getBody().getUsername());
    }

    @Test
    void testGetCurrentUser_Unauthorized() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(testUser);
        when(authentication.getPrincipal()).thenReturn(null);

        assertThrows(UnauthorizedException.class, () -> userService.getCurrentUser());
    }

    @Test
    void testUpdateUser_Success() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(testUser);
        UpdateUserDto updateUserDto = new UpdateUserDto("new@email.com", "newpassword");

        ResponseEntity<UserModel> response = userService.updateUser(updateUserDto);

        assertNotNull(response.getBody());
        assertEquals("new@email.com", response.getBody().getUsername());
        assertTrue(new BCryptPasswordEncoder().matches("newpassword", response.getBody().getPassword()));
    }

    @Test
    void testUpdateUser_Failure() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(testUser);
        when(authentication.getPrincipal()).thenReturn(null);

        ResponseEntity<UserModel> response = userService.updateUser(new UpdateUserDto("email", "password"));

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testGetAllUsers() {
        List<UserModel> users = List.of(testUser);
        when(userRepository.findAll()).thenReturn(users);

        ResponseEntity<List<UserModel>> response = userService.getAllUsers();

        assertNotNull(response.getBody());
    }

    @Test
    void testDeleteUser_Success() {
        UUID userId = testUser.getId();
        when(userRepository.existsById(userId)).thenReturn(true);

        ResponseEntity<Object> response = userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteUser_NotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userId));
    }
}
