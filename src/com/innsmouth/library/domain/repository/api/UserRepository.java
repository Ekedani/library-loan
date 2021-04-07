package com.innsmouth.library.domain.repository.api;

import com.innsmouth.library.data.dataobject.User;
import com.innsmouth.library.data.query.UserQuery;
import com.innsmouth.library.domain.repository.base.Repository;

import java.util.List;

public interface UserRepository extends Repository {

    public void insertUser(UserQuery user);


}
