package com.example.MusicAppServer.service;

import com.example.MusicAppServer.service.state.OperationStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    public ResponseEntity<String> buildResponse(OperationStatus status, String body) {
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
