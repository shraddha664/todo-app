package com.saru.todo.app.repository;

import com.saru.todo.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {
    @Query("SELECT u.id FROM User u JOIN u.todos t WHERE t.todoId = :todoId")
    Long findUserIdByTodoId(@Param("todoId") Long todoId);

    Optional<User> findByUserName(String userName);


  /*  @Query("SELECT u From User u")
    List<User> findAllUsers();

    @Query("SELECT u From User u WHERE u.id=:uId")
    User findUserById(@Param("uId") Long userId);

    @Query("SELECT u From User u WHERE u.id=:uId AND u.name=:name ")
    User findUserByUserIdAndName(@Param("uId") Long userId,@Param("name") String name);

    @Query("SELECT u From User u JOIN u.todos t WHERE t.id=:todoId AND t.name=:name")
    User findUserByTodoIdAndTodoName(@Param("todoId") Long todoId,@Param("name") String name);
    */


}
