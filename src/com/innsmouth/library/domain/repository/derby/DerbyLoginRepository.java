package com.innsmouth.library.domain.repository.derby;

import com.innsmouth.library.domain.repository.api.LoginRepository;

public class DerbyLoginRepository implements LoginRepository {
    @Override
    public boolean validateCredentials(String email, String password) {
        return true;
    }

    @Override
    public void setup() throws Exception {
        //todo
    }

    @Override
    public void connect() throws Exception {
        //todo
    }

    @Override
    public void close() throws Exception {
        //todo
    }
}
