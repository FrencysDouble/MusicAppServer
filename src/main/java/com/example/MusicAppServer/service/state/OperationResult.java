package com.example.MusicAppServer.service.state;

public class OperationResult<T> {
    private final OperationStatus status;
    private final T data;

    public OperationResult(OperationStatus status, T data) {
        this.status = status;
        this.data = data;
    }

    public OperationStatus getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }
}
