package cyber.login.jwt.system.loginsystemjwt.dtos;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;

public record ManipulateProjectDto(@Nullable long id, @NotNull String name, @NotNull String description) {
    
}
