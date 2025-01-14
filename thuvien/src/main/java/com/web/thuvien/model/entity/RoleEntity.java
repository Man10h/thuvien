package com.web.thuvien.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "role")
@Getter
@Setter
public class RoleEntity {
    @Id
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @OneToMany(mappedBy = "roleEntity")
    private List<UserEntity> userEntities;

}
