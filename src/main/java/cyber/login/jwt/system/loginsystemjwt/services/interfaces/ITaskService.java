package cyber.login.jwt.system.loginsystemjwt.services.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import cyber.login.jwt.system.loginsystemjwt.dtos.ManipulateTaskDto;
import cyber.login.jwt.system.loginsystemjwt.models.TaskModel;

public interface ITaskService {
    ResponseEntity<TaskModel> createTask(ManipulateTaskDto taskRequest);
    ResponseEntity<List<TaskModel>> getAllProjectTasks(Long projectId);
    ResponseEntity<TaskModel> updateTask(ManipulateTaskDto taskRequest);
    ResponseEntity<TaskModel> removeTask(Long taskId);
    ResponseEntity<TaskModel> getTaskById(Long taskId);
}
