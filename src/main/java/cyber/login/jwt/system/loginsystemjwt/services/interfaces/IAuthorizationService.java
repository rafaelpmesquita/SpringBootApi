package cyber.login.jwt.system.loginsystemjwt.services.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cyber.login.jwt.system.loginsystemjwt.dtos.AuthenticationDto;
import cyber.login.jwt.system.loginsystemjwt.dtos.RegisterDto;

public interface IAuthorizationService extends UserDetailsService {
    
    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
    
    ResponseEntity<Object> login(AuthenticationDto data);
    
    ResponseEntity<Object> register(RegisterDto registerDto);
}
