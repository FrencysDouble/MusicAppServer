package com.example.MusicAppServer.service;

import com.example.MusicAppServer.service.state.OperationStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * This is the Service for building Responses for a Controllers
 */

@Service
public class ResponseService {

    public <T> ResponseEntity buildResponse(OperationStatus status,T body) {
        HttpStatus httpStatus;
        String message = switch (status) {
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
            if (status == OperationStatus.SUCCESS)
            {
                httpStatus = HttpStatus.OK;
                return ResponseEntity.status(httpStatus).body(body);
            }

        return ResponseEntity.status(httpStatus).body(message);
    }
}
