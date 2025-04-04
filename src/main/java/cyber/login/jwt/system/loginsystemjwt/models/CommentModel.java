package cyber.login.jwt.system.loginsystemjwt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.micrometer.common.lang.Nullable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @JsonIgnoreProperties({ "password", "createdAt", "updatedAt", "userRole", "enabled", "credentialsNonExpired",
            "accountNonExpired", "authorities", "accountNonLocked" }) 
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = true)
    private TaskModel task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = true)
    private ProjectModel project;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public CommentModel(String content, UserModel user, @Nullable TaskModel task, @Nullable ProjectModel project) {
        this.content = content;
        this.user = user;
        this.task = task;
        this.project = project;
        this.createdAt = LocalDateTime.now();
    }
}