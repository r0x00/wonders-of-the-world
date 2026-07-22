package com.ecommerce.wonders.services;

import java.util.List;

import org.springframework.stereotype.Service;        

import com.ecommerce.wonders.dto.UserDto.CreateUser;
import com.ecommerce.wonders.dto.UserDto.ResponseUser;
import com.ecommerce.wonders.dto.UserDto.UpdateUser;
import com.ecommerce.wonders.exception.BadRequestException;
import com.ecommerce.wonders.model.User;
import com.ecommerce.wonders.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public List<ResponseUser> getAllUsers(){
        List<User> users = this.userRepository.findAll();

        List<ResponseUser> values = users.stream()
            .map(user -> new ResponseUser(
                user.getId(), 
                user.getName(), 
                user.getEmail()
            ))
            .toList();
        
        return values;
    }
    
    public Long getAllUsersCount(){
        long count = this.userRepository.count();

        return count;
    }

    public ResponseUser getUserById(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found with ID: " + id));

        ResponseUser result = new ResponseUser(
            user.getId(), 
            user.getName(), 
            user.getEmail()
        );

        return result;
    }


    public void updateUserById(Long id, UpdateUser rawJson) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found with ID: " + id));

        user.setName(rawJson.name());
        user.setEmail(rawJson.email());

        this.userRepository.save(user);
    }

    public void createUser(CreateUser rawJson) {
        User existUserWithCpf = this.userRepository.findUserByCpf(rawJson.cpf());

        if(existUserWithCpf != null) {
            throw new BadRequestException("User already exists with cpf: " + rawJson.cpf());
        }

        User existUserWithEmail = this.userRepository.findUserByEmail(rawJson.email());

        if(existUserWithEmail != null) {
            throw new BadRequestException("User already exists with email: " + rawJson.email());
        }

        User user = new User();

        user.setName(rawJson.name());
        user.setEmail(rawJson.email());
        user.setCpf(rawJson.cpf());

        this.userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        this.userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found with ID: " + id));

        this.userRepository.deleteById(id);
    }
    
}
