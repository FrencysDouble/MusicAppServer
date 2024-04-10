package com.example.MusicAppServer.service;

import com.example.MusicAppServer.model.User;
import com.example.MusicAppServer.model.request.RequestUser;
import com.example.MusicAppServer.repositories.AuthRepository;
import com.example.MusicAppServer.service.state.OperationResult;
import com.example.MusicAppServer.service.state.OperationStatus;
import com.example.MusicAppServer.session.IDSessionProvider;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    Logger logger = LoggerFactory.getLogger(AuthService.class);
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final AuthRepository authRepository;
    private final HttpSession httpSession;
    private final IDSessionProvider idSessionProvider;

    public AuthService(AuthRepository authRepository, HttpSession httpSession, IDSessionProvider idSessionProvider) {
        this.authRepository = authRepository;
        this.httpSession = httpSession;
        this.idSessionProvider = idSessionProvider;
    }

    public OperationResult<String> registerUser(User user)
    {
        String password = user.getPassword();
        String sessionId = idSessionProvider.getSessionId();
        if (password.isEmpty() || password.length() < MIN_PASSWORD_LENGTH) {
            return new OperationResult<>(OperationStatus.INVALID_CREDENTIALS, null);
        }
        try {
            authRepository.save(user);

            httpSession.setAttribute("id", sessionId);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR, null);
        }
        return new OperationResult<>(OperationStatus.SUCCESS, sessionId);
    }

    public OperationResult<String> authUser(RequestUser requestUser)
    {
        String sessionId = idSessionProvider.getSessionId();
        try {
            User user = authRepository.findByEmail(requestUser.getEmail());
            if (user != null && user.getPassword().equals(requestUser.getPassword())) {
                httpSession.setAttribute("id",sessionId);
                return new OperationResult<>(OperationStatus.SUCCESS, sessionId);
            }
            else {
                return new OperationResult<>(OperationStatus.INVALID_CREDENTIALS, null);
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
