package com.example.MusicAppServer.controller;


import com.example.MusicAppServer.model.User;
import com.example.MusicAppServer.model.request.RequestUser;
import com.example.MusicAppServer.service.AuthService;
import com.example.MusicAppServer.service.ResponseService;
import com.example.MusicAppServer.service.state.OperationResult;
import com.example.MusicAppServer.service.state.OperationStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class AuthController {
    private final AuthService authService;
    private final ResponseService responseService;

    public AuthController(AuthService authService, ResponseService responseService) {
        this.authService = authService;
        this.responseService = responseService;
    }

    @PostMapping("/reg")
    public ResponseEntity<String> registerUser(@RequestBody User user)
    {
        OperationResult<String> status = authService.registerUser(user);
        return responseService.buildResponse(status.getStatus() , status.getData());
    }

    @PostMapping("/auth")
    public ResponseEntity<String> authUser(@RequestBody RequestUser requestUser)
    {
        OperationResult<String> status = authService.authUser(requestUser);
        return responseService.buildResponse(status.getStatus(), status.getData());
    }

}
