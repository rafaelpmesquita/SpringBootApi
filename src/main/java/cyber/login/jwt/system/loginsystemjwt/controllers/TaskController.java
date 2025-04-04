package cyber.login.jwt.system.loginsystemjwt.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cyber.login.jwt.system.loginsystemjwt.dtos.ManipulateTaskDto;
import cyber.login.jwt.system.loginsystemjwt.models.TaskModel;
import cyber.login.jwt.system.loginsystemjwt.services.interfaces.ITaskService;

@RestController
@RequestMapping("tasks")
public class TaskController {
    private final ITaskService taskService;

    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("")
    public ResponseEntity<TaskModel> createTask(@RequestBody ManipulateTaskDto taskRequest) {
        return taskService.createTask(taskRequest);
    }

    @GetMapping("")
    public ResponseEntity<List<TaskModel>> getAllProjectTasks(@RequestParam Long projectId) {
        return taskService.getAllProjectTasks(projectId);
    }

    @PutMapping("")
    public ResponseEntity<TaskModel> updateTask(@RequestBody ManipulateTaskDto taskRequest) {
        return taskService.updateTask(taskRequest);
    }

    @DeleteMapping("")
    public ResponseEntity<TaskModel> removeTask(@RequestParam Long taskId) {
        return taskService.removeTask(taskId);
    }
}
