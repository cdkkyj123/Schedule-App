package com.example.schedule.user.service;

import com.example.schedule.common.AuthConstants;
import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.user.dto.SessionUser;
import com.example.schedule.user.entity.User;
import com.example.schedule.user.repository.UserRepository;
import com.example.schedule.validation.ForbiddenUserException;
import com.example.schedule.validation.UnauthorizedException;
import com.example.schedule.validation.UserNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommonValidationService {
    private final UserRepository userRepository;

    public User checkUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("없는 회원입니다.")
        );
    }

    public void existenceUser(User commentUser) {
        boolean exUser = userRepository.existsById(commentUser.getId());
        if (!exUser) {
            throw new UserNotFoundException("없는 회원입니다.");
        }
    }

    public void checkEqualUserUser(SessionUser sessionUser, User user) {
        if (!sessionUser.getId().equals(user.getId())) {
            throw new ForbiddenUserException("본인의 계정만 조작할 수 있습니다.");
        }
    }
}
