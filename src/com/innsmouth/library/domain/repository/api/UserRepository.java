package com.innsmouth.library.domain.repository.api;

import com.innsmouth.library.data.query.UserQuery;
import com.innsmouth.library.domain.repository.base.Repository;

public interface UserRepository extends Repository {

    public void insertUser(UserQuery user);

}
