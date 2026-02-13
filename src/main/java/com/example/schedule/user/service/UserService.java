package com.example.schedule.user.service;

import com.example.schedule.config.PasswordEncoder;
import com.example.schedule.user.dto.*;
import com.example.schedule.user.entity.User;
import com.example.schedule.user.repository.UserRepository;
import com.example.schedule.validation.AlreadyExistingEmailException;
import com.example.schedule.validation.AlreadyLoginUserException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserCommonValidationService userCommon;

    @Transactional
    public CreateUserResponse saveUser(CreateUserRequest request) {
        boolean existence = userRepository.existsByEmail(request.getEmail());
        if (existence) {
            throw new AlreadyExistingEmailException("이미 존재하는 이메일입니다.");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getName(), request.getEmail(), encodedPassword);
        User savedUser = userRepository.save(user);
        return new CreateUserResponse(savedUser);
    }

    public SessionUser login(@Valid LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new AlreadyLoginUserException("이미 로그인 되어있는 회원입니다.")
        );
        return new SessionUser(user);
    }

    public List<GetUserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(GetUserResponse::new)
                .toList();
    }

    public GetUserResponse getOneUser(Long userId) {
        User user = userCommon.checkUser(userId);
        return new GetUserResponse(user);
    }

    @Transactional
    public UpdateUserResponse updateUser(Long userId, UpdateUserRequest request, SessionUser sessionUser) {
        User user = userCommon.checkUser(userId);
        userCommon.checkEqualUserUser(sessionUser, user);
        String encodedPassword = passwordEncoder.encode(request.getUpdatePassword());
        user.update(request.getName(), request.getEmail(), encodedPassword);
        return new UpdateUserResponse(user);
    }

    @Transactional
    public void deleteUser(Long userId, SessionUser sessionUser) {
        User user = userCommon.checkUser(userId);
        userCommon.checkEqualUserUser(sessionUser, user);
        userRepository.deleteById(userId);
    }
}
