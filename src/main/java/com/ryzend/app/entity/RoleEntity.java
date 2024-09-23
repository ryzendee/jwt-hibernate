package com.ryzend.app.entity;

import com.ryzend.app.enums.ERole;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole role;

    @Builder.Default
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRoleEntity> userRoleEntityList  = new ArrayList<>();

    public void addUserRoleEntity(UserRoleEntity userRoleEntity) {
        userRoleEntityList.add(userRoleEntity);
        userRoleEntity.setRole(this);
    }
}
