package cyber.login.jwt.system.loginsystemjwt.dtos;

import jakarta.validation.constraints.NotNull;

public record AuthenticationDto(@NotNull String email, @NotNull String password) {
    
}
