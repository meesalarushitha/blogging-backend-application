package com.example.blogapp.Controllers;


import com.example.blogapp.Dto.UserDto;
import com.example.blogapp.ServiceInterfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<String> userRegister(@RequestBody UserDto user){
        return userService.registerUser(user);
    }

    @PostMapping("/admin/create")
    public UserDto userCreation(@RequestBody UserDto user){
        return userService.createUser(user);
    }
    @PostMapping("/user/update")
    public UserDto userUpdate(@RequestBody UserDto user,Integer id){
        return userService.updateUser(user,id);
    }
    @GetMapping("/{userId}")
    public UserDto getUserById(
            @PathVariable Integer userId) {

        return userService.getUserById(userId);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {

        return userService.getAllUsers();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Integer userId) {

        userService.deleteUser(userId);

        return ResponseEntity.ok("User deleted successfully");
    }
}

