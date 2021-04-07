package com.innsmouth.library.data.dataobject;

abstract public class BaseUser {
    private long id;

    public BaseUser(long id) {
        this.id = id;
    }

    protected long getId() {
        return id;
    }

    protected void setId(long id) {
        this.id = id;
    }
}
