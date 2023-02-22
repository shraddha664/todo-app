package com.saru.todo.app.controller;

import com.saru.todo.app.dto.TodoDto;
import com.saru.todo.app.dto.UserDto;
import com.saru.todo.app.dto.UserLoginInfoDto;
import com.saru.todo.app.entity.Todo;
import com.saru.todo.app.service.JwtService;
import com.saru.todo.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    private final UserService userService;

    @GetMapping("api/v1/welcome")
    public String welcomeUsers(){
        return "Welcome";
    }

    @PostMapping("api/v1/users/register")
    public ResponseEntity<String> saveUser(@RequestBody UserDto userDto){
        userService.saveUser(userDto);
        return  new ResponseEntity<> ("User registered succesfully", HttpStatus.OK);
    }


    @PostMapping("api/v1/users/{userId}/todos")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> saveTodo(@PathVariable("userId") Long id,@RequestBody TodoDto todoDto){
        userService.saveTodo(id,todoDto);
        return  new ResponseEntity<> ("Todo saved succesfully", HttpStatus.OK);

    }

    @GetMapping("api/v1/users/{userId}/todos")
    @PreAuthorize("hasAuthority('USER')")
    public List<Todo> findUserById(@PathVariable("userId") Long id){
        return userService.findUserById(id);
    }

    @PutMapping("api/v1/users/{userId}/todos/{todoId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> updateById(@PathVariable("userId") Long userId,@PathVariable("todoId") Long todoId,@RequestBody TodoDto todoDto) throws Throwable {
        String message= userService.updateById(userId, todoId, todoDto);
        System.out.println(message);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @DeleteMapping("api/v1/users/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteUserById(@PathVariable("userId") Long id){
        userService.deleteUserById(id);
        return new ResponseEntity<>("User deleted succesfully", HttpStatus.OK);
    }


    @DeleteMapping("api/v1/users/{userId}/todos/{todoId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> deleteUserByUserIdAndTodoId(@PathVariable("userId") Long userId,@PathVariable("todoId") Long todoId){
        String message= userService.deleteUserByUserIdAndTodoId(userId,todoId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("api/v1/users/{userId}/{todoId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> updateStatus(@PathVariable("userId") Long userId,@PathVariable("todoId") Long todoId){
        String message=userService.updateStatus(userId,todoId);
        return  new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("api/v1/users/login")
    @ResponseStatus(HttpStatus.FOUND)
    public String loginUser(@RequestBody UserLoginInfoDto userLoginInfoDto){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginInfoDto.getUserName(),userLoginInfoDto.getPassword()));
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(userLoginInfoDto.getUserName()) ;

        }else {
            throw  new UsernameNotFoundException("invalid user request");
        }
    }


}
