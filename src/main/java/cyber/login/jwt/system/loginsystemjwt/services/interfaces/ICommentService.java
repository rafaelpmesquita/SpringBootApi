package cyber.login.jwt.system.loginsystemjwt.services.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import cyber.login.jwt.system.loginsystemjwt.dtos.CreateCommentDto;
import cyber.login.jwt.system.loginsystemjwt.models.CommentModel;

public interface ICommentService {
    ResponseEntity<CommentModel> createComment(CreateCommentDto commentDto);
    ResponseEntity<List<CommentModel>> getCommentsByTaskId(Long taskId);
    ResponseEntity<List<CommentModel>> getCommentsByProjectId(Long projectId);
    ResponseEntity<Boolean> deleteComment(Long commentId);
}
