package com.example.blogapp.ServiceImpl;


import com.example.blogapp.Dto.UserDto;
import com.example.blogapp.Entity.Role;
import com.example.blogapp.Entity.User;
import com.example.blogapp.Exception.ResourceNotFoundException;
import com.example.blogapp.Repository.RoleRepository;
import com.example.blogapp.Repository.UserRepository;
import com.example.blogapp.ServiceInterfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public ResponseEntity<String> registerUser(UserDto userDto) {
        int id = userDto.getId();
        Optional<User> userId =userRepository.findById(id);
        if(userId.isEmpty()){
            User user = this.modelMapper.map(userDto,User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role role = this.roleRepository.findById(2)
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "id", 2));
            user.getRoles().add(role);
            userRepository.save(user);
            return new ResponseEntity<>("User created Successfully",HttpStatusCode.valueOf(200));

        }
        else{
            return new ResponseEntity<>("User Already exists",HttpStatusCode.valueOf(400));
        }

    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user=this.modelMapper.map(userDto,User.class);
        User savedUser= this.userRepository.save(user);
        return this.modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user= this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","id",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        User updateduser = this.userRepository.save(user);
        return this.modelMapper.map(updateduser,UserDto.class);

        }

    @Override
    public UserDto getUserById(Integer userId) {
       User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        return this.modelMapper.map(user,UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user->this.modelMapper.map(user,UserDto.class))
                .toList();

    }

    @Override
    public void deleteUser(Integer userId) {
     User user = this.userRepository.findById(userId)
             .orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
     this.userRepository.deleteById(userId);
    }

}



