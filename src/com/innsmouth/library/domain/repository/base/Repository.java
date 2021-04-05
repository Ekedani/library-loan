package com.innsmouth.library.domain.repository.base;

public interface Repository {
    void setup() throws Exception;
    void connect() throws Exception;
    void close() throws Exception;
}
