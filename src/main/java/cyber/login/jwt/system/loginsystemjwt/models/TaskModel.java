package cyber.login.jwt.system.loginsystemjwt.models;

import java.util.Date;

import cyber.login.jwt.system.loginsystemjwt.enums.StatusTask;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único da entidade

    private String title;

    @Enumerated(EnumType.STRING)
    private StatusTask status; // PENDENTE, EM_ANDAMENTO, CONCLUIDA

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "started_at", nullable = true)
    private Date startedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "finished_at", nullable = true)
    private Date finishedAt;

    @ManyToOne
    private ProjectModel project;

    public TaskModel() {
    }

    public TaskModel(String title, StatusTask status, Date createdAt, ProjectModel project) {
        this.title = title;
        this.status = status;
        this.createdAt = createdAt;
        this.project = project;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public long getId() {
        return this.id;
    }

    public StatusTask getStatus() {
        return this.status;
    }
}