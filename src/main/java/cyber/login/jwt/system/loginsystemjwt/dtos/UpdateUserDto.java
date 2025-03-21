package cyber.login.jwt.system.loginsystemjwt.dtos;

import jakarta.validation.constraints.NotNull;

public record UpdateUserDto(@NotNull String email, @NotNull String password) {

}
