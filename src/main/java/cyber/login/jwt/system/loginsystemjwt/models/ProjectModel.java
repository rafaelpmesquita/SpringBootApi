package cyber.login.jwt.system.loginsystemjwt.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cyber.login.jwt.system.loginsystemjwt.dtos.UserDto;
import jakarta.persistence.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@NoArgsConstructor
@AllArgsConstructor
public class ProjectModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

    @JsonIgnoreProperties({ "password", "createdAt", "updatedAt", "userRole", "enabled", "credentialsNonExpired",
            "accountNonExpired", "authorities", "accountNonLocked" }) // Escondendo outros campos
    @ManyToOne
    private UserModel owner;

    public ProjectModel(String name, String description, UserModel owner, Date createdAt) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.createdAt = createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}