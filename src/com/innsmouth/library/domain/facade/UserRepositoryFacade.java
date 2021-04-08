package com.innsmouth.library.domain.facade;

import com.innsmouth.library.data.query.UserQuery;
import com.innsmouth.library.domain.repository.api.UserRepository;
import com.innsmouth.library.domain.repository.base.Repository;

public class UserRepositoryFacade {
    private final UserRepository repository;

    public UserRepositoryFacade(UserRepository repository) {
        this.repository = repository;
    }


    public void addUser(UserQuery query) {
        repository.insertUser(query);
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
