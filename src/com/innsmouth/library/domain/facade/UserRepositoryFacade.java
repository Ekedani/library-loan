package com.innsmouth.library.domain.facade;

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


    public void addUser(UserQuery query) {
        repository.insertUser(query);
    }

    public User selectUserByID(long ID){return repository.selectUserById(ID);}

    public List<User> search(UserQuery query) {
        return repository.findUserByProperty(query);
    }

    public List<User> findAll(){
        return repository.findAll();
    }

    public void close() {
        try {
            repository.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(UserQuery query){
        repository.updateUser(query);
    }

    public void deleteUser(long selectedUserId) {
        repository.deleteUser(selectedUserId);
    }
}
