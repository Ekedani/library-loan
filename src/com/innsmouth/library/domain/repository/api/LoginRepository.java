package com.innsmouth.library.domain.repository.api;

import com.innsmouth.library.domain.repository.base.Repository;

public interface LoginRepository extends Repository {
    long validateCredentials(String email, String password);
}
