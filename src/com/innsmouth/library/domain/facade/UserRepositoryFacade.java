package com.innsmouth.library.domain.facade;

import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.data.dataobject.User;
import com.innsmouth.library.data.query.UserQuery;
import com.innsmouth.library.domain.repository.api.UserRepository;

import java.util.List;

public class UserRepositoryFacade {
    private final UserRepository repository;

    public UserRepositoryFacade(UserRepository repository) {
        this.repository = repository;
        try {
            repository.connect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNewUser(String name, String address, String email) {
        User user = new User();
        user.setName(name);
        user.setAddress(address);
        user.setEmail(email);

        repository.insertUser(user);
    }


}
