package cyber.login.jwt.system.loginsystemjwt.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cyber.login.jwt.system.loginsystemjwt.dtos.CreateCommentDto;
import cyber.login.jwt.system.loginsystemjwt.models.CommentModel;
import cyber.login.jwt.system.loginsystemjwt.models.ProjectModel;
import cyber.login.jwt.system.loginsystemjwt.models.TaskModel;
import cyber.login.jwt.system.loginsystemjwt.models.UserModel;
import cyber.login.jwt.system.loginsystemjwt.repositories.CommentRepository;
import cyber.login.jwt.system.loginsystemjwt.services.interfaces.ICommentService;
import cyber.login.jwt.system.loginsystemjwt.services.interfaces.IProjectService;
import cyber.login.jwt.system.loginsystemjwt.services.interfaces.ITaskService;
import cyber.login.jwt.system.loginsystemjwt.services.interfaces.IUserService;

@Service
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;
    private final IUserService userService;
    private final ITaskService taskService;
    private final IProjectService projectService;

    public CommentService(CommentRepository commentRepository, IUserService userService, ITaskService taskService,
            IProjectService projectService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @Override
    public ResponseEntity<CommentModel> createComment(CreateCommentDto commentDto) {
        UserModel usuarioLogado = userService.getCurrentUser().getBody();
        if (usuarioLogado == null) {
            return ResponseEntity.badRequest().build();
        }

        boolean hasProjectId = commentDto.projectId() != null;
        boolean hasTaskId = commentDto.taskId() != null;

        // Se ambos projectId e taskId forem passados, lança exceção
        if (hasProjectId && hasTaskId) {
            throw new IllegalArgumentException("Não pode ter projectId e taskId ao mesmo tempo.");
        }

        // Se nenhum dos dois for passado, lança exceção
        if (!hasProjectId && !hasTaskId) {
            throw new IllegalArgumentException("É necessário fornecer um projectId ou um taskId.");
        }

        ProjectModel project = null;
        TaskModel task = null;

        // Busca apenas o que foi fornecido
        if (hasProjectId) {
            project = projectService.getProjectById(commentDto.projectId()).getBody();
            if (project == null) {
                return ResponseEntity.badRequest().build();
            }
        }

        if (hasTaskId) {
            task = taskService.getTaskById(commentDto.taskId()).getBody();
            if (task == null) {
                return ResponseEntity.badRequest().build();
            }
        }

        // Cria o comentário associando apenas um dos dois
        CommentModel comment = new CommentModel(1L, commentDto.content(), usuarioLogado, task, project, LocalDateTime.now());
        commentRepository.save(comment);

        return ResponseEntity.ok(comment);
    }

    @Override
    public ResponseEntity<List<CommentModel>> getCommentsByTaskId(Long taskId) {
        TaskModel task = taskService.getTaskById(taskId).getBody();
        if (task == null) {
            return ResponseEntity.badRequest().build();
        }
        List<CommentModel> comments = commentRepository.findByTask(task);
        return ResponseEntity.ok(comments);
    }

    @Override
    public ResponseEntity<List<CommentModel>> getCommentsByProjectId(Long projectId) {
        ProjectModel project = projectService.getProjectById(projectId).getBody();
        if (project == null) {
            return ResponseEntity.badRequest().build();
        }
        List<CommentModel> comments = commentRepository.findByProject(project);
        return ResponseEntity.ok(comments);
    }

    @Override
    public ResponseEntity<Boolean> deleteComment(Long commentId) {
        CommentModel comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            return ResponseEntity.notFound().build();
        }
        commentRepository.delete(comment);
        return ResponseEntity.ok(true);
    }
}
