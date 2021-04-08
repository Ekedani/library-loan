package com.innsmouth.library.domain.facade;

import com.innsmouth.library.data.query.UserQuery;
import com.innsmouth.library.domain.repository.api.UserRepository;
import com.innsmouth.library.domain.repository.base.Repository;

public class UserRepositoryFacade extends BaseFacade {
    public UserRepositoryFacade(UserRepository repository) {
        super(repository);
    }


    public void addUser(UserQuery query) {
        ((UserRepository) repository).insertUser(query);
    }
}
