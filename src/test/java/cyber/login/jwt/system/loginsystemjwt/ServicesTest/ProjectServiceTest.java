package cyber.login.jwt.system.loginsystemjwt.ServicesTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import cyber.login.jwt.system.loginsystemjwt.dtos.ManipulateProjectDto;
import cyber.login.jwt.system.loginsystemjwt.enums.UserRole;
import cyber.login.jwt.system.loginsystemjwt.models.ProjectModel;
import cyber.login.jwt.system.loginsystemjwt.models.UserModel;
import cyber.login.jwt.system.loginsystemjwt.repositories.ProjectRepository;
import cyber.login.jwt.system.loginsystemjwt.services.ProjectService;
import cyber.login.jwt.system.loginsystemjwt.services.interfaces.IUserService;

import java.util.List;

public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private IUserService userService;

    @InjectMocks
    private ProjectService projectService;

    private UserModel mockUser;

    @BeforeEach
    public void setUp() { 
        MockitoAnnotations.openMocks(this);
        mockUser = new UserModel("user@email.com", "password", UserRole.USER);
    }

    @Test
    public void testCreateProject() {
        ManipulateProjectDto dto = new ManipulateProjectDto(0, "Projeto Teste", "Descricao do Projeto");
        when(userService.getCurrentUser()).thenReturn(ResponseEntity.ok(mockUser));

        ResponseEntity<ProjectModel> response = projectService.createProject(dto);

        assertEquals("Projeto Teste", response.getBody().getName());
        verify(projectRepository, times(1)).save(any(ProjectModel.class));
    }

    @Test
    public void testGetAllUserProjects() {
        when(userService.getCurrentUser()).thenReturn(ResponseEntity.ok(mockUser));
        when(projectRepository.findByOwner(mockUser)).thenReturn(Arrays.asList(
                new ProjectModel("Projeto 1", "Desc 1", mockUser, new Date()),
                new ProjectModel("Projeto 2", "Desc 2", mockUser, new Date())));

        ResponseEntity<List<ProjectModel>> response = projectService.getAllUserProjects();

        assertEquals(2, response.getBody().size());
        verify(projectRepository, times(1)).findByOwner(mockUser);
    }

    @Test
    public void testGetProjectById() {
        ProjectModel project = new ProjectModel("Projeto Teste", "Desc", mockUser, new Date());
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        ResponseEntity<ProjectModel> response = projectService.getProjectById(1L);

        assertEquals("Projeto Teste", response.getBody().getName());
    }

    @Test
    public void testUpdateProject() {
        ProjectModel existing = new ProjectModel("Old Name", "Old Desc", mockUser, new Date());
        existing.setId(1L);

        ManipulateProjectDto dto = new ManipulateProjectDto(1, "New Name", "New Desc");
        when(projectRepository.findById(1L)).thenReturn(Optional.of(existing));

        ResponseEntity<ProjectModel> response = projectService.updateProject(dto);

        assertEquals("New Name", response.getBody().getName());
        assertEquals("New Desc", response.getBody().getDescription());
        verify(projectRepository).save(existing);
    }

    @Test
    public void testDeleteProject() {
        ProjectModel project = new ProjectModel("Projeto Teste", "Desc", mockUser, new Date());
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        ResponseEntity<Boolean> response = projectService.deleteProject(1L);

        assertTrue(response.getBody());
        verify(projectRepository).delete(project);
    }
}
