package com.innsmouth.library.controller.login;

import com.innsmouth.library.data.dataobject.BaseUser;

public class UserSingleton {
    private static UserSingleton instance = null;

    private BaseUser currentUser = null;

    private UserSingleton() { }

    public static UserSingleton getInstance() {
        if (instance == null) {
            instance = new UserSingleton();
        }
        return instance;
    }

    public BaseUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(BaseUser currentUser) {
        this.currentUser = currentUser;
    }
}