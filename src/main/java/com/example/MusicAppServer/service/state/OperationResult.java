package com.example.MusicAppServer.service.state;

import java.util.List;

/**
 * This is the main Data Class for ResponseService
 * @param <T>
**/
public class OperationResult<T> {
    private final OperationStatus status;
    private final  T data;
    private final List<T> listData;

    public OperationResult(OperationStatus status, T data) {
        this.status = status;
        this.data = data;
        this.listData = null;
    }

    public OperationResult(OperationStatus status, List<T> listData) {
        this.status = status;
        this.data = null;
        this.listData = listData;
    }

    public OperationResult(OperationStatus status)
    {
        this.status = status;
        this.data = null;
        this.listData = null;
    }

    public OperationStatus getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public List<T> getListData()
    {
        return listData;
    }
}
