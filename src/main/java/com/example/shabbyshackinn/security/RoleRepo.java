package com.example.shabbyshackinn.security;


import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RoleRepo extends CrudRepository<Role , UUID> {
    Role findByName(String name);
}
