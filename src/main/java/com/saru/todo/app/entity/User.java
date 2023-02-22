package com.saru.todo.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Transactional
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    private String userName;
    private String userEmail;
    private String password;
    private String roles;

    @OneToMany(fetch = FetchType.EAGER,orphanRemoval=true,cascade = CascadeType.ALL,targetEntity = Todo.class)
    @JoinColumn(name = "FK_USER_ID",referencedColumnName = "userId")
    private List<Todo>todos;



}
