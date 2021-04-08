package com.innsmouth.library.domain.facade;

import com.innsmouth.library.domain.repository.api.BookRepository;

public class BaseFacade {
    protected final BookRepository repository;

    public BaseFacade(BookRepository repository) {
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
