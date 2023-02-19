package com.saru.todo.app.controller;

import com.saru.todo.app.dto.TodoDto;
import com.saru.todo.app.dto.UserDto;
import com.saru.todo.app.entity.Todo;
import com.saru.todo.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("api/v1/users/register")
    public ResponseEntity<String> saveUser(@RequestBody UserDto userDto){
        userService.saveUser(userDto);
       return  new ResponseEntity<> ("User registered succesfully", HttpStatus.OK);
    }


    @PostMapping("api/v1/users/{userId}/todos")
    public ResponseEntity<String> saveTodo(@PathVariable("userId") Long id,@RequestBody TodoDto todoDto){
       userService.saveTodo(id,todoDto);
        return  new ResponseEntity<> ("Todo saved succesfully", HttpStatus.OK);

    }

    @GetMapping("api/v1/users/{userId}/todos")
    public List<Todo> findUserById(@PathVariable("userId") Long id){
        return userService.findUserById(id);
    }

    @PutMapping("api/v1/users/{userId}/todos/{todoId}")
    public ResponseEntity<String> updateById(@PathVariable("userId") Long userId,@PathVariable("todoId") Long todoId,@RequestBody TodoDto todoDto) throws Throwable {
        String message= userService.updateById(userId, todoId, todoDto);
        System.out.println(message);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @DeleteMapping("api/v1/users/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable("userId") Long id){
        userService.deleteUserById(id);
        return new ResponseEntity<>("User deleted succesfully", HttpStatus.OK);
    }


    @DeleteMapping("api/v1/users/{userId}/todos/{todoId}")
    public ResponseEntity<String> deleteUserByUserIdAndTodoId(@PathVariable("userId") Long userId,@PathVariable("todoId") Long todoId){
        String message= userService.deleteUserByUserIdAndTodoId(userId,todoId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("api/v1/users/{userId}/todos/{todoId}")
    public ResponseEntity<String> updateStatus(@PathVariable("userId") Long userId,@PathVariable("todoId") Long todoId){
      String message=userService.updateStatus(userId,todoId);
      return  new ResponseEntity<>(message, HttpStatus.OK);
    }


}
