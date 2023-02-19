package com.saru.todo.app.repository;

import com.saru.todo.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {
    @Query("SELECT u.id FROM User u JOIN u.todos t WHERE t.todoId = :todoId")
    Long findUserIdByTodoId(@Param("todoId") Long todoId);
}
