package com.example.MusicAppServer.service.state;

import com.google.protobuf.Any;

public enum OperationStatus {
    SUCCESS(),
    CANCELED(),
    INTERNAL_SERVER_ERROR(),
    INVALID_CREDENTIALS()
}
