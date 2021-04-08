package com.innsmouth.library.domain.facade;

import com.innsmouth.library.domain.repository.api.LoginRepository;

public class LoginFacade {
    private final LoginRepository repository;

    public LoginFacade(LoginRepository repository) {
        this.repository = repository;
    }

    public boolean credentialsValid(String email, String password){
        return repository.validateCredentials(email, password);
    }

    public void close() {
        try {
            repository.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
