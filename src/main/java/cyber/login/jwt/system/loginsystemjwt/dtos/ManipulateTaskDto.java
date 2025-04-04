package cyber.login.jwt.system.loginsystemjwt.dtos;

import cyber.login.jwt.system.loginsystemjwt.enums.StatusTask;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;

public record ManipulateTaskDto(@Nullable long id, @NotNull long projectId, @NotNull String title, @NotNull StatusTask status) {

}
