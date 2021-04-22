package com.innsmouth.library.domain.repository.api;

import com.innsmouth.library.data.dataobject.User;
import com.innsmouth.library.domain.repository.base.Repository;

import java.util.List;

public interface UserRepository extends Repository {
    void insertUser(User    user);
    boolean updateUser(User user);
    boolean deleteUser(long id);
    User selectUserById(long id);


    List<User> findUserByProperty(User query);
    List<User> findAll();
}
