package site.studentManagement.backend.superadmin.entity;

import jakarta.persistence.*;
import lombok.Data;
import site.studentManagement.backend.auth.entity.User;
import site.studentManagement.backend.superadmin.enums.Designation;
import site.studentManagement.backend.superadmin.enums.ProfileStatus;

@Entity
@Table(name = "super_admin_profile")
@Data
public class SuperAdminProfile {

    @Id
    @Column(name = "profile_id")   // <-- ADD THIS LINE
    private String profileId;

    @OneToOne
    @JoinColumn(name = "user_id")  // <-- MUST MATCH your DB
    private User user;

    private String name;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Designation designation;

    private String institutionName;

    private String institutionCode;

    private String address;

    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
}
