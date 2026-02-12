package com.example.schedule.user.controller;

import com.example.schedule.common.AuthConstants;
import com.example.schedule.common.dto.ApiResponse;
import com.example.schedule.common.service.CommonService;
import com.example.schedule.user.dto.*;
import com.example.schedule.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final CommonService commonService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<CreateUserResponse>> signup(
            @RequestBody CreateUserRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(userService.saveUser(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(
            @Valid @RequestBody LoginRequest request,
            HttpSession session
    ) {
        SessionUser sessionUser = userService.login(request);
        session.setAttribute(AuthConstants.LOGIN_USER, sessionUser);
        return ResponseEntity.ok(ApiResponse.successWithNoContent());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetUserResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(userService.getAllUsers()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<GetUserResponse>> getOne(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(ApiResponse.success(userService.getOneUser(userId)));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UpdateUserResponse>> update(
            @PathVariable Long userId,
            @RequestBody UpdateUserRequest request,
            HttpSession session
    ) {
        SessionUser sessionUser = commonService.getSessionUser(session);
        return ResponseEntity.ok(ApiResponse.success(userService.updateUser(userId, request, sessionUser)));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long userId,
            HttpSession session
    ) {
        SessionUser sessionUser = commonService.getSessionUser(session);
        userService.deleteUser(userId, sessionUser);
        return ResponseEntity.ok(ApiResponse.successWithNoContent());
    }
}
