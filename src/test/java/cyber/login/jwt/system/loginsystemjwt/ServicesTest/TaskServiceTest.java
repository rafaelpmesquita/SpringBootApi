package cyber.login.jwt.system.loginsystemjwt.ServicesTest;

import cyber.login.jwt.system.loginsystemjwt.dtos.ManipulateTaskDto;
import cyber.login.jwt.system.loginsystemjwt.enums.StatusTask;
import cyber.login.jwt.system.loginsystemjwt.models.ProjectModel;
import cyber.login.jwt.system.loginsystemjwt.models.TaskModel;
import cyber.login.jwt.system.loginsystemjwt.repositories.TaskRepository;
import cyber.login.jwt.system.loginsystemjwt.services.TaskService;
import cyber.login.jwt.system.loginsystemjwt.services.interfaces.IProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TaskServiceTest {

    private TaskRepository taskRepository;
    private IProjectService projectService;
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        taskRepository = mock(TaskRepository.class);
        projectService = mock(IProjectService.class);
        taskService = new TaskService(taskRepository, projectService);
    }

    @Test
    public void testCreateTask_success() {
        ProjectModel project = new ProjectModel();
        ManipulateTaskDto dto = new ManipulateTaskDto(0L, 1L, "Nova Tarefa", StatusTask.PENDENTE);

        when(projectService.getProjectById(1L)).thenReturn(ResponseEntity.ok(project));

        ResponseEntity<TaskModel> response = taskService.createTask(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Nova Tarefa", response.getBody().getTitle());
        verify(taskRepository, times(1)).save(any(TaskModel.class));
    }

    @Test
    public void testCreateTask_projectNotFound() {
        ManipulateTaskDto dto = new ManipulateTaskDto(0L, 999L, "Falha", StatusTask.PENDENTE);
        when(projectService.getProjectById(999L)).thenReturn(ResponseEntity.badRequest().build());

        ResponseEntity<TaskModel> response = taskService.createTask(dto);

        assertEquals(400, response.getStatusCodeValue());
        verify(taskRepository, never()).save(any(TaskModel.class));
    }

    @Test
    public void testGetAllProjectTasks_success() {
        ProjectModel project = new ProjectModel();
        List<TaskModel> tasks = Arrays.asList(new TaskModel("Tarefa 1", StatusTask.PENDENTE, new Date(), project));
        when(projectService.getProjectById(1L)).thenReturn(ResponseEntity.ok(project));
        when(taskRepository.findByProject(project)).thenReturn(tasks);

        ResponseEntity<List<TaskModel>> response = taskService.getAllProjectTasks(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testUpdateTask_success() {
        ProjectModel project = new ProjectModel();
        TaskModel task = new TaskModel("Tarefa antiga", StatusTask.PENDENTE, new Date(), project);
        task.setId(1L);

        ManipulateTaskDto dto = new ManipulateTaskDto(1L, 1L, "Tarefa atualizada", StatusTask.CONCLUIDA);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        ResponseEntity<TaskModel> response = taskService.updateTask(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Tarefa atualizada", response.getBody().getTitle());
        assertEquals(StatusTask.CONCLUIDA, response.getBody().getStatus());
        verify(taskRepository).save(any(TaskModel.class));
    }

    @Test
    public void testUpdateTask_notFound() {
        ManipulateTaskDto dto = new ManipulateTaskDto(99L, 1L, "Inexistente", StatusTask.CONCLUIDA);
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<TaskModel> response = taskService.updateTask(dto);

        assertEquals(404, response.getStatusCodeValue());
        verify(taskRepository, never()).save(any());
    }

    @Test
    public void testRemoveTask_success() {
        TaskModel task = new TaskModel();
        task.setId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        ResponseEntity<TaskModel> response = taskService.removeTask(1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(taskRepository).delete(task);
    }

    @Test
    public void testRemoveTask_notFound() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<TaskModel> response = taskService.removeTask(999L);

        assertEquals(404, response.getStatusCodeValue());
        verify(taskRepository, never()).delete(any());
    }

    @Test
    public void testGetTaskById_success() {
        TaskModel task = new TaskModel();
        task.setId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        ResponseEntity<TaskModel> response = taskService.getTaskById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testGetTaskById_notFound() {
        when(taskRepository.findById(404L)).thenReturn(Optional.empty());

        ResponseEntity<TaskModel> response = taskService.getTaskById(404L);

        assertEquals(404, response.getStatusCodeValue());
    }
}
