package cyber.login.jwt.system.loginsystemjwt.ServicesTest;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import cyber.login.jwt.system.loginsystemjwt.dtos.CreateCommentDto;
import cyber.login.jwt.system.loginsystemjwt.models.*;
import cyber.login.jwt.system.loginsystemjwt.repositories.CommentRepository;
import cyber.login.jwt.system.loginsystemjwt.services.CommentService;
import cyber.login.jwt.system.loginsystemjwt.services.interfaces.IProjectService;
import cyber.login.jwt.system.loginsystemjwt.services.interfaces.ITaskService;
import cyber.login.jwt.system.loginsystemjwt.services.interfaces.IUserService;

public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private IUserService userService;

    @Mock
    private ITaskService taskService;

    @Mock
    private IProjectService projectService;

    private UserModel mockUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockUser = new UserModel("email@test.com", "123", cyber.login.jwt.system.loginsystemjwt.enums.UserRole.USER);
    }

    @Test
    public void createComment_withValidProject_shouldReturnComment() {
        CreateCommentDto dto = new CreateCommentDto("Nice project", null, 1L);
        ProjectModel project = new ProjectModel();
        project.setId(1L);

        when(userService.getCurrentUser()).thenReturn(ResponseEntity.ok(mockUser));
        when(projectService.getProjectById(1L)).thenReturn(ResponseEntity.ok(project));

        CommentModel savedComment = new CommentModel();
        savedComment.setContent("Nice project");

        when(commentRepository.save(any(CommentModel.class))).thenReturn(savedComment);

        ResponseEntity<CommentModel> response = commentService.createComment(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Nice project", response.getBody().getContent());
    }

    @Test
    public void createComment_withBothProjectAndTask_shouldThrowException() {
        CreateCommentDto dto = new CreateCommentDto("Invalid", 1L, 2L);
        when(userService.getCurrentUser()).thenReturn(ResponseEntity.ok(mockUser));

        assertThrows(IllegalArgumentException.class, () -> {
            commentService.createComment(dto);
        });
    }

    @Test
    public void createComment_withNoProjectOrTask_shouldThrowException() {
        CreateCommentDto dto = new CreateCommentDto("Invalid", null, null);
        when(userService.getCurrentUser()).thenReturn(ResponseEntity.ok(mockUser));

        assertThrows(IllegalArgumentException.class, () -> {
            commentService.createComment(dto);
        });
    }

    @Test
    public void createComment_userNotFound_shouldReturnBadRequest() {
        CreateCommentDto dto = new CreateCommentDto("Invalid", 1L, null);
        when(userService.getCurrentUser()).thenReturn(ResponseEntity.ok(null));

        ResponseEntity<CommentModel> response = commentService.createComment(dto);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void getCommentsByTaskId_valid_shouldReturnList() {
        TaskModel task = new TaskModel();
        task.setId(1L);

        List<CommentModel> commentList = List.of(new CommentModel(1L, "Comment", mockUser, task, null, LocalDateTime.now()));
        when(taskService.getTaskById(1L)).thenReturn(ResponseEntity.ok(task));
        when(commentRepository.findByTask(task)).thenReturn(commentList);

        ResponseEntity<List<CommentModel>> response = commentService.getCommentsByTaskId(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void getCommentsByProjectId_projectNotFound_shouldReturnBadRequest() {
        when(projectService.getProjectById(1L)).thenReturn(ResponseEntity.ok(null));

        ResponseEntity<List<CommentModel>> response = commentService.getCommentsByProjectId(1L);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void deleteComment_valid_shouldReturnTrue() {
        CommentModel comment = new CommentModel();
        comment.setId(1L);

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        ResponseEntity<Boolean> response = commentService.deleteComment(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody());
    }

    @Test
    public void deleteComment_notFound_shouldReturnNotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Boolean> response = commentService.deleteComment(1L);
        assertEquals(404, response.getStatusCodeValue());
    }
}