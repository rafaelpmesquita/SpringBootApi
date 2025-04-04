package cyber.login.jwt.system.loginsystemjwt.controllers;

import org.springframework.web.bind.annotation.RestController;

import cyber.login.jwt.system.loginsystemjwt.dtos.CreateCommentDto;
import cyber.login.jwt.system.loginsystemjwt.models.CommentModel;
import cyber.login.jwt.system.loginsystemjwt.services.interfaces.ICommentService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@RestController
@RequestMapping("comments")
public class CommentController {
    private final ICommentService commentService;
    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("")
    public ResponseEntity<CommentModel> createComment(@RequestBody CreateCommentDto commentDto) {
        return commentService.createComment(commentDto);
    }
    @GetMapping("/task")
    public ResponseEntity<List<CommentModel>> getCommentsByTaskId(@RequestParam Long taskId) {
        return commentService.getCommentsByTaskId(taskId);
    }
    @GetMapping("/project")
    public ResponseEntity<List<CommentModel>> getCommentsByProjectId(@RequestParam Long projectId) {
        return commentService.getCommentsByProjectId(projectId);
    }
    @DeleteMapping("")
    public ResponseEntity<Boolean> deleteComment(@RequestParam Long commentId) {
        return commentService.deleteComment(commentId);
    }
}
