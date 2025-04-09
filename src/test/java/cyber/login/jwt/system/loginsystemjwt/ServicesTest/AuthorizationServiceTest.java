package cyber.login.jwt.system.loginsystemjwt.ServicesTest;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cyber.login.jwt.system.loginsystemjwt.dtos.*;
import cyber.login.jwt.system.loginsystemjwt.enums.UserRole;
import cyber.login.jwt.system.loginsystemjwt.models.UserModel;
import cyber.login.jwt.system.loginsystemjwt.repositories.UserRepository;
import cyber.login.jwt.system.loginsystemjwt.security.TokenService;
import cyber.login.jwt.system.loginsystemjwt.services.AuthorizationService;

public class AuthorizationServiceTest {

    @InjectMocks
    private AuthorizationService authorizationService;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    private UserModel mockUser;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockUser = new UserModel("email@test.com", new BCryptPasswordEncoder().encode("123"), UserRole.USER);
        mockUser.setCreatedAt(new Date(System.currentTimeMillis()));
    }

    @Test
    public void login_validCredentials_shouldReturnToken() {
        AuthenticationDto dto = new AuthenticationDto("email@test.com", "123");

        when(applicationContext.getBean(AuthenticationManager.class)).thenReturn(authenticationManager);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(tokenService.generateToken(mockUser)).thenReturn("fake-token");

        ResponseEntity<Object> response = authorizationService.login(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof LoginResponseDto);
        assertEquals("fake-token", ((LoginResponseDto) response.getBody()).retorno());
    }

    @Test
    public void login_invalidCredentials_shouldReturnUnauthorized() {
        AuthenticationDto dto = new AuthenticationDto("email@test.com", "wrong");

        when(applicationContext.getBean(AuthenticationManager.class)).thenReturn(authenticationManager);
        when(authenticationManager.authenticate(any()))
            .thenThrow(new BadCredentialsException("Credenciais inválidas"));

        ResponseEntity<Object> response = authorizationService.login(dto);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Credenciais inválidas.", ((LoginResponseDto) response.getBody()).retorno());
    }

    @Test
    public void login_genericException_shouldReturnInternalServerError() {
        AuthenticationDto dto = new AuthenticationDto("email@test.com", "123");

        when(applicationContext.getBean(AuthenticationManager.class)).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<Object> response = authorizationService.login(dto);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Erro interno no servidor.", ((LoginResponseDto) response.getBody()).retorno());
    }

    @Test
    public void register_newUser_shouldReturnOk() {
        RegisterDto dto = new RegisterDto("newuser@test.com", "password", UserRole.USER);

        when(userRepository.findByEmail(dto.email())).thenReturn(null);
        when(userRepository.save(any(UserModel.class))).thenReturn(mockUser);

        ResponseEntity<Object> response = authorizationService.register(dto);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void register_existingUser_shouldReturnBadRequest() {
        RegisterDto dto = new RegisterDto("email@test.com", "123", UserRole.USER);

        when(userRepository.findByEmail(dto.email())).thenReturn(mockUser);

        ResponseEntity<Object> response = authorizationService.register(dto);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("registrar usuário."));
    }

    @Test
    public void register_exception_shouldReturnInternalServerError() {
        RegisterDto dto = new RegisterDto("err@test.com", "pass", UserRole.USER);

        when(userRepository.findByEmail(dto.email())).thenReturn(null);
        when(userRepository.save(any(UserModel.class))).thenThrow(new RuntimeException("Erro DB"));

        ResponseEntity<Object> response = authorizationService.register(dto);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Erro ao registrar usuário.", response.getBody());
    }
}