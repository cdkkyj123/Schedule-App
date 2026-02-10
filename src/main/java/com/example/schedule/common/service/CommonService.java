package com.example.schedule.common.service;

import com.example.schedule.common.AuthConstants;
import com.example.schedule.user.dto.SessionUser;
import com.example.schedule.validation.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    public SessionUser getSessionUser(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(AuthConstants.LOGIN_USER);
        if (sessionUser == null) {
            throw new UnauthorizedException("인가되지 않은 세션입니다.");
        }
        return sessionUser;
    }
}
