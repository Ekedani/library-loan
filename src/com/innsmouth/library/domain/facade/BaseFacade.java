package com.innsmouth.library.domain.facade;

import com.innsmouth.library.domain.repository.base.Repository;

public class BaseFacade {
    protected final Repository repository;

    public BaseFacade(Repository repository) {
        this.repository = repository;
        try {
            repository.connect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
