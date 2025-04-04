package cyber.login.jwt.system.loginsystemjwt.services;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cyber.login.jwt.system.loginsystemjwt.dtos.ManipulateTaskDto;
import cyber.login.jwt.system.loginsystemjwt.models.ProjectModel;
import cyber.login.jwt.system.loginsystemjwt.models.TaskModel;
import cyber.login.jwt.system.loginsystemjwt.repositories.TaskRepository;
import cyber.login.jwt.system.loginsystemjwt.services.interfaces.IProjectService;
import cyber.login.jwt.system.loginsystemjwt.services.interfaces.ITaskService;

@Service
public class TaskService implements ITaskService {
    private final TaskRepository taskRepository;
    private IProjectService projectService;

    public TaskService(TaskRepository taskRepository, IProjectService taskService) {
        this.taskRepository = taskRepository;
        this.projectService = taskService;
    }

    @Override
    public ResponseEntity<TaskModel> createTask(ManipulateTaskDto taskRequest) {
        ProjectModel project = projectService.getProjectById(taskRequest.projectId()).getBody();
        if (project == null) {
            return ResponseEntity.badRequest().build();
        }
        TaskModel task = new TaskModel(taskRequest.title(), taskRequest.status(), new Date(), project);
        taskRepository.save(task);
        return ResponseEntity.ok(task);
    }

    @Override
    public ResponseEntity<List<TaskModel>> getAllProjectTasks(Long projectId) {
        ProjectModel project = projectService.getProjectById(projectId).getBody();
        if (project == null) {
            return ResponseEntity.badRequest().build();
        }
        List<TaskModel> tasks = taskRepository.findByProject(project);
        return ResponseEntity.ok(tasks);
    }

    @Override
    public ResponseEntity<TaskModel> updateTask(ManipulateTaskDto taskRequest) {
        TaskModel task = taskRepository.findById(taskRequest.id()).orElse(null);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        task.setTitle(taskRequest.title());
        task.setStatus(taskRequest.status());
        task.setUpdatedAt(new Date());
        taskRepository.save(task);
        return ResponseEntity.ok(task);
    }

    @Override
    public ResponseEntity<TaskModel> removeTask(Long taskId) {
        TaskModel task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.delete(task);
        return ResponseEntity.ok(task);
    }

    @Override
    public ResponseEntity<TaskModel> getTaskById(Long taskId) {
        TaskModel task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }
}
