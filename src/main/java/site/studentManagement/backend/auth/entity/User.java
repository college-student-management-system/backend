package site.studentManagement.backend.auth.entity;

import jakarta.persistence.*;
import lombok.Data;
import site.studentManagement.backend.auth.enums.AccountStatus;
import site.studentManagement.backend.auth.enums.Role;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;
}