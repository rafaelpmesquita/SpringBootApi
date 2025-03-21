package cyber.login.jwt.system.loginsystemjwt.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cyber.login.jwt.system.loginsystemjwt.services.interfaces.IAuthorizationService;
import cyber.login.jwt.system.loginsystemjwt.dtos.AuthenticationDto;
import cyber.login.jwt.system.loginsystemjwt.dtos.RegisterDto;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {
    
    private final IAuthorizationService authorizationService;

    public AuthController(IAuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authdto(@RequestBody @Valid AuthenticationDto authDto){
        return authorizationService.login(authDto);
    }


    @PostMapping("/register")
    public ResponseEntity<Object> register (@RequestBody @Valid RegisterDto registerDto){
        return authorizationService.register(registerDto);
    }
}
