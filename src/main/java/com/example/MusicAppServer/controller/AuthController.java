package com.example.MusicAppServer.controller;


import com.example.MusicAppServer.model.User;
import com.example.MusicAppServer.model.request.RequestUser;
import com.example.MusicAppServer.service.AuthService;
import com.example.MusicAppServer.service.state.OperationResult;
import com.example.MusicAppServer.service.state.OperationStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/reg")
    public ResponseEntity<String> registerUser(@RequestBody User user)
    {
        OperationResult<String> status = authService.registerUser(user);
        return buildResponse(status.getStatus() , status.getData());
    }

    @PostMapping("/auth")
    public ResponseEntity<String> authUser(@RequestBody RequestUser requestUser)
    {
        OperationResult<String> status = authService.authUser(requestUser);
        return buildResponse(status.getStatus(), status.getData());
    }
    
    private ResponseEntity<String> buildResponse(OperationStatus status, String body) {
        HttpStatus httpStatus;
        String message = switch (status) {
            case SUCCESS -> {
                httpStatus = HttpStatus.OK;
                yield body;
            }
            case INVALID_CREDENTIALS -> {
                httpStatus = HttpStatus.UNAUTHORIZED;
                yield "Invalid credentials";
            }
            case INTERNAL_SERVER_ERROR -> {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                yield "Internal server error";
            }
            default -> {
                httpStatus = HttpStatus.BAD_REQUEST;
                yield "Bad request";
            }
        };

        return ResponseEntity.status(httpStatus).body(message);
    }
}
