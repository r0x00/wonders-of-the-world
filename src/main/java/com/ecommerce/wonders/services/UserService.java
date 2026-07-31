package com.ecommerce.wonders.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;        

import com.ecommerce.wonders.dto.UserDto.CreateUser;
import com.ecommerce.wonders.dto.UserDto.ResponseUser;
import com.ecommerce.wonders.dto.UserDto.ResponseUserGetAll;
import com.ecommerce.wonders.dto.UserDto.UpdateUser;
import com.ecommerce.wonders.exception.BadRequestException;
import com.ecommerce.wonders.mappers.UserMapper;
import com.ecommerce.wonders.model.User;
import com.ecommerce.wonders.model.User_;
import com.ecommerce.wonders.repository.UserRepository;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(
        UserRepository userRepository, 
        UserMapper userMapper,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseUserGetAll getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, JpaSort.of(User_.updatedAt).descending());
        
        Page<User> users = this.userRepository.findAll(pageable);

        List<ResponseUser> values = users.stream()
            .map(user -> this.userMapper.toDto(user))
            .toList();

        long count = this.userRepository.count();

        ResponseUserGetAll result = new ResponseUserGetAll(values, count);
        
        return result;
    }

    public ResponseUser getUserById(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found with ID: " + id));

        ResponseUser result = this.userMapper.toDto(user);

        return result;
    }

    public ResponseUser getUserByEmail(String email) {
        User user = this.userRepository.findUserByEmail(email).orElseThrow(() -> new BadRequestException("User not found with email: " + email));

        ResponseUser result = this.userMapper.toDto(user);

        return result; 
    }

    public void updateUserById(Long id, UpdateUser rawJson) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found with ID: " + id));

        this.userMapper.updateEntityFromDto(rawJson, user);

        this.userRepository.save(user);
    }

    public void createUser(CreateUser rawJson) {
        Optional<User> checkUserEmail = this.userRepository.findUserByEmail(rawJson.email());

        if(checkUserEmail.isPresent()) {
            throw new BadRequestException("User already exists with email: " + rawJson.email());
        }
        
        User user = this.userMapper.toEntityFromCreateDto(rawJson);

        String hashedPassword = this.passwordEncoder.encode(rawJson.password());
        user.setPassword(hashedPassword);

        this.userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        this.userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found with ID: " + id));

        this.userRepository.deleteById(id);
    }
    
}
