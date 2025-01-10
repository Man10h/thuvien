package com.web.thuvien.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private Boolean status = false;

    @ManyToMany
    @JoinTable(name = "myfavourite",
        joinColumns = @JoinColumn(name = "user_id", nullable = false),
        inverseJoinColumns =  @JoinColumn(name = "book_id", nullable = false)
    )
    private List<BookEntity> bookEntities;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;
}
