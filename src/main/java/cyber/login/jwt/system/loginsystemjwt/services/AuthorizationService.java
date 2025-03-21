package cyber.login.jwt.system.loginsystemjwt.services;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import cyber.login.jwt.system.loginsystemjwt.exceptions.UserAlreadyExistsException;
import cyber.login.jwt.system.loginsystemjwt.security.TokenService;
import cyber.login.jwt.system.loginsystemjwt.services.interfaces.IAuthorizationService;
import cyber.login.jwt.system.loginsystemjwt.dtos.AuthenticationDto;
import cyber.login.jwt.system.loginsystemjwt.dtos.LoginResponseDto;
import cyber.login.jwt.system.loginsystemjwt.dtos.RegisterDto;
import cyber.login.jwt.system.loginsystemjwt.models.UserModel;
import cyber.login.jwt.system.loginsystemjwt.repositories.UserRepository;
import jakarta.validation.Valid;

@Service
public class AuthorizationService implements IAuthorizationService {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário com email " + email + " não encontrado.");
        }
        return user;
    }

    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDto data) {
        try {
            authenticationManager = context.getBean(AuthenticationManager.class);
            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                    data.email(), data.password());
            Authentication auth = authenticationManager.authenticate(usernamePassword);
            String token = tokenService.generateToken((UserModel) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDto(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(new LoginResponseDto("Credenciais inválidas."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new LoginResponseDto("Erro interno no servidor."));
        }
    }

    @Override
    public ResponseEntity<Object> register(@RequestBody RegisterDto registerDto) {
        try {
            if (userRepository.findByEmail(registerDto.email()) != null) {
                throw new UserAlreadyExistsException("Usuário com email " + registerDto.email() + " já existe.");
            }

            String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());
            UserModel newUser = new UserModel(registerDto.email(), encryptedPassword, registerDto.userRole());
            newUser.setCreatedAt(new Date(System.currentTimeMillis()));

            userRepository.save(newUser);

            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao registrar usuário.");
        }
    }
}
