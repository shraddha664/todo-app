package com.saru.todo.app.service;

import com.saru.todo.app.dto.TodoDto;
import com.saru.todo.app.dto.UserDto;
import com.saru.todo.app.entity.Todo;
import com.saru.todo.app.entity.User;
import com.saru.todo.app.repository.TodoRepository;
import com.saru.todo.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setUserEmail(userDto.getUserEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
    }

    public void saveTodo(Long id, TodoDto todoDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException());

        Todo todo = new Todo();
        todo.setTaskName(todoDto.getTaskName());
        todo.setDescription(todoDto.getDescription());

        user.getTodos().add(todo);

        userRepository.save(user);

    }

    public List<Todo> findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        List<Todo> todo = user.getTodos();
        return todo;
    }


    public String updateById(Long userId, Long todoId, TodoDto todoDto) {
        String message = "Item not found";
        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            message = "User not found";
            return message;
        }
        User user1 = user.get();

        List<Todo> todos = user1.getTodos();

//     compare the todoId with all the ids of todos
        for (Todo todo1 : todos) {
            if (todo1.getTodoId().equals(todoId)) {
                System.out.println("within if");
                Todo todo = todoRepository.findById(todoId).get();

                todo.setTaskName(todoDto.getTaskName());
                todo.setDescription(todoDto.getDescription());

                user1.getTodos().add(todo);

                userRepository.save(user1);
                message = "Todo updated successfully";
                break;
            }
        }

        return message;

    }


    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public String deleteUserByUserIdAndTodoId(Long userId, Long todoId) {
        String message = "Todo not found";
        Optional<User> user = userRepository.findById(userId);
        System.out.println(user);

        if (!user.isPresent()) {
            message = "User not found";
            return message;
        }
        User user1 = user.get();

        List<Todo> todos = user1.getTodos();
        System.out.println(todos);
//     compare the todoId with all the ids of todos
        for (Todo todo1 : todos) {
            if (todo1.getTodoId().equals(todoId)) {

                System.out.println("inside if");
                todoRepository.deleteById(todoId);
                message = "Todo deleted succesfully";
                System.out.println(message);
                break;
            }
        }
        return message;
    }



    public String updateStatus(Long userId, Long todoId) {
        String message = "User not found";
        Optional<Todo> todo = todoRepository.findById(todoId);
        System.out.println(todo);

        if (!todo.isPresent()) {
            message = "Todo not found";
            return message;
        }

        Long existinguserId=userRepository.findUserIdByTodoId(todoId);
        if (existinguserId.equals(userId)){
            Todo todo1=todo.get();
            todo1.setIsCompleted(!todo1.getIsCompleted());
            todoRepository.save(todo1);
            message="Todo updates succesfully";
        }
        return message;
    }



}
