package com.example.schedule.user.service;

import com.example.schedule.user.dto.LoginRequest;
import com.example.schedule.user.dto.*;
import com.example.schedule.user.entity.User;
import com.example.schedule.user.repository.UserRepository;
import com.example.schedule.validation.AlreadyExistingEmailException;
import com.example.schedule.validation.AlreadyLoginUserException;
import com.example.schedule.validation.UserNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponse saveUser(CreateUserRequest request) {
        boolean existence = userRepository.existsByEmail(request.getEmail());
        if (existence) {
            throw new AlreadyExistingEmailException("이미 존재하는 이메일입니다.");
        }
        User user = new User(request.getName(), request.getEmail(), request.getPassword());
        User savedUser = userRepository.save(user);
        return new CreateUserResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public SessionUser login(@Valid LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new AlreadyLoginUserException("이미 로그인 되어있는 회원입니다.")
        );
        return new SessionUser(user);
    }

    @Transactional(readOnly = true)
    public List<GetUserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(GetUserResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public GetUserResponse getOneUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("없는 회원입니다.")
        );
        return new GetUserResponse(user);
    }

    @Transactional
    public UpdateUserResponse updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("없는 회원입니다.")
        );
        user.update(request.getName(), request.getEmail());
        return new UpdateUserResponse(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        boolean existence = userRepository.existsById(userId);
        if (!existence) {
            throw new UserNotFoundException("없는 회원입니다.");
        }
        userRepository.deleteById(userId);
    }
}
