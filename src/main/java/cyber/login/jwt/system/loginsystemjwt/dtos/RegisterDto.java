package cyber.login.jwt.system.loginsystemjwt.dtos;

import cyber.login.jwt.system.loginsystemjwt.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record RegisterDto(@NotNull String email, @NotNull String password, @NotNull UserRole userRole) {

}
