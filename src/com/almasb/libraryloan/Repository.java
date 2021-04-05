package com.almasb.libraryloan;

public interface Repository {
    void setup() throws Exception;
    void connect() throws Exception;
    void close() throws Exception;
}
