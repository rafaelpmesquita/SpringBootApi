package cyber.login.jwt.system.loginsystemjwt.dtos;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;

public record CreateCommentDto(@NotNull String content, @Nullable Long taskId, @Nullable Long projectId) {
}
